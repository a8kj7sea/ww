package me.a8kj.ww.internal.listeners.optional;

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
        validateEvent(event);

        MessagesFile messagesFile = (MessagesFile) pluginProvider.getConfigurationManager()
                .getConfiguration("messages");
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("events");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        // Check if PlaceholderAPI support is enabled and handle messages accordingly
        if (settingsRetriever.getBoolean(SettingsPathIdentifiers.PLACEHOLDER_API_SUPPORT)) {
            handlePAPIMessage(messagesFile);
        } else {
            handleRegularMessage(messagesFile);
        }
    }

    /**
     * Validates the StartGameEvent to ensure it contains valid data.
     *
     * @param event the StartGameEvent to validate
     * @throws IllegalStateException if the event game or event mob is null
     */
    private void validateEvent(StartGameEvent event) {
        if (event.getEventGame() == null || event.getEventGame().getEventMob() == null) {
            throw new IllegalStateException("Error while executing StartGameEvent. Please restart the server!");
        }
    }

    /**
     * Handles the broadcasting of messages using PlaceholderAPI support.
     *
     * @param messagesFile the MessagesFile containing message configurations
     */
    private void handlePAPIMessage(MessagesFile messagesFile) {
        PAPIMessageRetriever papiMessageRetriever = new PAPIMessageRetriever(messagesFile.getYamConfiguration());
        PlayerUtils.broadcastMessages(
                papiMessageRetriever.getMessageList(MessagePathIdentifiers.GAME_LOGIC_START_GAME_ANNOUNCE_PLAYERS));
    }

    /**
     * Handles the broadcasting of messages without PlaceholderAPI support.
     *
     * @param messagesFile the MessagesFile containing message configurations
     */
    private void handleRegularMessage(MessagesFile messagesFile) {
        MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());
        PlayerUtils.broadcastMessages(
                messageRetriever.getMessageList(MessagePathIdentifiers.GAME_LOGIC_START_GAME_ANNOUNCE_PLAYERS));
    }
}
