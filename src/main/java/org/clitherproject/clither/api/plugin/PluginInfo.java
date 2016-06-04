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

import org.clitherproject.clither.api.plugin.Dependency;

@Retention(RetentionPolicy.RUNTIME)
public @interface PluginInfo {

    public String name();

    public String version();

    public String author();

    public String description() default "";

    public String website() default "";
    
    Dependency[] dependencies() default {};
}
