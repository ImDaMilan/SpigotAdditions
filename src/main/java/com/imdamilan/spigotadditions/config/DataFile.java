package com.imdamilan.spigotadditions.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotating a class with this annotation will make all fields annotated with {@link Path} to be saved in the specified file using the {@link ConfigManager}.
 * Each saved object will be saved in a separate section with the name of the field annotated with {@link ObjectKey}.
 * The {@link ObjectKey} field must be a String.
 * The class must have a static getObjects() method that returns an ArrayList of objects of the class to be saved.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataFile {
    String value() default "config.yml";
}
