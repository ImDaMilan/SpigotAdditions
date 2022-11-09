package com.imdamilan.spigotadditions.serealize;

import org.bukkit.plugin.Plugin;

public interface YAMLSerealizer<T> {

    /**
     * Serializes the given object to a YAML formatted String.
     * @param object The object to be serialized.
     * @return The YAML formatted String.
     */
    String toYaml(T object);

    /**
     * Deserializes the given String to an object.
     * @param string The String to be deserialized.
     * @return The deserialized object.
     */
    T fromYaml(String string, Plugin plugin);
}
