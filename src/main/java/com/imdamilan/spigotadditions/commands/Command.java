package com.imdamilan.spigotadditions.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String name();
    String permission() default "";
    String usage() default "/<command>";
    String description() default "";
    String[] aliases() default {};
    String permissionMessage() default "You do not have permission to use this command.";
    int minArgs() default 0;
    boolean playerOnly() default false;
    boolean consoleOnly() default false;
}
