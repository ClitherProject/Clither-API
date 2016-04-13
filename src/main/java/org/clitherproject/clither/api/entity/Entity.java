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
package org.clitherproject.clither.api.entity;

import java.awt.Color;

import org.clitherproject.clither.api.world.Position;
import org.clitherproject.clither.api.world.World;

/**
 * An abstract class that describes an entity. An entity is any object in an
 * Slither.io world.
 */
public interface Entity {

    public World getWorld();

    public int getID();

    public EntityType getType();

    public Position getPosition();

    public void setPosition(Position position);

    public Color getColor();

    public void setColor(Color color);

    public int getPhysicalSize();

    public int getMass();

    public void setMass(int mass);

    public void addMass(int mass);

}
