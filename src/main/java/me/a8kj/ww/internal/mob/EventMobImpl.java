package me.a8kj.ww.internal.mob;

import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import com.google.common.base.Preconditions;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.NonNull;
import me.a8kj.ww.api.event.mob.impl.SpawnMobEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.utils.LocationsUtils;
import me.a8kj.ww.parent.utils.MathUtils;

/**
 * This class implements the {@link EventMob} interface, providing the
 * functionality
 * to manage and handle event-related mobs using MythicMobs in Bukkit.
 */
public class EventMobImpl implements EventMob {

    private final MythicBukkit mythicBukkit = MythicBukkit.inst();
    private final String name;
    private List<Location> spawnLocations;
    private ActiveMob activeMob;
    private Entity bukkitEntity;

    /**
     * Constructor for creating an instance of {@code EventMobImpl}.
     * 
     * @param name           The name of the MythicMob to be managed.
     * @param yConfiguration The YAML configuration containing mob details.
     */
    public EventMobImpl(@NonNull String name, @NonNull YamlConfiguration yConfiguration) {
        this.name = name;
        this.spawnLocations = LocationsUtils.loadLocations(yConfiguration, "spawn-locations");
        this.activeMob = null;
        this.bukkitEntity = null;
    }

    /**
     * Spawns the MythicMob at a random location from the configured spawn
     * locations.
     * If the mob is already alive, an exception is thrown.
     * 
     * @throws IllegalStateException if the mob is already alive or fails to spawn.
     */
    @Override
    public void spawn() {

        Preconditions.checkArgument(isAlive(), "Entity is already alive!");

        MythicMob mythicMob = mythicBukkit.getMobManager().getMythicMob(name)
                .orElseThrow(() -> new IllegalStateException(
                        "Couldn't find entity with this name, please make sure you have input a valid name"));

        // Pick random spawn location
        Location spawnLocation = MathUtils.pickRandomElement(spawnLocations);

        ActiveMob activeMob = mythicMob.spawn(BukkitAdapter.adapt(spawnLocation), 0);
        if (activeMob == null)
            throw new IllegalStateException("Failed to get ActiveMob!");

        Entity entity = activeMob.getEntity().getBukkitEntity();
        if (entity == null)
            throw new IllegalStateException("Failed to get BukkitEntity!");

        this.activeMob = activeMob;
        this.bukkitEntity = entity;

        Bukkit.getLogger()
                .info("[DEBUG-MODE] Entity with this name " + name + " spawned at " + spawnLocation + " successfully!");

        new SpawnMobEvent(this, spawnLocation).callEvent();
    }

    /**
     * Despawns the currently active MythicMob, if it exists and is alive.
     * 
     * @throws IllegalStateException if the mob is not alive or already despawned.
     */
    @Override
    public void despawn() {
        if (!getActiveMob().isPresent() || !getBukkitEntity().isPresent()) {
            throw new IllegalStateException(
                    "Couldn't find entity with this name. Please make sure you have input a valid name.");
        }

        if (!isValid() || !isAlive())
            throw new IllegalStateException(
                    "You cannot despawn an entity that has not spawned yet or has already died!");

        this.activeMob.despawn();
        Bukkit.getLogger().info("[DEBUG-MODE] Entity despawned successfully!");
    }

    /**
     * Returns the name of the mob being managed.
     * 
     * @return The name of the mob.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the list of spawn locations for the mob.
     * 
     * @return A list of {@link Location} where the mob can spawn.
     */
    @Override
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    /**
     * Retrieves the currently active {@link ActiveMob}.
     * 
     * @return An {@link Optional} containing the active mob, or empty if none
     *         exists.
     */
    @Override
    public Optional<ActiveMob> getActiveMob() {
        return Optional.ofNullable(activeMob);
    }

    /**
     * Retrieves the corresponding Bukkit entity of the mob.
     * 
     * @return An {@link Optional} containing the Bukkit entity, or empty if none
     *         exists.
     */
    @Override
    public Optional<Entity> getBukkitEntity() {
        return Optional.ofNullable(bukkitEntity);
    }

    /**
     * Checks if the mob is currently alive.
     * 
     * @return {@code true} if the mob is alive, {@code false} otherwise.
     */
    @Override
    public boolean isAlive() {
        return bukkitEntity != null && !bukkitEntity.isDead();
    }

    /**
     * Checks if the mob is currently valid within the game world.
     * 
     * @return {@code true} if the mob is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid() {
        return bukkitEntity != null && bukkitEntity.isValid();
    }
}
