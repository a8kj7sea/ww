package me.a8kj.ww.internal.listeners.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

import static me.a8kj.ww.parent.utils.StringUtils.legacyColorize;

/**
 * Listener for handling menu interactions.
 *
 * <p>
 * This class implements the {@link Listener} interface to respond to
 * inventory click events in the menus.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public class MenuListeners implements Listener {

    private final PluginProvider pluginProvider;

    /**
     * Handles inventory click events.
     *
     * <p>
     * This method is triggered whenever a player clicks in an inventory.
     * It cancels the event to prevent any item movement or interaction.
     * </p>
     *
     * @param event the inventory click event.
     */
    @EventHandler
    public void handleClick(InventoryClickEvent event) {
        // Check if the clicked inventory is valid
        if (isInvalidClickEvent(event)) {
            return;
        }

        InventoryView inventoryView = event.getView();
        // Check if the inventory is one of the protected menus
        if (isProtectedInventory(inventoryView)) {
            event.setCancelled(true); // Cancel the event to prevent any interaction
        }
    }

    /**
     * Checks if the inventory is protected.
     *
     * @param inventoryView the inventory view to check.
     * @return true if the inventory is protected; otherwise, false.
     */
    private boolean isProtectedInventory(InventoryView inventoryView) {
        return inventoryView.getTitle().equalsIgnoreCase(legacyColorize("&bScheduled Events - List")) ||
                inventoryView.getTitle().equalsIgnoreCase(legacyColorize("&eLocations - List"));
    }

    /**
     * Checks if the click event is valid.
     *
     * @param event the inventory click event.
     * @return true if the event is invalid; otherwise, false.
     */
    private boolean isInvalidClickEvent(InventoryClickEvent event) {
        return event.getClickedInventory() == null ||
                event.getCurrentItem() == null ||
                event.getAction() == null ||
                event.getClick() == null ||
                event.getInventory() == null ||
                event.getView() == null;
    }
}
