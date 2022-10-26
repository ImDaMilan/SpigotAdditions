package com.imdamilan.spigotadditions.inventories;

import lombok.Getter;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class PaginatedGUI {

    private final @Getter ArrayList<InventoryGUI> pages;
    private @Getter int currentPage = 0;

    /**
     * Creates a new empty PaginatedGUI.
     */
    public PaginatedGUI() {
        pages = new ArrayList<>();
    }

    /**
     * Creates a new PaginatedGUI from an existing InventoryGUI or InventoryGUIs.
     * @param pages The pages/GUIs to add.
     */
    public PaginatedGUI(InventoryGUI... pages) {
        this.pages = new ArrayList<>(Arrays.asList(pages));
    }

    public void nextPage(HumanEntity player) {
        try {
            currentPage++;
            player.openInventory(pages.get(currentPage).getInventory());
        } catch (IndexOutOfBoundsException e) {
            currentPage--;
        }
    }
}
