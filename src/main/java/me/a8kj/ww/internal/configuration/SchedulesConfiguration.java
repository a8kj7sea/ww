package me.a8kj.ww.internal.configuration;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class SchedulesConfiguration extends Configuration {
    public SchedulesConfiguration(JavaPlugin plugin) {
        super(plugin, "schedules.yml", true);
    }

}
