package com.imdamilan.spigotadditions.utils;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemUtils {

    public static <T, Z> void setTag(ItemStack item, String key, Z value, Plugin plugin, PersistentDataType<T, Z> type) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), type, value);
        item.setItemMeta(meta);
    }

    public static <T, Z> Z getTag(ItemStack item, String key, Plugin plugin, PersistentDataType<T, Z> type) {
        return item.hasItemMeta() && item.getItemMeta() != null
                && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, key), type)
                ? item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, key), type) : null;
    }

    public static <T, Z> boolean hasTag(ItemStack item, String key, Plugin plugin, PersistentDataType<T, Z> type) {
        return item.hasItemMeta() && item.getItemMeta() != null
                && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, key), type);
    }

    public static void removeTag(ItemStack item, String key, Plugin plugin) {
        if (item.hasItemMeta() && item.getItemMeta() != null) {
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
            item.setItemMeta(meta);
        }
    }

    public static void setLore(ItemStack item, String... lore) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
    }

    public static void setLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void addLore(ItemStack item, String... lore) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        List<String> loreList = meta.getLore();
        if (loreList == null) {
            loreList = Arrays.asList(lore);
        } else {
            loreList.addAll(Arrays.asList(lore));
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void addLore(ItemStack item, List<String> lore) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        List<String> loreList = meta.getLore();
        if (loreList == null) {
            loreList = lore;
        } else {
            loreList.addAll(lore);
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void setName(ItemStack item, String displayName) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
    }

    public static void setUnbreakable(ItemStack item, boolean unbreakable) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
    }

    public static void setCustomModelData(ItemStack item, int customModelData) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
    }

    public static void setDurability(ItemStack item, int durability) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof Damageable) {
            ((Damageable) meta).setDamage(durability);
        }
        item.setItemMeta(meta);
    }

    public static void addFlags(ItemStack item, ItemFlag... flags) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
    }

    public static void removeFlags(ItemStack item, ItemFlag... flags) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.removeItemFlags(flags);
        item.setItemMeta(meta);
    }

    public static void clearFlags(ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.removeItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
    }

    public static void setEnchantment(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
    }

    public static void setEnchantment(ItemStack item, Enchantment enchantment) {
        setEnchantment(item, enchantment, 1);
    }

    public static void removeEnchantment(ItemStack item, Enchantment enchantment) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.removeEnchant(enchantment);
        item.setItemMeta(meta);
    }

    public static void clearEnchantments(ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        for (Enchantment enchantment : meta.getEnchants().keySet()) {
            meta.removeEnchant(enchantment);
        }
        item.setItemMeta(meta);
    }

    public static void setAttribute(ItemStack item, Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot... slots) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        for (EquipmentSlot slot : slots) {
            meta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), amount, operation, slot));
        }
        item.setItemMeta(meta);
    }

    public static void setAttribute(ItemStack item, Attribute attribute, double amount) {
        setAttribute(item, attribute, amount, AttributeModifier.Operation.ADD_NUMBER);
    }

    public static void removeAttribute(ItemStack item, Attribute attribute) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        meta.removeAttributeModifier(attribute);
        item.setItemMeta(meta);
    }

    public static void clearAttributes(ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta.getAttributeModifiers() == null) return;
        for (Attribute attribute : meta.getAttributeModifiers().keySet()) {
            meta.removeAttributeModifier(attribute);
        }
        item.setItemMeta(meta);
    }

    public static void setPotionEffect(ItemStack item, PotionEffectType type, int duration, int amplifier) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).addCustomEffect(new PotionEffect(type, duration, amplifier), true);
        }
        item.setItemMeta(meta);
    }

    public static void removePotionEffect(ItemStack item, PotionEffectType type) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof PotionMeta) {
            ((PotionMeta) meta).removeCustomEffect(type);
        }
        item.setItemMeta(meta);
    }

    public static void clearPotionEffects(ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof PotionMeta) {
            for (PotionEffectType type : ((PotionMeta) meta).getCustomEffects().stream().map(PotionEffect::getType).toList()) {
                ((PotionMeta) meta).removeCustomEffect(type);
            }
        }
        item.setItemMeta(meta);
    }

    public static void setFireworkEffect(ItemStack item, FireworkEffect effect) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof FireworkMeta) {
            ((FireworkMeta) meta).addEffect(effect);
        }
        item.setItemMeta(meta);
    }

    public static void removeFireworkEffect(ItemStack item, int index) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof FireworkMeta) {
            ((FireworkMeta) meta).removeEffect(index);
        }
        item.setItemMeta(meta);
    }

    public static void clearFireworkEffects(ItemStack item) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof FireworkMeta) {
            ((FireworkMeta) meta).clearEffects();
        }
        item.setItemMeta(meta);
    }

    public static void setFireworkPower(ItemStack item, int power) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof FireworkMeta) {
            ((FireworkMeta) meta).setPower(power);
        }
        item.setItemMeta(meta);
    }

    public static void setBookPages(ItemStack item, String... pages) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof BookMeta) {
            ((BookMeta) meta).setPages(pages);
        }
        item.setItemMeta(meta);
    }

    public static void setBookPages(ItemStack item, List<String> pages) {
        setBookPages(item, pages.toArray(new String[0]));
    }

    public static void setBookAuthor(ItemStack item, String author) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof BookMeta) {
            ((BookMeta) meta).setAuthor(author);
        }
        item.setItemMeta(meta);
    }

    public static void setBookTitle(ItemStack item, String title) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof BookMeta) {
            ((BookMeta) meta).setTitle(title);
        }
        item.setItemMeta(meta);
    }

    public static void setBookGeneration(ItemStack item, BookMeta.Generation generation) {
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        assert meta != null;
        if (meta instanceof BookMeta) {
            ((BookMeta) meta).setGeneration(generation);
        }
        item.setItemMeta(meta);
    }

    public static boolean isItem(ItemStack item) {
        return item.getType().isItem();
    }

    public static boolean isValidItem(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }

    public static boolean isArmor(ItemStack item) {
        return item.getType().name().endsWith("_HELMET") || item.getType().name().endsWith("_CHESTPLATE") || item.getType().name().endsWith("_LEGGINGS") || item.getType().name().endsWith("_BOOTS");
    }

    public static boolean isHelmet(ItemStack item) {
        return item.getType().name().endsWith("_HELMET");
    }

    public static boolean isChestplate(ItemStack item) {
        return item.getType().name().endsWith("_CHESTPLATE");
    }

    public static boolean isLeggings(ItemStack item) {
        return item.getType().name().endsWith("_LEGGINGS");
    }

    public static boolean isBoots(ItemStack item) {
        return item.getType().name().endsWith("_BOOTS");
    }

    public static boolean isSword(ItemStack item) {
        return item.getType().name().endsWith("_SWORD");
    }

    public static boolean isAxe(ItemStack item) {
        return item.getType().name().endsWith("_AXE");
    }

    public static boolean isPickaxe(ItemStack item) {
        return item.getType().name().endsWith("_PICKAXE");
    }

    public static boolean isShovel(ItemStack item) {
        return item.getType().name().endsWith("_SHOVEL");
    }

    public static boolean isHoe(ItemStack item) {
        return item.getType().name().endsWith("_HOE");
    }

    public static boolean isTool(ItemStack item) {
        return isSword(item) || isAxe(item) || isPickaxe(item) || isShovel(item) || isHoe(item);
    }

    public static boolean isBow(ItemStack item) {
        return item.getType() == Material.BOW;
    }

    public static boolean isInBucket(ItemStack item) {
        return item.getType().name().endsWith("_BUCKET");
    }

    public static boolean isFood(ItemStack item) {
        return item.getType().isEdible();
    }

    public static boolean isDrink(ItemStack item) {
        return item.getType().isEdible() && item.getType().getMaxStackSize() == 1;
    }

    public static boolean isBlock(ItemStack item) {
        return item.getType().isBlock();
    }

    public static boolean isSolidBlock(ItemStack item) {
        return item.getType().isBlock() && item.getType().isSolid();
    }

    public static boolean isCrop(ItemStack item) {
        Material type = item.getType();
        return type.equals(Material.WHEAT) || type.equals(Material.CARROT) || type.equals(Material.POTATO) || type.equals(Material.BEETROOT) || type.equals(Material.NETHER_WART) || type.equals(Material.COCOA) || type.equals(Material.SUGAR_CANE) || type.equals(Material.CACTUS) || type.equals(Material.MELON_STEM) || type.equals(Material.PUMPKIN_STEM);
    }
}
