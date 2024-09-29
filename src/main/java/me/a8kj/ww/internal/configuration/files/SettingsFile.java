package me.a8kj.ww.internal.configuration.files;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class SettingsFile extends Configuration {
    public SettingsFile(JavaPlugin plugin) {
        super(plugin, "config.yml", true);
    }

}
