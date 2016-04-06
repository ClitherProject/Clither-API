package org.clitherproject.clither.api.plugin;

import java.util.logging.Logger;

import org.clitherproject.clither.api.Server;

public abstract class Plugin {
	
    private Server server;
    private PluginManager pluginManager;
    private boolean enabled = false;

    public void onEnable() {
        // To be overridden by implementations
    }

    public void onDisable() {
        // To be overridden by implementations
    }

    final void init(Server server, PluginManager pluginManager) {
        this.server = server;
        this.pluginManager = pluginManager;
    }

    final void setEnabled(boolean value) {
        if (enabled == value) {
            return;
        }

        boolean oldEnabled = enabled;
        enabled = value;
        if (oldEnabled && !value) {
            onDisable();
        } else if (!oldEnabled && value) {
            onEnable();
        }
    }

    public final Logger getLogger() {
        return server.getLogger();
    }

    public final PluginInfo getPluginInfo() {
        return getClass().getAnnotation(PluginInfo.class);
    }

    public final Server getServer() {
        return server;
    }

    public final PluginManager getPluginManager() {
        return pluginManager;
    }

    public final boolean isEnabled() {
        return enabled;
    }

}
