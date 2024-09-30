package me.a8kj.ww.internal.game.mechanic;

import org.bukkit.Bukkit;
import lombok.Getter;
import lombok.NonNull;
import me.a8kj.ww.api.event.game.impl.EndGameEvent;
import me.a8kj.ww.api.event.game.impl.EndGameEvent.EndReason;
import me.a8kj.ww.internal.configuration.files.EventsFile;
import me.a8kj.ww.internal.configuration.retrievers.EventRetriever;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.game.mechanic.GameMechanic;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * The EndMechanic class handles the logic for ending a game in the event
 * system. It manages the despawning of mobs and triggers the EndGameEvent.
 */
@Getter
public class EndMechanic extends GameMechanic {

    private final EndReason endReason;

    /**
     * Constructs an EndMechanic with the given plugin provider and end reason.
     *
     * @param pluginProvider The plugin provider used for configuration and event
     *                       management.
     * @param endReason      The reason for ending the game.
     */
    public EndMechanic(@NonNull PluginProvider pluginProvider, @NonNull EndReason endReason) {
        super(pluginProvider);
        this.endReason = endReason;
    }

    /**
     * Applies the end mechanic to the game, managing mob despawning and triggering
     * end events.
     *
     * @param game The game instance where the mechanic is applied.
     * @throws IllegalStateException if the mob is null or if there's an error
     *                               during despawning.
     */
    @Override
    public void apply(EventGame game) {
        EventMob mob = game.getEventMob();

        if (mob == null) {
            throw new IllegalStateException(
                    "Mob cannot be null. Please check the mob configuration or restart the server.");
        }

        EventsFile eventsFile = (EventsFile) pluginProvider.getConfigurationManager().getConfiguration("events");
        EventRetriever eventRetriever = new EventRetriever(eventsFile.getYamConfiguration());

        // Always trigger the end game event without checks
        if (endReason == EndReason.COMMAND) {
            if (!mob.despawn()) {
                throw new IllegalStateException("Error while despawning mob!");
            }
        }
        triggerEndGameEvent(game, eventRetriever);

        updateGamePhaseAndState(game);
    }

    /**
     * Checks if the end mechanic can be applied based on the game state and next
     * phase.
     *
     * @param state The current game state.
     * @param phase The next phase of the game.
     * @return true if the game state is INGAME and the next phase is END, otherwise
     *         false.
     */
    @Override
    public boolean canApplyGameMechanic(GameState state, NextPhase phase) {
        return state == GameState.INGAME && phase == NextPhase.END;
    }

    /**
     * Triggers the EndGameEvent if configured.
     *
     * @param game           The EventGame associated with this event.
     * @param eventRetriever The event retriever for checking configuration.
     */
    private void triggerEndGameEvent(EventGame game, EventRetriever eventRetriever) {
        new EndGameEvent(game, endReason).callEvent();
    }

    /**
     * Updates the game state to IDLE and sets the next game phase to START.
     *
     * @param game The EventGame to update.
     */
    private void updateGamePhaseAndState(EventGame game) {
        game.setGameState(GameState.IDLE);
        game.setNextGamePhase(NextPhase.START);
        Bukkit.getLogger().info("[DEBUG] Game state updated to IDLE and next phase set to START for game: " + game);
    }
}
