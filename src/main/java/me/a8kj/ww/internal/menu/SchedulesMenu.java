package me.a8kj.ww.internal.menu;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.menu.Menu;
import me.a8kj.ww.parent.entity.menu.attributes.MenuSettings;
import me.a8kj.ww.parent.entity.menu.enums.MenuSize;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;
import me.a8kj.ww.parent.utils.ItemStackBuilder;
import me.a8kj.ww.parent.utils.StringUtils;

/**
 * Represents a menu that displays scheduled events.
 *
 * <p>
 * This class extends {@link Menu} and implements methods to define the
 * contents and settings of the menu, providing a user-friendly display of
 * scheduled events.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public class SchedulesMenu extends Menu {

    private final List<ScheduledEvent> scheduledEvents;

    /**
     * Populates the menu contents based on the scheduled events.
     *
     * @param contents a map that associates slot numbers with item builders.
     * @throws IllegalStateException if the scheduled events list is empty.
     */
    @Override
    public void defineContents(Map<Integer, ItemStackBuilder> contents) {
        if (scheduledEvents.isEmpty()) {
            throw new IllegalStateException("Scheduled events cannot be empty!");
        }

        final String spacing = "        "; // Spacing for item lore

        for (int slot = 0; slot < scheduledEvents.size(); slot++) {
            ScheduledEvent scheduledEvent = scheduledEvents.get(slot);
            contents.put(slot, createEventItem(slot, scheduledEvent, spacing));
        }
    }

    /**
     * Creates an item builder for a scheduled event.
     *
     * @param slot           the slot number for the item.
     * @param scheduledEvent the scheduled event to display.
     * @param spacing        the spacing string for lore.
     * @return an ItemStackBuilder configured for the scheduled event.
     */
    private ItemStackBuilder createEventItem(int slot, ScheduledEvent scheduledEvent, String spacing) {
        String loreLine = String.format("&fDay: &c%s\n&fTime: &2%s",
                scheduledEvent.getDay(),
                StringUtils.formatTime(scheduledEvent.getHours(), scheduledEvent.getMinutes()));

        return new ItemStackBuilder(Material.PAPER)
                .setName("&b&lScheduled Event")
                .addLoreLine(spacing)
                .addLoreLine(loreLine)
                .addLoreLine(spacing);
    }

    /**
     * Configures the menu settings, such as size and title.
     *
     * @param menuSettings the settings to be applied to the menu.
     */
    @Override
    public void defineSettings(MenuSettings menuSettings) {
        menuSettings.setMenuSize(MenuSize.ROOMY);
        menuSettings.setTitle("&bScheduled Events - List");
    }

    /**
     * Handles click events within the inventory menu.
     *
     * @param event the inventory click event.
     */
    @Override
    public void handleClick(InventoryClickEvent event) {
        if (isInvalidClickEvent(event)) {
            return;
        }

        InventoryView inventoryView = event.getView();
        if (!inventoryView.getTitle().equalsIgnoreCase(getSettings().getTitle())) {
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
}
