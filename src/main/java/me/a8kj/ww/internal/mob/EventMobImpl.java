package me.a8kj.ww.internal.mob;

import java.util.List;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import com.google.common.collect.Lists;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.NonNull;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.utils.LocationsUtils;
import me.a8kj.ww.parent.utils.MathUtils;

public class EventMobImpl implements EventMob {

    private final MythicBukkit mythicBukkit = MythicBukkit.inst();

    private final String name;
    private List<Location> spawnLocations;

    private ActiveMob activeMob;
    private Entity bukkitEntity;

    public EventMobImpl(@NonNull String name, @NonNull YamlConfiguration yConfiguration) {
        this.name = name;
        this.spawnLocations = LocationsUtils.loadLocations(yConfiguration, "spawn-locations");
        this.activeMob = null;
        this.bukkitEntity = null;
    }

    @Override
    public void spawn() {
        if (isAlive())
            throw new IllegalStateException("entity is already alive !");

        Optional<MythicMob> mythicMob = mythicBukkit.getMobManager().getMythicMob(name);

        if (!mythicMob.isPresent()) {
            throw new IllegalStateException(
                    "Couldn't find entity with this name please make sure you had inputed a vaild name");
        }
        // pick random spawn location
        Location spawnLocation = MathUtils.pickRandomElement(spawnLocations);
        ActiveMob activeMob = mythicMob.get().spawn(BukkitAdapter.adapt(spawnLocation), 0);

        if (activeMob == null) {
            throw new IllegalStateException("Failed to get Activemob !");
        }

        Entity entity = activeMob.getEntity().getBukkitEntity();

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
        return !bukkitEntity.isDead();
    }

    @Override
    public boolean isValid() {
        return bukkitEntity.isValid();
    }

}
