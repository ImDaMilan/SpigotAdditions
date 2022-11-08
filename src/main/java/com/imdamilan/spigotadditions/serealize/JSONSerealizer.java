package com.imdamilan.spigotadditions.serealize;

import org.bukkit.plugin.Plugin;

public interface JSONSerealizer<T> {

    /**
     * Serializes the given object to a JSON formatted String.
     * @param object The object to be serialized.
     * @return The JSON formatted String.
     */
    String toJson(T object);

    /**
     * Deserializes the given String to an object.
     * @param string The String to be deserialized.
     * @param plugin The instance of the plugin that is deserializing the object.
     * @return The deserialized object.
     */
    T fromJson(String string, Plugin plugin);
}
