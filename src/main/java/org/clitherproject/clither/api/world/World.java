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
package org.clitherproject.clither.api.world;

import java.util.Collection;

import org.clitherproject.clither.api.Server;
import org.clitherproject.clither.api.entity.Entity;
import org.clitherproject.clither.api.entity.EntityType;

public interface World {
	
    public Server getServer();

    public Entity spawnEntity(EntityType type);

    public Entity spawnEntity(EntityType type, Position position);

    public void removeEntity(Entity entity);

    public void removeEntity(int id);

    public Entity getEntity(int id);

    public Collection<Entity> getEntities();

}
