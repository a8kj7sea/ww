package me.a8kj.ww.internal.command.subcommand.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import net.md_5.bungee.api.ChatColor;

/**
 * Command to start an event game if conditions are met.
 */
public class StartGameSub extends PlayerSubCommand {

    /**
     * Constructs a new StartGameSub command.
     *
     * @param pluginProvider The plugin provider to access game management
     *                       functionalities.
     */
    public StartGameSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    @Override
    public String getUsage() {
        return "/lg start"; // Command usage description
    }

    @Override
    public String getDescription() {
        return "Starts the event game if conditions are met."; // Command description
    }

    /**
     * Executes the command to start the event game.
     *
     * @param args   The command arguments (not used in this command).
     * @param source The player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        GameManager gameManager = pluginProvider.getGameManager();

        // Check if setup is complete
        if (!gameManager.checkSetup()) {
            source.sendMessage(
                    ChatColor.RED + "Couldn't start event right now; please ensure you have set up spawn locations.");
            return;
        }

        // Proceed if there is no current game
        if (!gameManager.getCurrentGame().isPresent()) {
            if (Bukkit.getOnlinePlayers().size() <= 1) {
                source.sendMessage(ChatColor.RED + "Not enough players to start the event!");
                return; // Exit early if not enough players
            }

            startNewGame(gameManager, source);
            return; // Early exit after starting the game
        }

        // Handle case when there is an ongoing game
        EventGame game = gameManager.getCurrentGame().get();
        if (game.getGameState() == GameState.INGAME) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_START_GAME_FAILURE));
            return; // Exit early if an event is running
        }

        // If the game is not in progress, start a new game
        startNewGame(gameManager, source);
    }

    /**
     * Starts a new game and notifies the player.
     *
     * @param gameManager The game manager responsible for game state.
     * @param source      The player executing the command.
     */
    private void startNewGame(GameManager gameManager, Player source) {
        gameManager.createGame();
        gameManager.startGame();
        source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_START_GAME_SUCCESS));
    }
}
