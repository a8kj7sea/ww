package me.a8kj.ww.api.event.game;

import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.game.EventGame;

/**
 * Abstract class for game-related events.
 * This class serves as a base for all events related to a specific game
 * instance.
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractGameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    /** The EventGame associated with this event. */
    private final @Nonnull EventGame eventGame;

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
                .info(String.format("[DEBUG-MODE] %s called successfully for game", getClass().getSimpleName()));
    }

}
