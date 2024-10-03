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
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.utils.LocationsUtils;
import me.a8kj.ww.parent.utils.MathUtils;

/**
 * Implementation of the {@link EventMob} interface to manage and handle
 * event-related
 * mobs using MythicMobs in Bukkit.
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
     * @param yConfiguration The YAML configuration containing mob details,
     *                       including spawn locations.
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
     * 
     * @return true if the mob is spawned successfully.
     * @throws IllegalStateException if the mob is already alive, cannot be found,
     *                               or if spawning fails.
     */
    @Override
    public boolean spawn() {
        checkIfAlreadyAlive();
        MythicMob mythicMob = getMythicMob();
        Location spawnLocation = getRandomSpawnLocation();
        spawnMob(mythicMob, spawnLocation);
        return true;
    }

    /**
     * Despawns the currently active MythicMob, if it exists and is alive.
     * 
     * @return true if the mob is successfully despawned.
     * @throws IllegalStateException if the mob is not alive or does not exist.
     */
    @Override
    public boolean despawn() {
        checkIfCanDespawn();
        performDespawn();
        return true;
    }

    /**
     * Retrieves the name of the mob being managed.
     * 
     * @return the name of the MythicMob.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieves the list of spawn locations for the mob.
     * 
     * @return a list of {@link Location} objects where the mob can spawn.
     */
    @Override
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    /**
     * Retrieves the currently active {@link ActiveMob}.
     * 
     * @return an {@link Optional} containing the active mob, or empty if none
     *         exists.
     */
    @Override
    public Optional<ActiveMob> getActiveMob() {
        return Optional.ofNullable(activeMob);
    }

    /**
     * Retrieves the corresponding Bukkit entity of the mob.
     * 
     * @return an {@link Optional} containing the Bukkit entity, or empty if none
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

    /**
     * Ensures that the mob is not already alive before attempting to spawn it.
     * 
     * @throws IllegalStateException if the mob is already alive.
     */
    private void checkIfAlreadyAlive() {
        Preconditions.checkArgument(!isAlive(), "Entity is already alive!");
    }

    /**
     * Retrieves the MythicMob instance based on the configured name.
     * 
     * @return the {@link MythicMob} instance corresponding to the name.
     * @throws IllegalStateException if the mob cannot be found.
     */
    private MythicMob getMythicMob() {
        return mythicBukkit.getMobManager().getMythicMob(name)
                .orElseThrow(() -> new IllegalStateException(
                        "Couldn't find entity with this name, please make sure you have input a valid name"));
    }

    /**
     * Retrieves a random spawn location from the list of configured locations.
     * 
     * @return the selected {@link Location}.
     */
    private Location getRandomSpawnLocation() {
        return MathUtils.pickRandomElement(spawnLocations);
    }

    /**
     * Spawns the MythicMob at the given location.
     * 
     * @param mythicMob     the {@link MythicMob} to spawn.
     * @param spawnLocation the {@link Location} where the mob should be spawned.
     * @throws IllegalStateException if the mob or entity fails to spawn.
     */
    private void spawnMob(MythicMob mythicMob, Location spawnLocation) {
        ActiveMob activeMob = mythicMob.spawn(BukkitAdapter.adapt(spawnLocation), 0);
        if (activeMob == null) {
            throw new IllegalStateException("Failed to get ActiveMob!");
        }

        Entity entity = activeMob.getEntity().getBukkitEntity();
        if (entity == null) {
            throw new IllegalStateException("Failed to get BukkitEntity!");
        }

        this.activeMob = activeMob;
        this.bukkitEntity = entity;

        Bukkit.getLogger()
                .info("[DEBUG-MODE] Entity with this name " + name + " spawned at " + spawnLocation + " successfully!");
    }

    /**
     * Ensures that the mob is alive and valid before despawning.
     * 
     * @throws IllegalStateException if the mob cannot be despawned due to it not
     *                               being alive or valid.
     */
    private void checkIfCanDespawn() {
        if (!getActiveMob().isPresent() || !getBukkitEntity().isPresent()) {
            throw new IllegalStateException(
                    "Couldn't find entity with this name. Please make sure you have input a valid name.");
        }

        if (!isValid() || !isAlive()) {
            throw new IllegalStateException(
                    "You cannot despawn an entity that has not spawned yet or has already died!");
        }
    }

    /**
     * Performs the despawning of the currently active mob.
     */
    private void performDespawn() {
        this.activeMob.despawn();
        Bukkit.getLogger().info("[DEBUG-MODE] Entity despawned successfully!");
    }
}
