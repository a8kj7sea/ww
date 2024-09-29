package me.a8kj.ww.parent.behavior;

/**
 * The {@code SpawnableMob} interface represents mobs that can be dynamically
 * spawned and removed from the game world.
 */
public interface SpawnableMob {

    /**
     * Spawns the mob into the game world at a specific location.
     *
     * @return {@code true} if the mob was successfully spawned, {@code false} otherwise.
     */
    boolean spawn();

    /**
     * Despawns the mob, removing it from the game world.
     *
     * @return {@code true} if the mob was successfully despawned, {@code false} otherwise.
     */
    boolean despawn();
}
