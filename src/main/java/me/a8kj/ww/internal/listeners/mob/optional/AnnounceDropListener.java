package me.a8kj.ww.internal.listeners.mob.optional;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.a8kj.ww.api.event.mob.AnnounceDropEvent;
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
        dropMessage.append(ChatColor.GOLD).append("🎉 The werewolf has been slain! 🎉\n")
                .append(ChatColor.RED).append("✨ Loot Drops: ✨\n");

        // Iterate through the drops and append them to the message
        for (ItemStack drop : drops) {
            dropMessage.append(ChatColor.YELLOW)
                    .append("- ")
                    .append(drop.getAmount())
                    .append("x ")
                    .append(ChatColor.AQUA)
                    .append(drop.getType().toString().replace("_", " ")) // Replaces underscores with spaces
                    .append(ChatColor.YELLOW).append("\n");
        }

        dropMessage.append(ChatColor.GREEN).append("Gather your rewards and continue your adventure!");
        // Broadcast the final message to all players
        Bukkit.broadcastMessage(dropMessage.toString());
    }
}
