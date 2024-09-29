package me.a8kj.ww.parent.entity.mob;

import java.util.*;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import io.lumine.mythic.core.mobs.ActiveMob;
import me.a8kj.ww.parent.behavior.SpawnableMob;

/**
 * The EventMob interface defines the core behavior of a mob in the game.
 * It extends the SpawnableMob interface to include spawning and despawning
 * capabilities along with additional mob-specific attributes and behaviors.
 */
public interface EventMob extends SpawnableMob {

    /**
     * Retrieves the name of the mob.
     * 
     * @return the name of the mob as a String.
     */
    String getName();

    /**
     * Gets a list of locations where the mob can spawn.
     * 
     * @return a list of Location objects representing valid spawn points for the
     *         mob.
     */
    List<Location> getSpawnLocations();

    /**
     * Retrieves the ActiveMob representation of this mob.
     * 
     * @return an Optional containing the ActiveMob associated with this event mob,
     *         or empty if not present.
     */
    Optional<ActiveMob> getActiveMob();

    /**
     * Retrieves the Bukkit entity representation of this mob.
     * 
     * @return an Optional containing the Bukkit Entity associated with this event
     *         mob,
     *         or empty if not present.
     */
    Optional<Entity> getBukkitEntity();

    /**
     * Checks if the mob is currently alive.
     * 
     * @return true if the mob is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Validates the mob's current state or attributes.
     * 
     * @return true if the mob's state is valid, false otherwise.
     */
    boolean isValid();
}
