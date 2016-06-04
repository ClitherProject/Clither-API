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
package org.clitherproject.clither.api.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a dependency for a {@link Plugin}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Dependency {

    /**
     * The plugin ID of the dependency.
     *
     * @return The dependency plugin ID
     * @see Plugin#id()
     */
    String id();

    /**
     * The required version range of the dependency in <b>Maven version range
     * syntax</b>:
     *
     * <table>
     * <tr><th>Range</th><th>Meaning</th></tr>
     * <tr><td>1.0</td><td>Any dependency version, 1.0 is recommended</td></tr>
     * <tr><td>[1.0]</td><td>x == 1.0</td></tr>
     * <tr><td>[1.0,)</td><td>x &gt;= 1.0</td></tr>
     * <tr><td>(1.0,)</td><td>x &gt; 1.0</td></tr>
     * <tr><td>(,1.0]</td><td>x &lt;= 1.0</td></tr>
     * <tr><td>(,1.0)</td><td>x &lt; 1.0</td></tr>
     * <tr><td>(1.0,2.0)</td><td>1.0 &lt; x &lt; 2.0</td></tr>
     * <tr><td>[1.0,2.0]</td><td>1.0 &lt;= x &lt;= 2.0</td></tr>
     * </table>
     *
     * @return The required version range, or an empty string if unspecified
     * @see <a href="https://goo.gl/edrup4">Maven version range specification</a>
     * @see <a href="https://goo.gl/WBsFIu">Maven version design document</a>
     */
    String version() default "";
    
    /**
     * The author of the dependency
     * @return The author of the dependency.
     */
    String author();

    /**
     * If this dependency is optional for the plugin to work. By default
     * this is {@code false}.
     *
     * @return True if the dependency is optional for the plugin to work
     */
    boolean optional() default false;

}
