package com.imdamilan.spigotadditions.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotating a class with this annotation will make all static fields annotated with {@link Path} to be saved in the specified file using the {@link ConfigManager}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    String value() default "config.yml";
}
