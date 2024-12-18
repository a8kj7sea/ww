package me.a8kj.ww.internal.manager;

import lombok.NonNull;
import me.a8kj.ww.api.event.game.impl.EndGameEvent;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.LocationsFile;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.internal.game.Game;
import me.a8kj.ww.internal.game.mechanic.EndMechanic;
import me.a8kj.ww.internal.game.mechanic.StartMechanic;
import me.a8kj.ww.internal.mob.EventMobImpl;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import org.bukkit.Bukkit;

import java.util.Optional;

/**
 * The GameManager class handles the lifecycle of a single game instance,
 * including creation, storage, and retrieval from a file.
 */
public class GameManager {

    private final PluginProvider pluginProvider;
    private EventGame currentGame; // Only one game instance

    /**
     * Constructs a GameManager with the given plugin provider.
     *
     * @param pluginProvider The plugin provider used for configuration and event
     *                       management.
     */
    public GameManager(@NonNull PluginProvider pluginProvider) {
        this.pluginProvider = pluginProvider;
    }

    /**
     * Creates a new game and stores it in memory.
     *
     * @return The created EventGame instance.
     */
    public EventGame createGame() {
        currentGame = new Game();
        currentGame.setGameState(GameState.IDLE);
        currentGame.setNextGamePhase(NextPhase.START);

        LocationsFile locationsFile = (LocationsFile) pluginProvider.getConfigurations().get("locations");
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurations().get("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        EventMob mob = new EventMobImpl(settingsRetriever.getString(SettingsPathIdentifiers.MOB_NAME),
                locationsFile.getYamConfiguration());
        currentGame.setEventMob(mob);

        return currentGame;
    }

    /**
     * Checks if the game has been properly set up.
     *
     * <p>
     * This method checks whether the necessary setup process for the game is
     * complete
     * by verifying that spawn locations have been defined. If the setup is
     * incomplete
     * or failed, it returns false.
     * </p>
     *
     * @return true if the setup is complete (i.e., spawn locations exist), false
     *         otherwise.
     */
    public boolean checkSetup() {

        boolean isOk = pluginProvider.getConfigurations()
                .get("locations")
                .getYamConfiguration()
                .contains("spawn-locations");

        return isOk;
    }

    /**
     * Determines if the game can be started.
     *
     * <p>
     * The game can start if:
     * - The setup is complete (spawn locations are defined).
     * - The current game exists and is in the IDLE state.
     * - The next game phase is set to START.
     * </p>
     *
     * @return true if the game can be started, false otherwise.
     */
    public boolean canStartGame() {
        return currentGame != null
                && currentGame.getGameState() == GameState.IDLE
                && currentGame.getNextGamePhase() == NextPhase.START
                && checkSetup(); // Ensures the setup (like spawn locations) is complete
    }

    /**
     * Starts the current game using the StartMechanic.
     */
    public void startGame() {
        if (canStartGame()) {
            StartMechanic startMechanic = new StartMechanic(pluginProvider);
            startMechanic.apply(currentGame);
            Bukkit.getLogger().info("[DEBUG] Game started successfully.");
        } else {
            Bukkit.getLogger().warning(
                    "[DEBUG] Cannot start the game. Check if the setup is complete or if the game is in the correct state.");
        }
    }

    /**
     * Ends the current game using the EndMechanic.
     *
     * @param endReason The reason for ending the game.
     */
    public void endGame(EndGameEvent.EndReason endReason) {
        if (currentGame != null) {
            EndMechanic endMechanic = new EndMechanic(pluginProvider, endReason);
            endMechanic.apply(currentGame);
        }
    }

    /**
     * Removes (destroys) the current game instance, setting it to null.
     */
    public void removeGame() {
        currentGame = null;
        Bukkit.getLogger().info("[DEBUG] Current game has been removed.");
    }

    /**
     * Retrieves the current game instance, if available.
     *
     * @return An Optional containing the current EventGame instance, or an empty
     *         Optional if no game is active.
     */
    public Optional<EventGame> getCurrentGame() {
        return Optional.ofNullable(currentGame);
    }

}
