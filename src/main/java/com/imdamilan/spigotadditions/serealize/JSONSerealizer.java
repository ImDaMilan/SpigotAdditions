package com.imdamilan.spigotadditions.serealize;

import org.bukkit.plugin.Plugin;

public interface JSONSerealizer<T> {
    String toJson(T object);
    T fromJson(String string, Plugin plugin);
}
