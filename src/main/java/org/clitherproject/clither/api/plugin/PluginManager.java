package org.clitherproject.clither.api.plugin;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.clitherproject.clither.api.Server;
import org.clitherproject.clither.api.event.Cancellable;
import org.clitherproject.clither.api.event.Event;
import org.clitherproject.clither.api.event.EventHandler;
import org.clitherproject.clither.api.event.EventPriority;
import org.clitherproject.clither.api.event.Listener;

public class PluginManager {

    private final Server server;
    private final Map<String, Plugin> plugins = new HashMap<String, Plugin>();
    private final Multimap<Plugin, Listener> listeners = ArrayListMultimap.create();
    private URLClassLoader classLoader;
    private boolean loadedPlugins = false;

    public PluginManager(Server server) {
        Preconditions.checkNotNull(server, "server");
        this.server = server;
    }

    public void loadPlugins(File directory) throws Throwable {
        Preconditions.checkState(!loadedPlugins, "Plugins can not currently be reloaded");
        Preconditions.checkArgument(directory.isDirectory(), "Directory is not a directory");
        File[] jarFiles = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        URL[] urls = new URL[jarFiles.length];

        for (int i = 0; i < urls.length; i++) {
            urls[i] = jarFiles[i].toURI().toURL();
        }

        loadedPlugins = true;
        classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());

        // Scan through the classes to find Plugin extensions with @PluginInfo
        List<Class<? extends Plugin>> pluginClasses = new ArrayList<>();
        for (File jarFile : jarFiles) {
            try (ZipInputStream zip = new ZipInputStream(new FileInputStream(jarFile))) {
                ZipEntry entry;

                while ((entry = zip.getNextEntry()) != null) {
                    if (!entry.getName().endsWith(".class")) {
                        continue;
                    }

                    String[] splitName = entry.getName().split("/");
                    if (splitName[splitName.length - 1].contains("$")) {
                        // Inner class, skip
                        continue;
                    }

                    String className = entry.getName().substring(0, entry.getName().length() - 6).replace('/', '.');
                    Class<?> clazz = Class.forName(className, true, classLoader);

                    if (Plugin.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(PluginInfo.class)) {
                        // We have a plugin!
                        pluginClasses.add(clazz.asSubclass(Plugin.class));
                    }
                }
            }
        }

        // Next, initialize the plugins
        for (Class<? extends Plugin> clazz : pluginClasses) {
            PluginInfo info = clazz.getAnnotation(PluginInfo.class);
            Plugin plugin = clazz.newInstance();
            plugins.put(info.name().toLowerCase(), plugin);
            plugin.init(server, this);
            server.getLogger().info("Loaded plugin " + info.name() + " (version " + info.version() + ").");
        }
    }

    public void enablePlugins() {
        plugins.values().forEach(this::enablePlugin);
    }

    public void disablePlugins() {
        plugins.values().forEach(this::disablePlugin);
    }

    public void enablePlugin(Plugin plugin) {
        Preconditions.checkNotNull(plugin, "plugin");
        Preconditions.checkArgument(!plugin.isEnabled(), "Plugin is already enabled!");

        PluginInfo info = plugin.getPluginInfo();
        plugin.setEnabled(true);
        server.getLogger().info("Enabled plugin " + info.name() + " (version " + info.version() + ").");
    }

    public void disablePlugin(Plugin plugin) {
        Preconditions.checkNotNull(plugin, "plugin");
        Preconditions.checkArgument(plugin.isEnabled(), "Plugin is not enabled!");

        PluginInfo info = plugin.getPluginInfo();
        plugin.setEnabled(false);
        listeners.removeAll(plugin);
        server.getLogger().info("Disabled plugin " + info.name() + " (version " + info.version() + ").");
    }

    public void registerEvents(Plugin plugin, Listener listener) {
        Preconditions.checkNotNull(plugin, "plugin");
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkState(plugin.isEnabled(), "Can not register a Listener for a disabled Plugin!");
        Preconditions.checkArgument(!listeners.containsValue(listener), "Attempted to register a Listener that is already registered!");

        listeners.put(plugin, listener);
    }

    public void callEvent(Event event) {
        Preconditions.checkNotNull(event, "event");

        // Discover event handlers
        // TODO - This should probably be moved and cached to execute on registration of the listener
        Multimap<EventPriority, Method> methods = ArrayListMultimap.create();
        Map<Method, Listener> reverseMap = new HashMap<>();
        for (Listener l : listeners.values()) {
            for (Method m : l.getClass().getDeclaredMethods()) {
                if (!m.isAnnotationPresent(EventHandler.class)) {
                    continue;
                }

                if (m.getParameterCount() == 1 && m.getParameterTypes()[0].equals(event.getClass())) {
                    // Bingo!
                    EventHandler annotation = m.getAnnotation(EventHandler.class);
                    EventPriority priority = annotation.priority();
                    if (priority != null) {
                        // priority must be non-null, no funny business here
                        methods.put(priority, m);
                        reverseMap.put(m, l);
                    }
                }
            }
        }

        // Execute the handlers in order of priority
        for (EventPriority priority : EventPriority.values()) {
            for (Method m : methods.get(priority)) {
                if (event instanceof Cancellable) {
                    EventHandler annotation = m.getAnnotation(EventHandler.class);
                    if (annotation.ignoreCancelled() && ((Cancellable) event).isCancelled()) {
                        continue;
                    }
                }

                try {
                    m.invoke(reverseMap.get(m), event);
                } catch (Throwable t) {
                    server.getLogger().log(Level.SEVERE, "Error while handling event " + event.getClass().getCanonicalName(), t);
                }
            }
        }
    }

    /**
     * Gets the instance of the plugin by the specified name, if it exists.
     *
     * @param name the name of the plugin
     * @return the instance of the plugin, or null if the plugin does not exist
     */
    public Plugin getPlugin(String name) {
        return plugins.get(name.toLowerCase());
    }
}

