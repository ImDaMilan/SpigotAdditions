package com.imdamilan.spigotadditions.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class LinkedGUIButton extends GUIButton {

    private final InventoryGUI to;

    /**
     * Creates a button that, when clicked, opens the gui that is linked to it.
     * @param item The item to be displayed in the inventory.
     * @param to The InventoryGUI to be opened.
     */
    protected LinkedGUIButton(ItemStack item, InventoryGUI to) {
        super(item);
        this.to = to;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.getWhoClicked().openInventory(to.getInventory());
    }
}
