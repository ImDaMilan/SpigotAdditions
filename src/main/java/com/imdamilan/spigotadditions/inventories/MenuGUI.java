package com.imdamilan.spigotadditions.inventories;

import com.imdamilan.spigotadditions.SpigotAdditions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuGUI implements Listener {

    private final @Getter InventoryType type;
    private final @Getter String title;
    private final @Getter HashMap<Integer, GUIButton> buttons = new HashMap<>();
    private final @Getter ArrayList<Integer> openSlots = new ArrayList<>();
    private final @Getter Inventory inventory;

    public MenuGUI(InventoryType type, String title) {
        this.type = type;
        this.title = title;
        Bukkit.getPluginManager().registerEvents(this, SpigotAdditions.getInstance());
        this.inventory = Bukkit.createInventory(null, type, title);
    }

    public MenuGUI(String title) {
        this(InventoryType.CHEST, title);
    }

    public void open(Player player) {
        player.openInventory(getInventory());
    }

    public void addButton(GUIButton button) {
        int slot = getInventory().firstEmpty();
        buttons.put(slot, button);
        openSlots.remove(slot);
    }

    public void addButton(GUIButton button, int slot) {
        buttons.put(slot, button);
        openSlots.remove(slot);
    }

    public void removeButton(int slot) {
        buttons.remove(slot);
        openSlots.add(slot);
    }

    public void clearButtons() {
        buttons.clear();
        openSlots.clear();
    }

    public void fillEmptySlots() {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, InventoryGUI.getFillerItem());
            }
        }
    }

    public void fillEmptySlots(ItemStack item) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, item);
            }
        }
    }

    public void fillEmptySlots(GUIButton button) {
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, button.getItem());
            }
        }
    }

    public void fill(int start, int end, ItemStack item) {
        for (int i = start; i < end; i++) {
            getInventory().setItem(i, item);
        }
    }

    public void fill(int start, int end, GUIButton button) {
        for (int i = start; i < end; i++) {
            getInventory().setItem(i, button.getItem());
        }
    }

    public void clear() {
        getInventory().clear();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!inventory.equals(event.getView().getTopInventory()) && !event.getClickedInventory().equals(inventory)) {
            return;
        }
        if (buttons.containsKey(event.getSlot())) {
            buttons.get(event.getSlot()).onClick(event);
            event.setCancelled(true);
            return;
        }
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            return;
        }
        if (!openSlots.contains(event.getSlot()) && event.getSlot() < inventory.getSize()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!inventory.equals(event.getView().getTopInventory()) && !event.getInventory().equals(inventory)) {
            return;
        }
        for (int slot : event.getRawSlots()) {
            if (!openSlots.contains(slot) && slot < inventory.getSize()) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
