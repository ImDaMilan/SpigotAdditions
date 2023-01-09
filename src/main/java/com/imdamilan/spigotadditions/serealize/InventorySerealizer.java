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

    @Override
    public String toYaml(Inventory object) {
        try {
            return new YAMLMapper().writeValueAsString(new ObjectMapper().readTree(this.toJson(object)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
