package me.a8kj.ww.internal.listeners.mob.optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.api.event.game.impl.EndGameEvent;
import me.a8kj.ww.api.event.game.impl.EndGameEvent.EndReason;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.MessagesFile;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.internal.configuration.retrievers.messages.MessageRetriever;
import me.a8kj.ww.internal.configuration.retrievers.messages.PAPIMessageRetriever;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.LocationsUtils;
import me.a8kj.ww.parent.utils.PlayerUtils;

/**
 * Listens for the EndGameEvent and handles the logic for announcing the end of
 * the game.
 */
@RequiredArgsConstructor
@Getter
public class EndGameListener implements Listener {

    private final PluginProvider pluginProvider;
    private static final String ADMIN_ANNOUNCE_PERMISSION = "ww.admin.announce";

    /**
     * Handles the EndGameEvent by broadcasting the appropriate messages to players.
     *
     * @param event The EndGameEvent containing information about the game's end.
     */
    @EventHandler
    public void onEndGame(EndGameEvent event) {
        validateEvent(event);

        EndReason endReason = event.getEndReason();
        MessagesFile messagesFile = (MessagesFile) pluginProvider.getConfigurationManager()
                .getConfiguration("messages");
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager().getConfiguration("events");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        if (endReason == EndReason.COMMAND) {
            broadcastEndMessage(messagesFile, settingsRetriever,
                    MessagePathIdentifiers.GAME_LOGIC_END_GAME_COMMAND_ANNOUNCE_MESSAGE);
        } else {
            broadcastDeathMessage(messagesFile, settingsRetriever, event);
        }

        pluginProvider.getGameManager().removeGame();
    }

    /**
     * Validates the EndGameEvent to ensure it has valid game and mob references.
     *
     * @param event The EndGameEvent to validate.
     * @throws IllegalStateException if the event game or mob is null.
     */
    private void validateEvent(EndGameEvent event) {
        if (event.getEventGame() == null || event.getEventGame().getEventMob() == null) {
            throw new IllegalStateException("Error while executing EndGameEvent. Please restart the server!");
        }
    }

    /**
     * Broadcasts a message to players when the game ends via a command.
     *
     * @param messagesFile      The messages file containing the configurations.
     * @param settingsRetriever The settings retriever for fetching configuration
     *                          values.
     * @param messagePath       The path identifier for the end game message.
     */
    private void broadcastEndMessage(MessagesFile messagesFile, SettingsRetriever settingsRetriever,
            MessagePathIdentifiers messagePath) {
        if (settingsRetriever.getBoolean(SettingsPathIdentifiers.PLACEHOLDER_API_SUPPORT)) {
            PAPIMessageRetriever papiMessageRetriever = new PAPIMessageRetriever(messagesFile.getYamConfiguration());
            PlayerUtils.broadcastMessages(papiMessageRetriever.getMessageList(messagePath));
        } else {
            MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());
            PlayerUtils.broadcastMessages(messageRetriever.getMessageList(messagePath));
        }
    }

    /**
     * Broadcasts a message to players when the game ends due to a death event.
     *
     * @param messagesFile      The messages file containing the configurations.
     * @param settingsRetriever The settings retriever for fetching configuration
     *                          values.
     * @param event             The EndGameEvent containing the game's end
     *                          information.
     */
    private void broadcastDeathMessage(MessagesFile messagesFile, SettingsRetriever settingsRetriever,
            EndGameEvent event) {
        String mobCoordinates = LocationsUtils
                .getLocationCordsAsString(event.getEventGame().getEventMob().getBukkitEntity().get().getLocation());

        if (settingsRetriever.getBoolean(SettingsPathIdentifiers.PLACEHOLDER_API_SUPPORT)) {
            PAPIMessageRetriever papiMessageRetriever = new PAPIMessageRetriever(messagesFile.getYamConfiguration());
            PlayerUtils.broadcastMessages(papiMessageRetriever
                    .getMessageList(MessagePathIdentifiers.GAME_LOGIC_END_GAME_DEATH_ANNOUNCE_MESSAGE));
            PlayerUtils.sendPermissionMessage(ADMIN_ANNOUNCE_PERMISSION,
                    papiMessageRetriever.getMessage(MessagePathIdentifiers.GAME_LOGIC_END_GAME_DEATH_ANNOUNCE_ADMIN)
                            .replace("%mob_cords%", mobCoordinates));
        } else {
            MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());
            PlayerUtils.broadcastMessages(
                    messageRetriever.getMessageList(MessagePathIdentifiers.GAME_LOGIC_END_GAME_DEATH_ANNOUNCE_MESSAGE));
            PlayerUtils.sendPermissionMessage(ADMIN_ANNOUNCE_PERMISSION,
                    messageRetriever.getMessage(MessagePathIdentifiers.GAME_LOGIC_END_GAME_DEATH_ANNOUNCE_ADMIN)
                            .replace("%mob_cords%", mobCoordinates));
        }

    }

}
