package me.a8kj.ww.internal.game.mechanic;

import lombok.NonNull;
import me.a8kj.ww.api.event.game.impl.StartGameEvent;
import me.a8kj.ww.api.event.mob.impl.SpawnMobEvent;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.game.mechanic.GameMechanic;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import org.bukkit.Bukkit;

/**
 * The StartMechanic class handles the logic for starting a game in the event
 * system.
 * It spawns the event mob, triggers necessary events, and updates the game
 * phase and state.
 */
public class StartMechanic extends GameMechanic {

    /**
     * Constructs a StartMechanic with a given plugin provider.
     *
     * @param pluginProvider The plugin provider used for configuration and event
     *                       management.
     */
    public StartMechanic(@NonNull PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    /**
     * Applies the start mechanic to the game, spawning the mob, triggering events,
     * and updating the game state and phase.
     *
     * @param game The game instance where the mechanic is applied.
     * @throws IllegalStateException if the mob is null or fails to spawn.
     */
    @Override
    public void apply(EventGame game) {
        EventMob mob = game.getEventMob();

        if (mob == null) {
            throw new IllegalStateException(
                    "Mob cannot be null. Please check the mob configuration or restart the server.");
        }

        // Try spawning the mob and proceed if successful
        if (mob.spawn()) {
            handleEventTriggering(game, mob);
            updateGamePhaseAndState(game);
        } else {
            // Log failure to spawn the mob for debugging purposes
            Bukkit.getLogger().warning("[ERROR] Failed to spawn mob for game: " + game);
        }
    }

    /**
     * Checks if the start mechanic can be applied based on the game state and next
     * phase.
     *
     * @param state The current game state.
     * @param phase The next phase of the game.
     * @return true if the game state is IDLE and the next phase is START, otherwise
     *         false.
     */
    @Override
    public boolean canApplyGameMechanic(GameState state, NextPhase phase) {
        return state == GameState.IDLE && phase == NextPhase.START;
    }

    /**
     * Updates the game state to INGAME and sets the next game phase to END.
     *
     * @param game The EventGame to update.
     */
    private void updateGamePhaseAndState(EventGame game) {
        game.setGameState(GameState.INGAME);
        game.setNextGamePhase(NextPhase.END);
        Bukkit.getLogger().info("[DEBUG] Game state updated to INGAME and next phase set to END for game: " + game);
    }

    /**
     * Handles triggering of relevant events after a successful mob spawn.
     *
     * @param game The game instance.
     * @param mob  The spawned event mob.
     */
    private void handleEventTriggering(EventGame game, EventMob mob) {
        // Trigger SpawnMobEvent unconditionally
        new SpawnMobEvent(mob).callEvent();

        // Trigger StartGameEvent unconditionally
        new StartGameEvent(game).callEvent();
    }
}
