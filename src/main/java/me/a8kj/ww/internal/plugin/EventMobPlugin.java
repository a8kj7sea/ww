package me.a8kj.ww.internal.plugin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * The EventMobPlugin class handles the plugin lifecycle events such as enabling
 * and disabling the plugin, and initializes the WWPluginProvider.
 */
public class EventMobPlugin extends JavaPlugin {

    public static final List<Entity> spawnedEnityList = Lists.newArrayList();

    @Getter
    private static PluginProvider pluginProvider;

    /**
     * Called when the plugin is enabled.
     * Initializes the plugin and registers necessary classes and events.
     */
    @Override
    public void onEnable() {
        if (!canStart()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Register custom classes for serialization
        ConfigurationSerialization.registerClass(ScheduledEvent.class, "ScheduledEvent");

        // Initialize and start the plugin provider
        pluginProvider = new WWPluginProvider(getLogger(), this);
        pluginProvider.onStart();

        // Log success message
        Bukkit.getConsoleSender().sendMessage("\u00a7aWW-Plugin has been enabled successfully!");

    }

    /**
     * Called when the plugin is disabled.
     * Cleans up resources and stops the plugin provider.
     */
    @Override
    public void onDisable() {
        if (pluginProvider != null) {
            pluginProvider.onStop();
        }
    }

    /**
     * Checks if all required plugins are installed.
     *
     * @return true if all required plugins are installed, false otherwise
     */
    private boolean canStart() {
        List<String> requiredPlugins = Arrays.asList("WorldGuard", "MythicMobs");
        return requiredPlugins.stream().allMatch(this::isPluginInstalled);
    }

    /**
     * Checks if a plugin with the given name is installed.
     *
     * @param pluginName the name of the plugin to check
     * @return true if the plugin is installed, false otherwise
     */
    private boolean isPluginInstalled(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null) {
            getLogger().severe(pluginName + " is not installed! Disabling plugin.");
            return false;
        }
        return true;
    }
}
