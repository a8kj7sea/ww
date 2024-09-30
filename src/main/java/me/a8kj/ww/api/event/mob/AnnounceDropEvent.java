package me.a8kj.ww.api.event.mob;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents an event that is triggered when a mob drops items.
 * This event can be used to announce or handle the drops of a specific mob.
 */
@RequiredArgsConstructor
@Getter
public class AnnounceDropEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /**
     * A list of ItemStack representing the items dropped by the mob.
     */
    private final List<ItemStack> drops;

    /**
     * Returns the handler list for this event.
     *
     * @return The HandlerList containing all registered handlers for this event.
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Calls the event through the Bukkit plugin manager.
     * Logs a debug message indicating that the event was called successfully.
     */
    public void callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        Bukkit.getLogger()
                .info(String.format("[DEBUG-MODE] %s called successfully for mob", getClass().getSimpleName()));
    }
}
