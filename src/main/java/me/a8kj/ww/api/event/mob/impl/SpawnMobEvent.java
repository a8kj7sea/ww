package me.a8kj.ww.api.event.mob.impl;

import lombok.Getter;
import me.a8kj.ww.api.event.mob.MobEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;

/**
 * Represents an event that signifies the spawning of a mob.
 * This event is triggered when a mob is successfully spawned in the game.
 */
@Getter
public class SpawnMobEvent extends MobEvent {

    /**
     * Constructs a SpawnMobEvent with the specified EventMob.
     *
     * @param mob The EventMob that is being spawned.
     */
    public SpawnMobEvent(EventMob mob) {
        super(mob);
    }
}
