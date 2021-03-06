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

import org.clitherproject.clither.api.SnakeOwner;
import org.clitherproject.clither.api.world.Position;

public interface Snake {
	
    public String getName();

    public void setName(String name);

    public SnakeOwner getOwner();
    
    public int getID();
    
    public int getMass();

    public Position getPosition();

    public int getPhysicalSize();

    public void setPosition(Position add);

}
