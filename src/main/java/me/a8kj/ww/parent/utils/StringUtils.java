package me.a8kj.ww.parent.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class StringUtils {

    public static String legacyColorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
