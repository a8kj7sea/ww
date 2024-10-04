package me.a8kj.ww.api.event.mob.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import java.util.function.Consumer;

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
     * Consumer to handle teleportation logic, can be injected from outside
     */
    private Consumer<Location> teleportationHandler;

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
        this.cancelled = false;

        /**
         * Default teleportation handler just teleports to the provided location
         */
        this.teleportationHandler = this::defaultTeleportationHandler;
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
        if (cancel && teleportationHandler != null) {
            teleportationHandler.accept(fromLocation);
        }
    }

    /**
     * Default teleportation logic: teleport the mob to the provided location.
     *
     * @param safeLocation The location to teleport the mob to.
     */
    private void defaultTeleportationHandler(Location safeLocation) {
        if (safeLocation != null && eventMob.getBukkitEntity().isPresent()) {
            eventMob.getBukkitEntity().get().teleport(safeLocation);
        }
    }

    /**
     * Set a custom teleportation handler.
     *
     * @param handler The custom Consumer that handles teleportation.
     */
    public void setTeleportationHandler(Consumer<Location> handler) {
        this.teleportationHandler = handler;
    }

    @Override
    public void callEvent() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
