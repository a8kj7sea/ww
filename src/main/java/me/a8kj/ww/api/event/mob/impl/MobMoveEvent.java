package me.a8kj.ww.api.event.mob.impl;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;

import lombok.Getter;
import me.a8kj.ww.api.event.mob.MobEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;

/**
 * Event triggered when a mob moves from one location to another.
 * This event is cancellable, allowing other plugins to prevent the movement.
 */
@Getter
public class MobMoveEvent extends MobEvent implements Cancellable {

    private final Location fromLocation;
    private final Location toLocation;

    private boolean cancelled;

    /**
     * Constructor for MobMoveEvent.
     *
     * @param eventMob     The EventMob that is moving.
     * @param fromLocation The location the mob is moving from.
     * @param toLocation   The location the mob is moving to.
     */
    public MobMoveEvent(EventMob eventMob, Location fromLocation, Location toLocation) {
        super(eventMob);
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    /**
     * Checks if the event has been cancelled.
     *
     * @return true if the event is cancelled, false otherwise.
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of the event.
     *
     * @param cancel true to cancel the event, false to allow it.
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
        if (cancel)
            teleportToSafeLocation();
    }

    private void teleportToSafeLocation() {

    }
}
