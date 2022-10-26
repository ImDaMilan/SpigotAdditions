package com.imdamilan.spigotadditions.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EnchantmentWrapper extends Enchantment {

    private final String name;
    private final int maxLevel;
    private final ArrayList<Enchantment> conflicts = new ArrayList<>();
    private final boolean treasure;
    private final boolean cursed;
    private final EnchantmentTarget target;

    /**
     * Creates a new custom enchantment registered as a Minecraft enchantment.
     * @param key The key of the enchantment, all lowercase, no spaces
     * @param name The name of the enchantment
     * @param maxLevel The maximum level of the enchantment
     * @param treasure Whether the enchantment is a treasure enchantment
     * @param cursed Whether the enchantment is a cursed enchantment
     * @param target The enchantment target
     * @param conflicts The enchantments that conflict with this enchantment
     */
    public EnchantmentWrapper(String key, String name, int maxLevel, boolean treasure, boolean cursed, EnchantmentTarget target, Enchantment... conflicts) {
        super(NamespacedKey.minecraft(key));
        this.name = name;
        this.maxLevel = maxLevel;
        this.treasure = treasure;
        this.cursed = cursed;
        this.target = target;
        Collections.addAll(this.conflicts, conflicts);
        try {
            if (!Arrays.asList(Enchantment.values()).contains(this)) {
                Field fieldAcceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
                fieldAcceptingNew.setAccessible(true);
                fieldAcceptingNew.set(null, true);
                fieldAcceptingNew.setAccessible(false);
                Enchantment.registerEnchantment(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new custom enchantment registering it as a plugin enchantment.
     * @param plugin The plugin that is registering the enchantment
     * @param key The key of the enchantment, all lowercase, no spaces
     * @param name The name of the enchantment
     * @param maxLevel The maximum level of the enchantment
     * @param treasure Whether the enchantment is a treasure enchantment
     * @param cursed Whether the enchantment is a cursed enchantment
     * @param target The enchantment target
     * @param conflicts The enchantments that conflict with this enchantment
     */
    public EnchantmentWrapper(Plugin plugin, String key, String name, int maxLevel, boolean treasure, boolean cursed, EnchantmentTarget target, Enchantment... conflicts) {
        super(new NamespacedKey(plugin, key));
        this.name = name;
        this.maxLevel = maxLevel;
        this.treasure = treasure;
        this.cursed = cursed;
        this.target = target;
        Collections.addAll(this.conflicts, conflicts);
        try {
            Field fieldAcceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            fieldAcceptingNew.setAccessible(true);
            fieldAcceptingNew.set(null, true);
            fieldAcceptingNew.setAccessible(false);
            Enchantment.registerEnchantment(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return target;
    }

    @Override
    public boolean isTreasure() {
        return treasure;
    }

    @Override
    public boolean isCursed() {
        return cursed;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return conflicts.contains(other);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return target.includes(item);
    }
}
