package me.a8kj.ww.parent.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class StringUtils {

    /**
     * Translates alternate color codes in the given text to the corresponding
     * Minecraft color codes.
     *
     * <p>
     * This method converts any instances of '&' followed by a character
     * (representing a color) into the corresponding {@link ChatColor} value
     * used in Minecraft chat messages.
     * </p>
     *
     * @param text the text to colorize
     * @return the colorized text with Minecraft color codes
     */
    public static String legacyColorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Formats the provided hours and minutes into a string of the format "HH::MM".
     *
     * @param hours   the hour value (0-23)
     * @param minutes the minute value (0-59)
     * @return the formatted time string in "HH::MM" format
     */
    public static String formatTime(int hours, int minutes) {
        String formattedHours = String.format("%02d", hours);
        String formattedMinutes = String.format("%02d", minutes);
        return formattedHours + ":" + formattedMinutes;
    }
}
