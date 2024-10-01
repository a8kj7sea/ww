package me.a8kj.ww.internal.configuration.retrievers.messages;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.parent.configuration.ConfigValueRetriever;
import me.a8kj.ww.parent.utils.StringUtils;

/**
 * A class for retrieving messages from the configuration without support for
 * PlaceholderAPI.
 */
@RequiredArgsConstructor
@Getter
public class MessageRetriever implements ConfigValueRetriever<MessagePathIdentifiers> {

    private final YamlConfiguration yamlConfiguration;

    /**
     * Retrieves a message from the configuration for a given path identifier.
     * The message is colorized using legacy color codes.
     *
     * @param pathId The identifier for the path.
     * @return The message as a string with colorized legacy codes.
     */
    public String getMessage(@NonNull MessagePathIdentifiers pathId) {
        String message = ConfigValueRetriever.super.getString(pathId);
        return StringUtils.legacyColorize(message).replace("%prefix%", getMessage(MessagePathIdentifiers.PREFIX));
    }

    /**
     * Retrieves a list of messages from the configuration for a given path
     * identifier. Each message is colorized using legacy color codes.
     *
     * @param pathId The identifier for the path.
     * @return A list of colorized messages.
     */
    public List<String> getMessageList(@NonNull MessagePathIdentifiers pathId) {
        List<String> messages = ConfigValueRetriever.super.getStringList(pathId);
        return messages.stream()
                .map(StringUtils::legacyColorize)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a message from the configuration for a given path identifier,
     * with colorization applied for a specific player.
     *
     * @param pathId The identifier for the path.
     * @param player The player for whom the message will be colorized.
     * @return The colorized message as a string.
     */
    public String getMessage(@NonNull MessagePathIdentifiers pathId, @NonNull Player player) {
        String message = ConfigValueRetriever.super.getString(pathId);
        return StringUtils.legacyColorize(message).replace("%prefix%", getMessage(MessagePathIdentifiers.PREFIX));
    }

    /**
     * Retrieves a list of messages from the configuration for a given path
     * identifier, with colorization applied for a specific player.
     *
     * @param pathId The identifier for the path.
     * @param player The player for whom the messages will be colorized.
     * @return A list of colorized messages.
     */
    public List<String> getMessageList(@NonNull MessagePathIdentifiers pathId, @NonNull Player player) {
        List<String> messages = ConfigValueRetriever.super.getStringList(pathId);
        return messages.stream()
                .map(StringUtils::legacyColorize)
                .collect(Collectors.toList());
    }
}
