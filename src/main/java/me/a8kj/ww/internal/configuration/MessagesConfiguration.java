package me.a8kj.ww.internal.configuration;

import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;

public class MessagesConfiguration extends Configuration {
    public MessagesConfiguration(JavaPlugin plugin) {
        super(plugin, "messages.yml", true);
    }

}
