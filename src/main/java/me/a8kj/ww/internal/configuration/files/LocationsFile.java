package me.a8kj.ww.internal.configuration.files;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class LocationsFile extends Configuration {
    public LocationsFile(JavaPlugin plugin) {
        super(plugin, "messages.yml", true);
    }

}
