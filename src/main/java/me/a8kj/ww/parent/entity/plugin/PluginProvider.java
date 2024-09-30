package me.a8kj.ww.parent.entity.plugin;

import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.internal.manager.ConfigurationManager;
import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.schedule.EventScheduler;

/**
 * Interface representing a provider for plugin-related functionalities.
 * This interface provides access to the plugin's logger, main plugin instance,
 * and configuration settings, as well as lifecycle methods for starting and
 * stopping the plugin.
 */
public interface PluginProvider {

    /**
     * Retrieves the logger for this plugin.
     *
     * @return the Logger instance for logging messages.
     */
    Logger getLogger();

    /**
     * Retrieves the main JavaPlugin instance associated with this provider.
     *
     * @return the JavaPlugin instance.
     */
    JavaPlugin getPlugin();

    /**
     * Retrieves a map of configuration settings for this plugin.
     *
     * @return a map where the key is the configuration name and the value is the
     *         Configuration instance.
     */
    Map<String, Configuration> getConfigurations();

    /**
     * Retrieves the configurations manager instance associated with this provider.
     *
     * @return the Configuration manager.
     */
    ConfigurationManager getConfigurationManager();

    /**
     * Retrieves the game manager instance associated with this provider.
     *
     * @return the game manager.
     */
    GameManager getGameManager();

    /**
     * Retrieves the event scheduler instance associated with this provider.
     *
     * @return the event scheduler.
     */
    EventScheduler getEventScheduler();
    /**
     * Invoked when the plugin is started.
     * This method can be overridden to add startup logic.
     */
    default void onStart() {
    }

    /**
     * Invoked when the plugin is stopped.
     * This method can be overridden to add shutdown logic.
     */
    default void onStop() {
    }
}
