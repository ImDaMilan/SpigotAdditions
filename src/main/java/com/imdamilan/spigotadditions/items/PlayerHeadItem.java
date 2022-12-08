package com.imdamilan.spigotadditions.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class PlayerHeadItem {

    private final OfflinePlayer player;
    private final ItemStack item;

    /**
     * Creates a new PlayerHeadItem with the given player and name.
     * @param player The player of the PlayerHeadItem.
     * @param name The name of the PlayerHeadItem.
     */
    public PlayerHeadItem(OfflinePlayer player, String name) {
        this.player = player;
        this.item = new ItemStackBuilder(Material.PLAYER_HEAD).name(name);
    }

    /**
     * Creates a new PlayerHeadItem with the given player.
     * @param player The player of the PlayerHeadItem.
     */
    public PlayerHeadItem(OfflinePlayer player) {
        this(player, player.getName());
    }

    /**
     * Creates a new ItemStackBuilder with the given UUID and name.
     * @param uuid The UUID of the player.
     * @param name The name of the item.
     */
    public PlayerHeadItem(UUID uuid, String name) {
        this(Bukkit.getOfflinePlayer(uuid), name);
    }

    /**
     * Creates a new PlayerHeadItem with the given UUID.
     * @param uuid The UUID of the player.
     */
    public PlayerHeadItem(UUID uuid) {
        this(Bukkit.getOfflinePlayer(uuid));
    }

    /**
     * Builds the ItemStack with the player's head.
     */
    public ItemStack get() {
        SkullMeta meta = (SkullMeta) (item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType()));
        assert meta != null;
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        return item;
    }
}
