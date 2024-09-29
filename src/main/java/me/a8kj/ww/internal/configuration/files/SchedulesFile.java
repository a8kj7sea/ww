package me.a8kj.ww.internal.configuration.files;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class SchedulesFile extends Configuration {
    public SchedulesFile(JavaPlugin plugin) {
        super(plugin, "schedules.yml", true);
    }

}
