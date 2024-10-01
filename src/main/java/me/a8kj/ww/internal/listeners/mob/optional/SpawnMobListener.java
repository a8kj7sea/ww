package me.a8kj.ww.internal.listeners.mob.optional;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.api.event.mob.impl.SpawnMobEvent;
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
 * Listener for handling mob spawn events in the game.
 */
@RequiredArgsConstructor
@Getter
public class SpawnMobListener implements Listener {

    private final PluginProvider pluginProvider;

    /**
     * Handles the SpawnMobEvent. Broadcasts a message when a mob is spawned
     * and sends a notification to admins if PlaceholderAPI support is enabled.
     *
     * @param event the SpawnMobEvent containing information about the spawned mob
     * @throws IllegalStateException if the event or its mob entity is null
     */
    @EventHandler
    public void onSpawnMob(SpawnMobEvent event) {
        validateEvent(event);

        // Retrieve configuration files for messages and settings
        MessagesFile messagesFile = (MessagesFile) pluginProvider.getConfigurationManager()
                .getConfiguration("messages");
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("events");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        // Check if PlaceholderAPI support is enabled
        if (settingsRetriever.getBoolean(SettingsPathIdentifiers.PLACEHOLDER_API_SUPPORT)) {
            handlePAPIMessage(messagesFile, event);
        } else {
            handleRegularMessage(messagesFile, event);
        }
    }

    /**
     * Validates the SpawnMobEvent to ensure it contains valid data.
     *
     * @param event the SpawnMobEvent to validate
     * @throws IllegalStateException if the event or its mob entity is null
     */
    private void validateEvent(SpawnMobEvent event) {
        if (event == null || event.getEventMob() == null || event.getEventMob().getBukkitEntity() == null) {
            throw new IllegalStateException("Error while executing SpawnMobEvent. Please restart the server!");
        }
    }

    /**
     * Handles the broadcasting of messages using PlaceholderAPI support.
     *
     * @param messagesFile the MessagesFile containing message configurations
     * @param event        the SpawnMobEvent containing information about the
     *                     spawned mob
     */
    private void handlePAPIMessage(MessagesFile messagesFile, SpawnMobEvent event) {
        PAPIMessageRetriever papiMessageRetriever = new PAPIMessageRetriever(messagesFile.getYamConfiguration());

        // Broadcast the mob summon message
        PlayerUtils.broadcastMessage(
                papiMessageRetriever.getMessage(MessagePathIdentifiers.GAME_LOGIC_START_GAME_MOB_SUMMONED));

        // Notify admins with mob coordinates
        PlayerUtils.sendPermissionMessage("ww.admin.announce",
                papiMessageRetriever.getMessage(MessagePathIdentifiers.GAME_LOGIC_START_GAME_ANNOUNCE_ADMIN)
                        .replace("%mob_cords%", LocationsUtils.getLocationCordsAsString(
                                event.getEventMob().getBukkitEntity().get().getLocation())));
    }

    /**
     * Handles the broadcasting of messages without PlaceholderAPI support.
     *
     * @param messagesFile the MessagesFile containing message configurations
     * @param event        the SpawnMobEvent containing information about the
     *                     spawned mob
     */
    private void handleRegularMessage(MessagesFile messagesFile, SpawnMobEvent event) {
        MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());

        // Broadcast the mob summon message
        PlayerUtils.broadcastMessage(
                messageRetriever.getMessage(MessagePathIdentifiers.GAME_LOGIC_START_GAME_MOB_SUMMONED));

        // Notify admins with mob coordinates
        PlayerUtils.sendPermissionMessage("ww.admin.announce",
                messageRetriever.getMessage(MessagePathIdentifiers.GAME_LOGIC_START_GAME_ANNOUNCE_ADMIN)
                        .replace("%mob_cords%", LocationsUtils.getLocationCordsAsString(
                                event.getEventMob().getBukkitEntity().get().getLocation())));
    }
}
