package com.imdamilan.spigotadditions.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ConfigManager {

    /**
     * Saves all the fields annotated with {@link Path} in the specified class with the {@link Config} or {@link DataFile} annotation to the specified file.
     * @param plugin The plugin instance.
     * @param clazz The class with the annotated fields.
     * @param <T> The type of the class.
     */
    public <T> void saveToConfig(Plugin plugin, Class<T> clazz) {
        if (clazz.isAnnotationPresent(DataFile.class)) {
            DataFile dataFile = clazz.getAnnotation(DataFile.class);
            String name = dataFile.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            try {
                ArrayList<T> objects = (ArrayList<T>) clazz.getDeclaredMethod("getObjects").invoke(null);
                for (Object object : objects) {
                    String key = "";
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(ObjectKey.class)) key = (String) field.get(object);
                    }
                    for (Field field : clazz.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Path.class)) {
                            Path path = field.getAnnotation(Path.class);
                            String name1 = path.value();
                            if (name1.isBlank() || name1.isEmpty()) name1 = field.getName();
                            try {
                                configuration.set(key + "." + name1, field.get(object));
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
                throw new RuntimeException("The class " + clazz.getSimpleName() + " doesn't have a valid @ObjectKey string field!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Path.class)) {
                    Path path = field.getAnnotation(Path.class);
                    String name1 = path.value();
                    if (name1.isBlank() || name1.isEmpty()) name1 = field.getName();
                    try {
                        configuration.set(name1, field.get(null));
                        configuration.save(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have an @DataFile or @Config annotation!");
        }
    }

    /**
     * Loads all the fields annotated with {@link Path} in the specified class with the {@link Config} or {@link DataFile} annotation from the specified file.
     * @param clazz The class with the annotated fields.
     * @param plugin The plugin instance.
     * @param <T> The type of the class.
     */
    public <T> void saveToConfig(Class<T> clazz, Plugin plugin) {
        saveToConfig(plugin, clazz);
    }

    /**
     * Loads all the fields annotated with {@link Path} in the specified class with the {@link Config} or {@link DataFile} annotation from the specified file.
     * @param plugin The plugin instance.
     * @param clazz The class with the annotated fields.
     * @return An ArrayList of objects of the specified class if it is a DataFile, or null if it is a Config class.
     * @param <T> The type of the class.
     */
    public <T> ArrayList<T> getFromConfig(Plugin plugin, Class<T> clazz) {
        if (clazz.isAnnotationPresent(DataFile.class)) {
            DataFile dataFile = clazz.getAnnotation(DataFile.class);
            String name = dataFile.value();
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
                            String name1 = path.value();
                            if (name1.isBlank() || name1.isEmpty()) name1 = field.getName();
                            try {
                                field.set(object, configuration.get(key + "." + name1));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    objects.add(object);
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have a default no-args constructor!");
            }
            return objects;
        } else if (clazz.isAnnotationPresent(Config.class)) {
            Config config = clazz.getAnnotation(Config.class);
            String name = config.value();
            File file = new File(plugin.getDataFolder(), name);
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Path.class)) {
                    Path path = field.getAnnotation(Path.class);
                    String name1 = path.value();
                    if (name1.isBlank() || name1.isEmpty()) name1 = field.getName();
                    try {
                        field.set(null, configuration.get(name1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new RuntimeException("The class " + clazz.getSimpleName() + " does not have an @DataFile or @Config annotation!");
        }
        return null;
    }

    /**
     * Loads all the fields annotated with {@link Path} in the specified class with the {@link Config} or {@link DataFile} annotation from the specified file.
     * @param clazz The class with the annotated fields.
     * @param plugin The plugin instance.
     * @return An ArrayList of objects of the specified class if it is a DataFile, or null if it is a Config class.
     * @param <T> The type of the class.
     */
    public <T> ArrayList<T> getFromConfig(Class<T> clazz, Plugin plugin) {
        return getFromConfig(plugin, clazz);
    }
}
