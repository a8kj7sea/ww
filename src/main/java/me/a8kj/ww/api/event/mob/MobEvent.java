package me.a8kj.ww.api.event.mob;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.units.qual.h;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.mob.EventMob;

/**
 * Abstract class for events related to mobs in the game.
 * This class serves as a base for all mob-related events.
 */
@RequiredArgsConstructor
@Getter
public abstract class MobEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /** The EventMob associated with this event. */
    protected final EventMob eventMob;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
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
