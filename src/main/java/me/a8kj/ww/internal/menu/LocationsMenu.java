package me.a8kj.ww.internal.menu;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
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
 * This class extends {@link Menu} and implements the methods to define the
 * contents and settings of the menu. It handles the display of scheduled
 * events in a user-friendly manner.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public class SchedulesMenu extends Menu {

    private final List<ScheduledEvent> scheduledEvents;

    /**
     * Defines the contents of the menu based on the scheduled events.
     * 
     * @param contents a map that associates slot numbers with item builders.
     * @throws IllegalStateException if there are no scheduled events.
     */
    @Override
    public void defineContents(Map<Integer, ItemStackBuilder> contents) {
        if (scheduledEvents.isEmpty()) {
            throw new IllegalStateException("Scheduled events cannot be empty!");
        }

        final String spacing = "        "; // Define spacing constant

        for (int slot = 0; slot < scheduledEvents.size(); slot++) {
            ScheduledEvent scheduledEvent = scheduledEvents.get(slot);

            String loreLine = String.format("&fDay: &c%s\n&fTime: &2%s",
                    scheduledEvent.getDay(),
                    StringUtils.formatTime(scheduledEvent.getHours(), scheduledEvent.getMinutes()));

            contents.put(slot, new ItemStackBuilder(Material.PAPER)
                    .setName("&b&lScheduled Event")
                    .addLoreLine(spacing)
                    .addLoreLine(loreLine)
                    .addLoreLine(spacing));
        }
    }

    /**
     * Defines the settings for the menu, such as size and title.
     * 
     * @param menuSettings the settings to be configured for the menu.
     */
    @Override
    public void defineSettings(MenuSettings menuSettings) {
        menuSettings.setMenuSize(MenuSize.ROOMY);
        menuSettings.setTitle("&bScheduled Events - List");
    }

    /**
     * Handles inventory click events for the menu.
     * 
     * @param event the inventory click event.
     */
    @Override
    public void handleClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;
        if (event.getCurrentItem() == null)
            return;
        if (event.getAction() == null)
            return;
        if (event.getClick() == null)
            return;

        if (event.getInventory() == null)
            return;

        if (event.getView() == null)
            return;

        InventoryView inventoryView = event.getView();
        if (!inventoryView.getTitle().equalsIgnoreCase(getSettings().getTitle())) {
            return;
        }
        event.setCancelled(true);
    }
}
