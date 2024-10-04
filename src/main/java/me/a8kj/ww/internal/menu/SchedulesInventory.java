package me.a8kj.ww.internal.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;
import me.a8kj.ww.parent.utils.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SchedulesInventory {

    /**
     * Opens an inventory displaying the scheduled events.
     *
     * @param player          the player to whom the inventory will be shown.
     * @param scheduledEvents the set of scheduled events to display.
     */
    @SuppressWarnings("unchecked")
    public void openSchedulesInventory(Player player, Set<ScheduledEvent> scheduledEvents) {
        if (scheduledEvents == null || scheduledEvents.isEmpty()) {
            player.sendMessage(StringUtils.legacyColorize("&cThere are no scheduled events."));
            return;
        }

        // Create an inventory with 27 slots and a custom colored title
        Inventory inventory = Bukkit.createInventory(null, 4 * 9,
                StringUtils.legacyColorize("&bScheduled Events - List"));

        // Populate the inventory with scheduled event items
        int slot = 0;
        for (Object obj : scheduledEvents) {
            ScheduledEvent scheduledEvent;
            if (obj instanceof LinkedHashMap) {
                // Deserialize the LinkedHashMap back into a ScheduledEvent
                scheduledEvent = ScheduledEvent.deserialize((Map<String, Object>) obj);
            } else if (obj instanceof ScheduledEvent) {
                scheduledEvent = (ScheduledEvent) obj;
            } else {
                continue; // Skip if it's not a valid type
            }

            ItemStack item = createScheduledEventItem(scheduledEvent);
            inventory.setItem(slot++, item);

            if (slot >= inventory.getSize())
                break;
        }
        // Open the inventory for the player
        player.openInventory(inventory);
    }

    /**
     * Creates an ItemStack representing a scheduled event.
     *
     * @param scheduledEvent the scheduled event to represent.
     * @return the ItemStack for the scheduled event.
     */
    private ItemStack createScheduledEventItem(ScheduledEvent scheduledEvent) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // Add colorized display name and lore
            meta.setDisplayName(StringUtils.legacyColorize("&e&lScheduled Event"));
            List<String> lore = new ArrayList<>();
            lore.add(StringUtils.legacyColorize("&bDay: &f" + scheduledEvent.getDay()));
            lore.add(StringUtils
                    .legacyColorize("&bTime: &f" + formatTime(scheduledEvent.getHours(), scheduledEvent.getMinutes())));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Formats the time for the scheduled event.
     *
     * @param hours   the hours of the event.
     * @param minutes the minutes of the event.
     * @return a formatted time string.
     */
    private String formatTime(int hours, int minutes) {
        return String.format("%02d:%02d", hours, minutes);
    }
}
