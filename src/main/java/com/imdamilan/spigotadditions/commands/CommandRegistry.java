package com.imdamilan.spigotadditions.commands;

import com.google.common.reflect.ClassPath;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Deprecated
public class CommandRegistry {

    public static void register() {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (!plugin.getName().equals("SpigotAdditions")) {
                if (!plugin.getDescription().getDepend().contains("SpigotAdditions") && !plugin.getDescription().getSoftDepend().contains("SpigotAdditions")) continue;
            }
            try {
                for (ClassPath.ClassInfo classInfo : ClassPath.from(plugin.getClass().getClassLoader()).getTopLevelClassesRecursive(plugin.getClass().getPackage().getName())) {
                    try {
                        classInfo.load().getDeclaredMethods();
                    } catch (NoClassDefFoundError e) {
                        continue;
                    }
                    Class<?> clazz = classInfo.load();
                    if (clazz.isAnnotationPresent(Command.class)) {
                        registerCommand(new AnnotationCommand(clazz.getAnnotation(Command.class), clazz));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        syncCommands();
    }

    private static void syncCommands() {
        try {
            Class<?> craftServer = Bukkit.getServer().getClass();
            Method syncCommandsMethod = craftServer.getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerCommand(BukkitCommand command) {
        Field commandMapField;
        CommandMap commandMap = null;
        try {
            commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (commandMap != null) {
            commandMap.register(command.getLabel(), command);
        }
    }
}
