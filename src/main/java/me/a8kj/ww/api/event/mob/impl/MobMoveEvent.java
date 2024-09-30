package me.a8kj.ww.api.event.mob.impl;

import org.bukkit.Location;

import lombok.Getter;
import me.a8kj.ww.api.event.mob.MobEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;

/**
 * Event triggered when a mob moves from one location to another.
 */

@Getter
public class MobMoveEvent extends MobEvent {

    private final Location fromLocation; // The location the mob is moving from
    private final Location toLocation; // The location the mob is moving to

    /**
     * Constructor for MobMoveEvent.
     *
     * @param eventMob     The EventMob that is moving.
     * @param fromLocation The location the mob is moving from.
     * @param toLocation   The location the mob is moving to.
     */
    public MobMoveEvent(EventMob eventMob, Location fromLocation, Location toLocation) {
        super(eventMob); // Call the constructor of MobEvent
        this.fromLocation = fromLocation; // Initialize fromLocation
        this.toLocation = toLocation; // Initialize toLocation
    }
}
