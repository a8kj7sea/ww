package me.a8kj.ww.parent.entity.plugin;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.internal.manager.ConfigurationManager;
import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.internal.schedules.SchedulesManager;
import me.a8kj.ww.internal.task.MobWatcherTask;
import me.a8kj.ww.internal.task.SchedulerTask;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.menu.Menu;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.schedule.EventScheduler;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * Interface representing a provider for plugin-related functionalities.
 * This interface provides access to essential components such as configuration
 * settings, managers, and tasks, as well as lifecycle methods for plugin
 * startup
 * and shutdown.
 * 
 * <p>
 * Classes implementing this interface can provide customized behavior for
 * interacting with the plugin's environment, including managing game events,
 * menus, and scheduled tasks.
 * </p>
 */
public interface PluginProvider {

    /**
     * Retrieves the logger for this plugin.
     * 
     * @return the {@link Logger} instance for logging messages.
     */
    Logger getLogger();

    /**
     * Retrieves the main {@link JavaPlugin} instance associated with this provider.
     * 
     * @return the {@link JavaPlugin} instance.
     */
    JavaPlugin getPlugin();

    /**
     * Retrieves a map of configuration settings for this plugin.
     * 
     * @return a map where the key is the configuration name and the value is the
     *         {@link Configuration} instance.
     */
    Map<String, Configuration> getConfigurations();

    /**
     * Retrieves a map of registered menu(s) for this plugin.
     * 
     * @return a map where the key is the menu name and the value is the
     *         {@link Menu}
     *         instance.
     */
    Map<String, Menu> getMenus();

    /**
     * Retrieves the configuration manager instance associated with this provider.
     * 
     * @return the {@link ConfigurationManager} instance that handles configuration
     *         management.
     */
    ConfigurationManager getConfigurationManager();

    /**
     * Retrieves the game manager instance associated with this provider.
     * 
     * @return the {@link GameManager} instance that manages game-related logic.
     */
    GameManager getGameManager();

    /**
     * Retrieves the event scheduler instance associated with this provider.
     * 
     * @return the {@link EventScheduler} instance that handles the scheduling of
     *         events.
     */
    EventScheduler getEventScheduler();

    /**
     * Retrieves the schedule manager instance associated with this provider.
     * 
     * @return the {@link SchedulesManager} that manages scheduled tasks.
     */
    SchedulesManager getSchedulesManager();

    /**
     * Retrieves the scheduler task instance associated with this provider.
     * 
     * @return the {@link SchedulerTask} that handles the execution of scheduled
     *         tasks.
     */
    SchedulerTask getSchedulerTask();

    /**
     * Retrieves the mob move task instance associated with this provider.
     * 
     * @return the {@link MobWatcherTask} that handles the mob movement task.
     */
    MobWatcherTask getMobWatcherTask();

    void defineMobWatcherTask(PluginProvider pluginProvider, EventMob mob);

    /**
     * Retrieves a set of scheduled events for this plugin.
     * 
     * @return a {@link Set} of {@link ScheduledEvent} instances representing the
     *         scheduled events.
     */
    Set<ScheduledEvent> getScheduledEvents();

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
