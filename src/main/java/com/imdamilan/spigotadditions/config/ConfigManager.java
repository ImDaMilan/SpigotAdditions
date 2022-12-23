package com.imdamilan.spigotadditions.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ConfigManager {

    public <T> void saveToConfig(Plugin plugin, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            try {
                ArrayList<T> objects = (ArrayList<T>) clazz.getDeclaredMethod("getObjects").invoke(null);
                for (Object object : objects) {
                    String key = "";
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(ConfigKey.class)) key = (String) field.get(object);
                    }
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Path.class)) {
                            Path path = field.getAnnotation(Path.class);
                            try {
                                configuration.set(key + path.value(), field.get(object));
                                configuration.save(file);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have a static getObjects() method that returns an ArrayList of objects!");
            } catch (ClassCastException e ) {
                throw new RuntimeException("The class " + clazz.getSimpleName() + " doesn't have a valid @ConfigKey string field!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (clazz.isAnnotationPresent(StaticConfig.class)) {
            StaticConfig config = clazz.getAnnotation(StaticConfig.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Path.class)) {
                    Path path = field.getAnnotation(Path.class);
                    try {
                        configuration.set(path.value(), field.get(null));
                        configuration.save(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have a @Config or @StaticConfig annotation!");
        }
    }

    public <T> void saveToConfig(Class<T> clazz, Plugin plugin) {
        saveToConfig(plugin, clazz);
    }

    public <T> ArrayList<T> getFromConfig(Plugin plugin, Class<T> clazz) {
        if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            ArrayList<T> objects = new ArrayList<>();
            try {
                for (String key : configuration.getKeys(false)) {
                    T object = clazz.getDeclaredConstructor().newInstance();
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Path.class)) {
                            Path path = field.getAnnotation(Path.class);
                            try {
                                field.set(object, configuration.get(key + path.value()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    objects.add(object);
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return objects;
        } else if (clazz.isAnnotationPresent(StaticConfig.class)) {
            StaticConfig config = clazz.getAnnotation(StaticConfig.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Path.class)) {
                    Path path = field.getAnnotation(Path.class);
                    try {
                        field.set(null, configuration.get(path.value()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have a @Config or @StaticConfig annotation!");
        }
        return null;
    }

    public <T> ArrayList<T> getFromConfig(Class<T> clazz, Plugin plugin) {
        return getFromConfig(plugin, clazz);
    }
}
