package com.imdamilan.spigotadditions.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ConfigHandler {

    public static <T> void save(Plugin plugin, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            try {
                ArrayList<T> objects = (ArrayList<T>) clazz.getDeclaredMethod("getObjects").invoke(null);
                for (Object object : objects) {
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Path.class)) {
                            Path path = field.getAnnotation(Path.class);
                            try {
                                configuration.set(path.value(), field.get(object));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have a static getObjects() method that returns an ArrayList of objects!");
            }
        }
    }

    public static <T> void save(Class<T> clazz, Plugin plugin) {
        save(plugin, clazz);
    }
}
