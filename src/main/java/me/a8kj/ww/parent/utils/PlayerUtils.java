package me.a8kj.ww.parent.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerUtils {

    public static void broadcastMessage(@NonNull String message) {
        Bukkit.getServer().broadcastMessage(StringUtils.legacyColorize(message));
    }

    public static void broadcastMessages(@NonNull List<String> messages) {
        messages.forEach(PlayerUtils::broadcastMessage);
    }

    public static <Source> void sendMessage(@NonNull Source source, @NonNull String message) {
        String coloredMessage = StringUtils.legacyColorize(message);
        if (source instanceof Entity) {
            ((Entity) source).sendMessage(coloredMessage);
        } else if (source instanceof CommandSender) {
            ((CommandSender) source).sendMessage(coloredMessage);
        } else {
            throw new IllegalArgumentException("Invalid source type");
        }
    }

    public static <Source> void sendMessages(@NonNull Source source, @NonNull List<String> messages) {
        messages.forEach(message -> sendMessage(source, message));
    }

    public static void sendPermissionMessage(@NonNull String permission, @NonNull String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.hasPermission(permission))
                .forEach(player -> sendMessage(player, message));
    }
}
