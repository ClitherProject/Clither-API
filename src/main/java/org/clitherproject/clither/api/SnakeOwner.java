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

import java.util.Collection;

import org.clitherproject.clither.api.entity.Snake;

/**
 * SnakeOwner is an interface implemented by all snake owners. In the default Clither
 * implementation, this interface is only implemented by Player. However, it can
 * be extended by plugins to allow for bots, among other uses.
 */
public interface SnakeOwner {
	
    /**
     * Gets the name of this snake owner. This name is independent of the name of
     * the owner's snakes; although all new snakes formed by this owner will use
     * this name as a default, the name of each snake can be controlled
     * individually.
     *
     * @return the owner's name
     */
    public String getName();

    /**
     * Sets this owner's name. This name is independent of the name of the
     * owner's snakes; as such, using this method will not rename current snakes,
     * but only set the default name of future snakes.
     *
     * @param name
     */
    public void setName(String name);

    /**
     * Gets all snakes that belong to this owner. The collection returned may be
     * immutable.
     *
     * @return a collection of snakes belonging to this owner
     */
    public Collection<Snake> getSnakes();

    /**
     * Adds a snake to this owner.
     *
     * @param snake the snake to add
     */
    public void addSnake(Snake snake);

    /**
     * Removes a snake from this owner.
     *
     * @param snake the snake to remove
     */
    public void removeSnake(Snake snake);

    /**
     * Removes a snake with the specified entity ID from this owner.
     *
     * @param id the entity ID of the cell to remove
     */
    public void removeSnake(int id);

}
