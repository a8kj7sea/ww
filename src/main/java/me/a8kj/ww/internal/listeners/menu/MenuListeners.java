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
     * It delegates the handling of the click event to all registered menus.
     * </p>
     *
     * @param event the inventory click event.
     */
    @EventHandler
    public void handleClick(InventoryClickEvent event) {
        if (isInvalidClickEvent(event)) {
            return;
        }

        InventoryView inventoryView = event.getView();
        if (!inventoryView.getTitle().equalsIgnoreCase(legacyColorize("&bScheduled Events - List")) ||
                inventoryView.getTitle().equalsIgnoreCase(legacyColorize("&bLocations - List"))) {
            return;
        }

        event.setCancelled(true); // Cancel the event if it's a valid click
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

    // public void onInventoryClick(InventoryClickEvent event) {
    // pluginProvider.getMenus().values().forEach(menu -> menu.handleClick(event));
    // }
}
