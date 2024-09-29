package me.a8kj.ww.internal.configuration;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class EventsConfigurations extends Configuration {
    public EventsConfigurations(JavaPlugin plugin) {
        super(plugin, "events.yml", true);
    }
}
