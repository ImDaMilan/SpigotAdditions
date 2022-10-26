package com.imdamilan.spigotadditions.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class CustomItem {

    private ItemStack item;

    /**
     * Creates a new CustomItem with the given material and name.
     * @param item The ItemStack that will be used as a base for the CustomItem.
     * @param sKey The key of the CustomItem, must be unique, lowercase, and contain no spaces, should be in the format of "plugin_name:custom_item_name".
     * @param plugin The instance of the plugin.
     */
    public CustomItem(ItemStack item, String sKey, Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, "custom_items");
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, sKey);
            item.setItemMeta(meta);
        } else {
            throw new NullPointerException("The ItemMeta of the item passed to CustomItem constructor must not be null.");
        }
    }

    /**
     * Builds the ItemStack.
     */
    public ItemStack get() {
        return item;
    }

    /**
     * Checks if the given ItemStack is a CustomItem.
     * @param item The ItemStack to check.
     * @param plugin The instance of the plugin.
     * @return True if the ItemStack is a CustomItem, false otherwise.
     */
    public static boolean isCustomItem(ItemStack item, Plugin plugin) {
        return item.hasItemMeta() && item.getItemMeta() != null
        && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "custom_items"), PersistentDataType.STRING);
    }
}
