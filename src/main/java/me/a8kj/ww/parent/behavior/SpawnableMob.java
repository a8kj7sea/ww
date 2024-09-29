package me.a8kj.ww.parent.behavior;

/**
 * The SpawnableMob interface defines the contract for mobs that can be spawned
 * and despawned.
 */
public interface SpawnableMob {

    /**
     * Spawns the mob in the game world at its designated spawn location.
     */
    void spawn();

    /**
     * Despawns the mob, removing it from the game world.
     */
    void despawn();
}
