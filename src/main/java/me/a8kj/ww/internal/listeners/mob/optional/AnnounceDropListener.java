package me.a8kj.ww.internal.listeners.mob.optional;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import me.a8kj.ww.api.event.mob.AnnounceDropEvent;
import me.a8kj.ww.internal.plugin.EventMobPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;

/**
 * Listener for handling the AnnounceDropEvent.
 * This class is responsible for broadcasting messages
 * about loot drops when a specific mob is slain.
 */
public class AnnounceDropListener implements Listener {

    /**
     * Handles the AnnounceDropEvent.
     * Checks if the drop list is not null and broadcasts the drops.
     *
     * @param event The AnnounceDropEvent containing the loot drops.
     */
    @EventHandler
    public void onDrop(AnnounceDropEvent event) {
        // Check if drops are null to avoid NullPointerException
        if (event.getDrops() == null) {
            return;
        }
        // Broadcast the drops to all players
        broadcastDrops(event.getDrops());
    }

    /**
     * Broadcasts the list of item drops to all players.
     * Formats the message to make it engaging and informative.
     *
     * @param drops The list of ItemStack representing the drops.
     */
    private void broadcastDrops(List<ItemStack> drops) {
        StringBuilder dropMessage = new StringBuilder();
        dropMessage.append("<gold>🎉 The werewolf has been slain! 🎉\n")
                .append("<red>✨ Loot Drops: ✨\n");

        // Iterate through the drops and append them to the message
        for (ItemStack drop : drops) {
            String itemName = getItemDisplayName(drop);
            dropMessage.append("<yellow>")
                    .append("- ")
                    .append(drop.getAmount())
                    .append("x ")
                    .append("<aqua>")
                    .append(itemName) // Display the item name determined by conditions
                    .append("<yellow>\n");
        }

        dropMessage.append("<green>Gather your rewards and continue your adventure!");
        // Broadcast the final message to all players
        broadcastColoredMessage(dropMessage.toString());
    }

    /**
     * Gets the display name for an item.
     * If it's an Oraxen item, use the Oraxen display name.
     * If it has a custom display name, use that.
     * Otherwise, use the default item type name with color formatting.
     *
     * @param item The ItemStack to check.
     * @return The appropriate name for display.
     */
    private String getItemDisplayName(ItemStack item) {

        ItemBuilder oraxenItem = OraxenItems.getItemById(OraxenItems.getIdByItem(item));
        if (oraxenItem != null && oraxenItem.hasItemName()) {
            return oraxenItem.getItemName();
        }

        // Check if the item has a custom display name
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }

        // Fallback to the item type name with spaces and color
        return ChatColor.AQUA + item.getType().toString().replace("_", " ").toLowerCase();

    }

    public void broadcastColoredMessage(String configMessage) {
        // Get the MiniMessage instance
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component message = miniMessage.deserialize(configMessage);

        // Send the parsed message to all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            EventMobPlugin.getPluginProvider().getAdventure().player(player).sendMessage(message);
        }
    }

}
