package me.a8kj.ww.internal.listeners.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

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
    public void onInventoryClick(InventoryClickEvent event) {
        pluginProvider.getMenus().values().forEach(menu -> menu.handleClick(event));
    }
}
