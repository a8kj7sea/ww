package me.a8kj.ww.internal.configuration.files;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class EventsFile extends Configuration {
    public EventsFile(JavaPlugin plugin) {
        super(plugin, "events.yml", true);
    }
}
