package com.imdamilan.spigotadditions.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Objects;

public class ItemStackBuilder extends ItemStack {

    /**
     * Creates a new ItemStackBuilder with the given ItemStack.
     * @param item The ItemStack of the ItemStackBuilder.
     */
    public ItemStackBuilder(ItemStack item) {
        super(item);
    }

    /**
     * Creates a new ItemStackBuilder with the given Material and amount.
     * @param item The Material of the ItemStackBuilder.
     * @param amount The amount of the ItemStackBuilder.
     */
    public ItemStackBuilder(Material item, int amount) {
        super(item, amount);
    }

    /**
     * Creates a new ItemStackBuilder with the given Material.
     * @param item The Material of the ItemStackBuilder.
     */
    public ItemStackBuilder(Material item) {
        super(item);
    }

    /**
     * Sets the amount of the items in the ItemStackBuilder.
     * @param amount New amount of items in this stack
     */
    public ItemStackBuilder amount(int amount) {
        super.setAmount(amount);
        return this;
    }

    /**
     * Sets the display name of the ItemStack.
     * @param name The new display name of the ItemStack.
     */
    public ItemStackBuilder name(String name) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.setDisplayName(name);
        super.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the lore of the ItemStack.
     * @param lore The new lore of the ItemStack.
     */
    public ItemStackBuilder lore(String... lore) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.setLore(Arrays.asList(lore));
        super.setItemMeta(meta);
        return this;
    }

    /**
     * Adds a new lore line to the ItemStack.
     * @param lore The new lore line of the ItemStack.
     */
    public ItemStackBuilder addLore(String... lore) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        Objects.requireNonNull(meta.getLore()).addAll(Arrays.asList(lore));
        super.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the lore line at the given index of the ItemStack.
     * @param index The index of the lore line.
     * @param lore The new lore line of the ItemStack.
     */
    public ItemStackBuilder lore(int index, String lore) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        Objects.requireNonNull(meta.getLore()).set(index, lore);
        super.setItemMeta(meta);
        return this;
    }

    /**
     * Adds an attribute modifier to the ItemStack.
     * @param attribute The attribute of the modifier.
     * @param modifier The modifier of the attribute.
     */
    public ItemStackBuilder addAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.addAttributeModifier(attribute, modifier);
        super.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the custom model data of the ItemStack.
     * @param modelData The new custom model data of the ItemStack.
     */
    public ItemStackBuilder modelData(int modelData) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.setCustomModelData(modelData);
        super.setItemMeta(meta);
        return this;
    }

    /**
     * Adds an enchantment to the ItemStack.
     * @param enchantment The enchantment to add.
     * @param level The level of the enchantment.
     */
    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        super.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds an enchantment to the ItemStack.
     * @param enchantment The enchantment to add.
     */
    public ItemStackBuilder enchant(Enchantment enchantment) {
        super.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    /**
     * Removes an enchantment from the ItemStack.
     * @param enchantment The enchantment to remove.
     */
    public ItemStackBuilder removeEnchant(Enchantment enchantment) {
        super.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Clears all enchantments from the ItemStack.
     */
    public ItemStackBuilder clearEnchants() {
        super.getEnchantments().forEach((enchantment, integer) -> super.removeEnchantment(enchantment));
        return this;
    }

    /**
     * Adds a persistent data tag to the ItemStack.
     * @param key The namespaced key of the persistent data tag.
     * @param type The type of the persistent data tag.
     * @param data The data of the persistent data tag.
     */
    public <T, Z> ItemStackBuilder addTag(NamespacedKey key, PersistentDataType<T, Z> type, Z data) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.getPersistentDataContainer().set(key, type, data);
        super.setItemMeta(meta);
        return this;
    }
}
