package me.a8kj.ww.internal.configuration.retrievers;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.parent.configuration.ConfigValueRetriever;
import me.clip.placeholderapi.PlaceholderAPI;

/**
 * A class for retrieving messages from the configuration with support for
 * PlaceholderAPI.
 */
@RequiredArgsConstructor
@Getter
public class MessageRetriever implements ConfigValueRetriever<MessagePathIdentifiers> {

    private final YamlConfiguration yamlConfiguration;

    /**
     * Retrieves a message from the configuration for a given path identifier.
     *
     * @param pathId The identifier for the path.
     * @return The message as a string with placeholders replaced.
     */
    public String getMessage(@NonNull MessagePathIdentifiers pathId) {
        String message = ConfigValueRetriever.super.getString(pathId);
        return PlaceholderAPI.setPlaceholders(null, message);
    }

    /**
     * Retrieves a list of messages from the configuration for a given path
     * identifier.
     *
     * @param pathId The identifier for the path.
     * @return A list of messages with placeholders replaced.
     */
    public List<String> getMessageList(@NonNull MessagePathIdentifiers pathId) {
        List<String> messages = ConfigValueRetriever.super.getStringList(pathId);
        return messages.stream()
                .map(message -> PlaceholderAPI.setPlaceholders(null, message))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a message from the configuration for a given path identifier,
     * with placeholders replaced for a specific player.
     *
     * @param pathId The identifier for the path.
     * @param player The player for whom placeholders will be replaced.
     * @return The message as a string with placeholders replaced.
     */
    public String getMessage(@NonNull MessagePathIdentifiers pathId, @NonNull Player player) {
        String message = ConfigValueRetriever.super.getString(pathId);
        return PlaceholderAPI.setPlaceholders(player, message);
    }

    /**
     * Retrieves a list of messages from the configuration for a given path
     * identifier,
     * with placeholders replaced for a specific player.
     *
     * @param pathId The identifier for the path.
     * @param player The player for whom placeholders will be replaced.
     * @return A list of messages with placeholders replaced.
     */
    public List<String> getMessageList(@NonNull MessagePathIdentifiers pathId, @NonNull Player player) {
        List<String> messages = ConfigValueRetriever.super.getStringList(pathId);
        return messages.stream()
                .map(message -> PlaceholderAPI.setPlaceholders(player, message))
                .collect(Collectors.toList());
    }
}
