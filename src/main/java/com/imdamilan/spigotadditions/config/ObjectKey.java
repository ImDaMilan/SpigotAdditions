package com.imdamilan.spigotadditions.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for annotating a field in a class annotated with {@link DataFile} to specify the section/object key of the object.
 * Used to separate objects in a file, the field must be a String.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectKey {}
