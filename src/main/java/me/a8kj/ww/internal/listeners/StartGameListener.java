package me.a8kj.ww.internal.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.api.event.game.impl.StartGameEvent;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.MessagesFile;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.internal.configuration.retrievers.messages.MessageRetriever;
import me.a8kj.ww.internal.configuration.retrievers.messages.PAPIMessageRetriever;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.PlayerUtils;

/**
 * Listens for the StartGameEvent and handles the logic for announcing the start
 * of the game.
 */
@RequiredArgsConstructor
@Getter
public class StartGameListener implements Listener {

    /** The plugin provider for accessing configuration and services. */
    private final PluginProvider pluginProvider;

    /**
     * Handles the StartGameEvent by broadcasting an announcement message to
     * players.
     *
     * @param event The StartGameEvent containing information about the game.
     * @throws IllegalStateException if the event game or event mob is null.
     */
    @EventHandler
    public void onGameStarted(StartGameEvent event) {
        // Validate that the event game and event mob are not null
        if (event.getEventGame() == null || event.getEventGame().getEventMob() == null) {
            throw new IllegalStateException("Error while executing StartGameEvent. Please restart the server!");
        }

        // Retrieve configuration files for messages and settings
        MessagesFile messagesFile = (MessagesFile) pluginProvider.getConfigurationManager()
                .getConfiguration("messages");
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager().getConfiguration("events");

        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        // Check if PlaceholderAPI support is enabled
        if (settingsRetriever.getBoolean(SettingsPathIdentifiers.PLACEHOLDER_API_SUPPORT)) {
            PAPIMessageRetriever papiMessageRetriever = new PAPIMessageRetriever(messagesFile.getYamConfiguration());

            // Announce the start game message to all players
            PlayerUtils.broadcastMessages(
                    papiMessageRetriever.getMessageList(MessagePathIdentifiers.GAME_LOGIC_START_GAME_ANNOUNCE_PLAYERS));
        } else {
            MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());
            // Announce the start game message to all players
            PlayerUtils.broadcastMessages(
                    messageRetriever.getMessageList(MessagePathIdentifiers.GAME_LOGIC_START_GAME_ANNOUNCE_PLAYERS));
        }
    }
}
