package com.imdamilan.spigotadditions.inventories;

import com.imdamilan.spigotadditions.SpigotAdditions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class InventoryGUI implements Listener {

    private final @Getter Inventory inventory;
    private final @Getter HashMap<Integer, GUIButton> buttons = new HashMap<>();
    private final @Getter ArrayList<Integer> openSlots = new ArrayList<>();

    /**
     * Creates a new InventoryGUI from an existing Inventory.
     * @param inventory The Inventory to use.
     */
    public InventoryGUI(Inventory inventory) {
        this.inventory = inventory;
        Bukkit.getPluginManager().registerEvents(this, SpigotAdditions.getInstance());
    }

    /**
     * Creates a new InventoryGUI with the given title and size.
     * @param title The title of the inventory.
     * @param size The size of the inventory.
     */
    public InventoryGUI(String title, int size) {
        this(Bukkit.createInventory(null, size, title));
    }

    public int getFirstEmptySlot() {
        return inventory.firstEmpty();
    }

    /**
     * Adds a custom amount of buttons to the inventory.
     * @param buttons The buttons to add.
     */
    public void addButtons(GUIButton... buttons) {
        for (GUIButton button : buttons) {
            this.buttons.put(inventory.firstEmpty(), button);
            inventory.addItem(button.getItem());
        }
    }

    /**
     * Adds a button to the inventory.
     * @param slot The slot to add the button to.
     * @param button The button to add.
     */
    public void addButton(int slot, GUIButton button) {
        inventory.setItem(slot, button.getItem());
        buttons.put(slot, button);
    }

    /**
     * Removes a button from the inventory.
     * @param slot The slot to remove the button from.
     */
    public void removeButtonOrItem(int slot) {
        inventory.setItem(slot, null);
        buttons.remove(slot);
    }

    /**
     * Fills the empty slots of an inventory with a given item.
     * @param item The item to fill the inventory with.
     */
    public void fillEmptySlots(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item);
            }
        }
    }

    /**
     * Fills all the slots of an inventory with a given item, starting from a given slot, and ending at a given slot.
     * @param item The item to fill the inventory with.
     * @param start The slot to start filling from.
     * @param end The slot to stop filling at.
     */
    public void fillSlots(ItemStack item, int start, int end) {
        for (int i = start; i < end; i++) {
            inventory.setItem(i, item);
        }
    }

    /**
     * Adds all the given items to the inventory.
     * @param items The items to add.
     */
    public void addItems(ItemStack... items) {
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
    }

    /**
     * Adds all the items in the given collection to the inventory.
     * @param items The items to add.
     */
    public void addItems(Collection<ItemStack> items) {
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
    }

    /**
     * Adds the given item to the inventory in the given slot.
     * @param slot The slot to add the item to.
     * @param item The item to add.
     */
    public void addItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    /**
     * Adds the given item to the inventory in the given slot.
     * @param slot The slot to add the item to.
     * @param item The item to add.
     */
    public void addItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
    }

    /**
     * Adds the given item to the inventory in the first available slot.
     * @param item The item to add.
     */
    public void addItem(ItemStack item) {
        inventory.addItem(item);
    }

    /**
     * Opens this slot, so that items can be added/removed from it.
     * @param slot The slot to open.
     */
    public void openSlot(int slot) {
        openSlots.add(slot);
    }

    /**
     * Closes this slot, so that items can no longer be added/removed from it.
     * @param slot The slot to close.
     */
    public void closeSlot(int slot) {
        openSlots.remove(slot);
    }

    /**
     * Checks if a slot is open.
     * @param slot The slot to check.
     * @return Whether the slot is open.
     */
    public boolean isSlotOpen(int slot) {
        return openSlots.contains(slot);
    }

    /**
     * Checks if a slot is closed.
     * @param slot The slot to check.
     * @return Whether the slot is closed.
     */
    public boolean isSlotClosed(int slot) {
        return !openSlots.contains(slot);
    }

    /**
     * Opens all the slots in the inventory.
     */
    public void openAllSlots() {
        for (int i = 0; i < inventory.getSize(); i++) {
            openSlots.add(i);
        }
    }

    /**
     * Closes all the slots in the inventory.
     */
    public void closeAllSlots() {
        openSlots.clear();
    }

    /**
     * Opens the selected slots in the inventory, so that items can be added/removed from them.
     * @param slots The slots to open.
     */
    public void openSlots(int... slots) {
        for (int slot : slots) {
            openSlots.add(slot);
        }
    }

    /**
     * Closes the selected slots in the inventory, so that items can no longer be added/removed from them.
     * @param slots The slots to close.
     */
    public void closeSlots(int... slots) {
        for (int slot : slots) {
            openSlots.remove(slot);
        }
    }

    /**
     * Opens the selected slots in the inventory, so that items can be added/removed from them.
     * @param start The slot to start opening from.
     * @param end The slot to stop opening at.
     */
    public void openSlots(int start, int end) {
        for (int i = start; i < end; i++) {
            openSlots.add(i);
        }
    }

    /**
     * Opens the inventory for a given player.
     * @param player The player to open the inventory for.
     */
    public void openToPlayer(Player player) {
        player.openInventory(inventory);
    }

    /**
     * Closes the selected slots in the inventory, so that items can no longer be added/removed from them.
     * @param start The slot to start closing from.
     * @param end The slot to stop closing at.
     */
    public void closeSlots(int start, int end) {
        for (int i = start; i < end; i++) {
            openSlots.remove(i);
        }
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
        if (!openSlots.contains(event.getSlot())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!inventory.equals(event.getView().getTopInventory()) && !event.getInventory().equals(inventory)) {
            return;
        }
        for (int slot : event.getRawSlots()) {
            if (!openSlots.contains(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
