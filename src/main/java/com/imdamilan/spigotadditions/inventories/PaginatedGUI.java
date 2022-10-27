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

    /**
     * Moves to the next page.
     * @param player The player to move the page for.
     */
    public void nextPage(HumanEntity player) {
        try {
            currentPage++;
            player.openInventory(pages.get(currentPage).getInventory());
        } catch (IndexOutOfBoundsException e) {
            currentPage--;
        }
    }

    /**
     * Returns the previous page of the PaginatedGUI.
     * @param player The player to open the inventory for.
     */
    public void previousPage(HumanEntity player) {
        try {
            currentPage--;
            player.openInventory(pages.get(currentPage).getInventory());
        } catch (IndexOutOfBoundsException e) {
            currentPage++;
        }
    }

    /**
     * Adds a page to the PaginatedGUI.
     * @param page The page to add.
     */
    public void addPage(InventoryGUI page) {
        pages.add(page);
    }
}
