package com.imdamilan.spigotadditions.items;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class CustomItem {

    /**
     * Creates a new CustomItem with the given ItemStack, key and plugin, since no nKey is provided here, a hard-coded one will be used.
     * To use a custom key, use the {@link CustomItem#from(ItemStack, String, String, Plugin)} method.
     * @param item The ItemStack that will be used as a base for the CustomItem.
     * @param sKey The key of the CustomItem, must be unique, lowercase, and contain no spaces, should be in the format of "plugin_name:custom_item_name".
     * @param plugin The instance of the plugin.
     */
    public static ItemStack from(ItemStack item, String sKey, Plugin plugin) {
        return from(item, sKey, "custom_items", plugin);
    }

    /**
     * Creates a new CustomItem with the given ItemStack, keys and plugin.
     * @param item The ItemStack that will be used as a base for the CustomItem.
     * @param sKey The key of the CustomItem, must be unique, lowercase, and contain no spaces, should be in the format of "plugin_name:custom_item_name".
     * @param nKey The namespace key that persistent data will be stored under, must be unique, lowercase, and contain no spaces.
     * @param plugin The instance of the plugin.
     */
    public static ItemStack from(ItemStack item, String sKey, String nKey, Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, nKey);
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        if (meta != null) {
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, sKey);
            item.setItemMeta(meta);
        } else {
            throw new NullPointerException("The ItemMeta of the item passed to CustomItem method must not be null.");
        }
        return item;
    }

    /**
     * Checks if the given ItemStack is a CustomItem.
     * @param item The ItemStack to check.
     * @param plugin The instance of the plugin that registered the item.
     * @return True if the ItemStack is a CustomItem, false otherwise.
     */
    public static boolean isCustomItem(ItemStack item, Plugin plugin) {
        return item.hasItemMeta() && item.getItemMeta() != null
        && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "custom_items"), PersistentDataType.STRING);
    }

    /**
     * Checks if the given ItemStack is a CustomItem.
     * @param item The ItemStack to check.
     * @param nKey The namespace key that persistent data was stored under.
     * @param plugin The instance of the plugin that registered the item.
     * @return True if the ItemStack is a CustomItem, false otherwise.
     */
    public static boolean isCustomItem(ItemStack item, String nKey, Plugin plugin) {
        return item.hasItemMeta() && item.getItemMeta() != null
                && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, nKey), PersistentDataType.STRING);
    }

    /**
     * Adds a custom property to the item via PersistentDataContainer.
     * @param item The ItemStack to add the property to.
     * @param key The key of the property, must be unique, lowercase, and contain no spaces.
     * @param value The value of the property.
     * @param plugin The instance of the plugin that registered the item.
     */
    public static void addCustomProperty(ItemStack item, String key, String value, Plugin plugin) {
        NamespacedKey nKey = new NamespacedKey(plugin, key);
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.getPersistentDataContainer().set(nKey, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    /**
     * Gets the value of a custom property from the item via PersistentDataContainer.
     * @param item The ItemStack to get the property from.
     * @param key The key of the property.
     * @param plugin The instance of the plugin that registered the item.
     * @return The value of the property, or null if the property does not exist.
     */
    public static String getCustomProperty(ItemStack item, String key, Plugin plugin) {
        NamespacedKey nKey = new NamespacedKey(plugin, key);
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        return meta.getPersistentDataContainer().get(nKey, PersistentDataType.STRING);
    }
}
