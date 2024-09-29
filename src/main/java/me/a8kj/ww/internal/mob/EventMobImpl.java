package me.a8kj.ww.internal.mob;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import com.google.common.collect.Lists;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.NonNull;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.mob.EventMob;

public class EventMobImpl implements EventMob {

    private final MythicBukkit mythicBukkit = MythicBukkit.inst();

    private final String name;
    private List<Location> spawnLocations;

    private ActiveMob activeMob;
    private Entity bukkitEntity;

    public EventMobImpl(@NonNull String name, @NonNull YamlConfiguration yConfiguration) {
        this.name = name;
        this.spawnLocations = Lists.newArrayList();
    }

    @Override
    public void spawn() {
        if (isAlive())
            throw new IllegalStateException("entity is already alive !");

        Optional<MythicMob> mythicMob = mythicBukkit.getMobManager().getMythicMob(name);
        if (mythicBukkit.get)
    }

    @Override
    public void despawn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'despawn'");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    @Override
    public Optional<ActiveMob> getActiveMob() {
        return Optional.ofNullable(activeMob);
    }

    @Override
    public Optional<Entity> getBukkitEntity() {
        return Optional.ofNullable(bukkitEntity);
    }

    @Override
    public boolean isAlive() {
        return bukkitEntity.map(entity -> !entity.isDead())
                .orElseThrow(() -> new IllegalStateException("Entity not spawned!"));
    }

    @Override
    public boolean isValid() {
        return bukkitEntity.map(Entity::isValid).orElseThrow(() -> new IllegalStateException("Entity invalid!"));
    }

}
