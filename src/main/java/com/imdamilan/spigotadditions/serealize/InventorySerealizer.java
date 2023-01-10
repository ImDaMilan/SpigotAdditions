package com.imdamilan.spigotadditions.serealize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class InventorySerealizer implements JSONSerealizer<Inventory>, YAMLSerealizer<Inventory> {

    /**
     * Serializes an inventory to a JSON string.
     * @param inventory The object to be serialized.
     * @return The JSON string.
     */
    @Override
    public String toJson(Inventory inventory) {
        ItemSerealizer is = new ItemSerealizer();
        JsonArray array = new JsonArray();
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null && !Objects.requireNonNull(inventory.getItem(i)).getType().isAir()) {
                array.add(is.toJson(Objects.requireNonNull(inventory.getItem(i))));
            }
        }
        return new Gson().toJson(array);
    }

    /**
     * Deserializes an inventory from a JSON string.
     * @param string The String to be deserialized.
     * @param plugin The instance of the plugin that is deserializing the object.
     * @return The deserialized object.
     */
    @Override
    public Inventory fromJson(String string, Plugin plugin) {
        ItemSerealizer is = new ItemSerealizer();
        JsonArray array = new Gson().fromJson(string, JsonArray.class);
        Inventory inventory = plugin.getServer().createInventory(null, array.size());
        for (int i = 0; i < array.size(); i++) {
            inventory.setItem(i, is.fromJson(array.get(i).toString(), plugin));
        }
        return inventory;
    }

    /**
     * Serializes an inventory to a YAML string.
     * @param object The object to be serialized.
     * @return The YAML string.
     */
    @Override
    public String toYaml(Inventory object) {
        try {
            return new YAMLMapper().writeValueAsString(new ObjectMapper().readTree(this.toJson(object)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes an inventory from a YAML string.
     * @param string The String to be deserialized.
     * @param plugin The instance of the plugin that is deserializing the object.
     * @return The deserialized object.
     */
    @Override
    public Inventory fromYaml(String string, Plugin plugin) {
        try {
            ObjectMapper reader = new ObjectMapper(new YAMLFactory());
            Object obj = reader.readValue(string, Object.class);
            ObjectMapper writer = new ObjectMapper();
            return this.fromJson(writer.writeValueAsString(obj), plugin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
