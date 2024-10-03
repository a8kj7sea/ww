package me.a8kj.ww.internal.command.subcommand.game;

import org.bukkit.entity.Player;

import me.a8kj.ww.api.event.game.impl.EndGameEvent.EndReason;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Command to end an event game if one is currently active.
 */
public class EndGameSub extends PlayerSubCommand {

    /**
     * Constructs a new EndGameSub command.
     *
     * @param pluginProvider The plugin provider to access game management
     *                       functionalities.
     */
    public EndGameSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    @Override
    public String getUsage() {
        return "/lg end"; // Command usage description
    }

    @Override
    public String getDescription() {
        return "Ends the current event game if one is active."; // Command description
    }

    /**
     * Executes the command to end the current event game.
     *
     * @param args   The command arguments (not used in this command).
     * @param source The player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        GameManager gameManager = pluginProvider.getGameManager();

        // Check if there is a current game
        if (gameManager.getCurrentGame().isEmpty()) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_STOP_GAME_FAILURE));
            return; // Exit early if no game is active
        }

        EventGame game = gameManager.getCurrentGame().orElseThrow(); // Retrieve the game

        // Check if the current game is in progress
        if (game.getGameState() != GameState.INGAME) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_STOP_GAME_FAILURE));
            return; // Exit early if the game is not in progress
        }

        // End the game and notify the player
        gameManager.endGame(EndReason.COMMAND);
        source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_STOP_GAME_SUCCESS));
    }
}
