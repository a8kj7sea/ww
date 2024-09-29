package me.a8kj.ww.api.event.mob.impl;

import org.bukkit.Location;

import lombok.Getter;
import me.a8kj.ww.api.event.mob.MobEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;

@Getter
public class SpawnMobEvent extends MobEvent {

    private final Location spawnLocation;

    public SpawnMobEvent(EventMob mob, Location location) {
        super(mob);
        this.spawnLocation = location;
    }
}
