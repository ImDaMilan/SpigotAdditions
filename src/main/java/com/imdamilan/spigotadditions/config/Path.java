package com.imdamilan.spigotadditions.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to specify the file path of a field in a class annotated with {@link DataFile} or {@link Config}.
 * Only fields who have this annotation will be saved in the file.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {
    String value() default "";
}
