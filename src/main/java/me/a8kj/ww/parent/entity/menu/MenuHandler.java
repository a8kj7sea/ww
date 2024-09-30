package me.a8kj.ww.parent.entity.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

/**
 * Interface for handling inventory menus in the plugin.
 * 
 * <p>
 * This interface defines methods for handling various inventory events, such as
 * clicks, opening, and closing. Implementing classes should provide specific
 * behavior for these events.
 * </p>
 */
public interface MenuHandler {

    /**
     * Handles inventory click events.
     *
     * <p>
     * This method is called when an item in the inventory is clicked.
     * Implementing classes should define the specific behavior for
     * inventory clicks.
     * </p>
     *
     * @param event the InventoryClickEvent containing details about the click
     *              event.
     * @throws IllegalStateException if this method is not implemented.
     */
    default void handleClick(InventoryClickEvent event) {
        throw new IllegalStateException("Not implemented");
    }

    /**
     * Handles inventory open events.
     *
     * <p>
     * This method is called when the inventory is opened. Implementing classes
     * should define the specific behavior for inventory openings.
     * </p>
     *
     * @param event the InventoryOpenEvent containing details about the open
     *              event.
     * @throws IllegalStateException if this method is not implemented.
     */
    default void handleOpen(InventoryOpenEvent event) {
        throw new IllegalStateException("Not implemented");
    }

    /**
     * Handles inventory close events.
     *
     * <p>
     * This method is called when the inventory is closed. Implementing classes
     * should define the specific behavior for inventory closures.
     * </p>
     *
     * @param event the InventoryCloseEvent containing details about the close
     *              event.
     * @throws IllegalStateException if this method is not implemented.
     */
    default void handleClose(InventoryCloseEvent event) {
        throw new IllegalStateException("Not implemented");
    }
}
