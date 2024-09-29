package me.a8kj.ww.internal.plugin;

import java.util.*;
import java.util.logging.*;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;

import lombok.*;

import me.a8kj.ww.internal.configuration.files.*;

import me.a8kj.ww.internal.manager.ConfigurationManager;

import me.a8kj.ww.parent.configuration.Configuration;

import me.a8kj.ww.parent.entity.plugin.PluginProvider;

@RequiredArgsConstructor
@Getter
public class WWPluginProvider implements PluginProvider {

    private final Logger logger;
    private final JavaPlugin plugin;

    private final Map<String, Configuration> configurations = Maps.newHashMap();
    private ConfigurationManager configurationManager;

    @Override
    public void onStart() {
        configurationManager = new ConfigurationManager(configurations);
        registerConfigurations();
    }

    @Override
    public void onStop() {

    }


    

    private void registerConfigurations() {
        configurationManager.register("settings", new SettingsFile(plugin));
        configurationManager.register("events", new EventsFile(plugin));
        configurationManager.register("locations", new LocationsFile(plugin));
        configurationManager.register("schedules", new SchedulesFile(plugin));
        configurationManager.register("messages", new MessagesFile(plugin));
    }

    @Override
    public Map<String, Configuration> getConfigurations() {
        return configurations;
    }
}
