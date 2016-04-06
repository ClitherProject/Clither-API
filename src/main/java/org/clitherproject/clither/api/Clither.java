/**
 * This file is part of Clither.
 *
 * Clither is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Clither is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Clither.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.clitherproject.clither.api;

import org.clitherproject.clither.api.plugin.Messenger;
import org.clitherproject.clither.api.plugin.PluginManager;
import org.clitherproject.clither.api.world.World;
import java.util.logging.Logger;

/**
 * Singleton class for convenient access to the currently running Ogar server.
 */
public class Clither {

    private static Server INSTANCE;

    /**
     * Static-use class.
     */
    private Clither() {
    }

    public static void setServer(Server server) {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton server instance!");
        }

        INSTANCE = server;
    }

    public static Server getServer() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return INSTANCE.getLogger();
    }

    public static PluginManager getPluginManager() {
        return INSTANCE.getPluginManager();
    }

    public static Messenger getMessenger() {
        return INSTANCE.getMessenger();
    }

    public static World getWorld() {
        return INSTANCE.getWorld();
    }
    
}

