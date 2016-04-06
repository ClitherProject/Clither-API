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
