package me.a8kj.ww.internal.plugin;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.command.LGCommand;
import me.a8kj.ww.internal.configuration.enums.EventPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.*;
import me.a8kj.ww.internal.configuration.retrievers.EventRetriever;
import me.a8kj.ww.internal.listeners.OtherListeners;
import me.a8kj.ww.internal.listeners.menu.MenuListeners;
import me.a8kj.ww.internal.listeners.mob.*;
import me.a8kj.ww.internal.listeners.mob.optional.*;
import me.a8kj.ww.internal.manager.ConfigurationManager;
import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.internal.schedules.EventsScheduler;
import me.a8kj.ww.internal.schedules.SchedulesManager;
import me.a8kj.ww.internal.schedules.handlers.LoadHandler;
import me.a8kj.ww.internal.schedules.handlers.SaveHandler;
import me.a8kj.ww.internal.task.MobWatcherTask;
import me.a8kj.ww.internal.task.SchedulerTask;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.menu.Menu;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.schedule.EventScheduler;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * Main plugin provider for the WW Plugin.
 * Handles initialization, configuration management, and event registration.
 */
@RequiredArgsConstructor
@Getter
public class WWPluginProvider implements PluginProvider {

    private final Logger logger;
    private final JavaPlugin plugin;

    private final Map<String, Configuration> configurations = Maps.newHashMap();
    private final Map<String, Menu> menus = Maps.newHashMap();

    private ConfigurationManager configurationManager;
    private GameManager gameManager;
    private SchedulesManager schedulesManager;
    private EventScheduler eventScheduler;
    private SchedulerTask schedulerTask;
    private MobWatcherTask mobWatcherTask;
    private final Set<ScheduledEvent> scheduledEvents = Sets.newHashSet();

    private static final String NO_SCHEDULES_WARNING = "[DEBUG-MODE] There is no schedules event to load!";
    private static final String GAME_NOT_SET_WARNING = "[DEBUG-MODE] Please set game by adding spawn locations!";
    private static final String PLUGIN_DISABLED_MESSAGE = "\u00a7cWW-Plugin has been disabled!";

    /**
     * Starts the plugin by initializing managers, loading configurations,
     * scheduling tasks, and registering event listeners.
     */
    @Override
    public void onStart() {
        initializeManagers();
        checkGameSetup();
        initializeScheduling();
        registerEventListeners();
        registerCommands();
        // registerMenu();
    }

    /**
     * Stops the plugin by saving scheduled events and canceling running tasks.
     */
    @Override
    public void onStop() {
        saveScheduledEvents();
        disablePlugin();
    }

    /**
     * Initializes the managers used by the plugin.
     */
    private void initializeManagers() {
        configurationManager = new ConfigurationManager(configurations);
        registerConfigurations();
        gameManager = new GameManager(this);
    }

    /**
     * Registers all necessary command(s) for the plugin.
     */
    private void registerCommands() {
        var command = this.plugin.getCommand("werewolf");
        if (command != null) {
            command.setExecutor(new LGCommand(this));
            command.setTabCompleter(new LGCommand(this));
        } else {
            logger.warning("Command 'werewolf' is not registered!");
        }
    }

    /**
     * Registers all necessary configurations for the plugin.
     */
    private void registerConfigurations() {
        configurationManager.register("settings", new SettingsFile(plugin));
        configurationManager.register("events", new EventsFile(plugin));
        configurationManager.register("locations", new LocationsFile(plugin));
        configurationManager.register("schedules", new SchedulesFile(plugin));
        configurationManager.register("messages", new MessagesFile(plugin));
    }

    /**
     * Checks if the game is properly set up.
     * Logs a warning if the setup is incomplete.
     */
    private void checkGameSetup() {
        if (!gameManager.checkSetup()) {
            logger.warning(GAME_NOT_SET_WARNING);
        }
    }

    /**
     * Initializes and schedules events for the plugin.
     * Loads the schedule configuration and starts scheduled tasks.
     */
    private void initializeScheduling() {
        SchedulesFile schedulesFile = (SchedulesFile) configurationManager.getConfiguration("schedules");
        eventScheduler = new EventsScheduler(scheduledEvents);
        schedulesManager = new SchedulesManager(schedulesFile);

        schedulesManager.getRegistry().register("load", new LoadHandler(schedulesFile, scheduledEvents));
        schedulesManager.getRegistry().register("save", new SaveHandler(schedulesFile, scheduledEvents));

        if (!schedulesFile.getYamConfiguration().contains("schedules")) {
            logger.warning(NO_SCHEDULES_WARNING);
        } else {
            schedulesManager.handleProcessByName("load");
            schedulerTask = new SchedulerTask(this);
            schedulerTask.runTaskTimer(plugin, 0, 20);
        }
    }

    /**
     * Registers event listeners based on configuration values.
     */
    private void registerEventListeners() {
        EventsFile eventsFile = (EventsFile) configurationManager.getConfiguration("events");
        EventRetriever eventRetriever = new EventRetriever(eventsFile.getYamConfiguration());

        registerConditionalListeners(eventRetriever);
        registerCoreListeners();
    }

    /**
     * Registers listeners based on configuration settings.
     *
     * @param eventRetriever The event retriever to check for enabled events.
     */
    private void registerConditionalListeners(EventRetriever eventRetriever) {
        registerListenerIfDisabled(eventRetriever, EventPathIdentifiers.END_GAME_EVENT, new EndGameListener(this));
        registerListenerIfDisabled(eventRetriever, EventPathIdentifiers.START_GAME_EVENT, new StartGameListener(this));
        registerListenerIfDisabled(eventRetriever, EventPathIdentifiers.ANNOUNCE_DROP_EVENT,
                new AnnounceDropListener());
        registerListenerIfDisabled(eventRetriever, EventPathIdentifiers.MOB_MOVE_EVENT, new MobMoveListener(this));
        registerListenerIfDisabled(eventRetriever, EventPathIdentifiers.SPAWN_MOB_EVENT, new SpawnMobListener(this));
    }

    private void registerListenerIfDisabled(EventRetriever eventRetriever, EventPathIdentifiers identifier,
            Listener listener) {
        if (!eventRetriever.getBoolean(identifier)) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    /**
     * Registers core listeners that are always needed.
     */
    private void registerCoreListeners() {
        plugin.getServer().getPluginManager().registerEvents(new MenuListeners(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MythicMobDeathListener(this), plugin);
         plugin.getServer().getPluginManager().registerEvents(new
         OtherListeners(this), plugin);
    }

    /**
     * Saves scheduled events if any exist, using the schedule manager.
     */
    private void saveScheduledEvents() {
        if (gameManager != null && !scheduledEvents.isEmpty()) {
            schedulesManager.handleProcessByName("save");
        }
    }

    /**
     * Cancels the scheduled task and logs a message to the console.
     */
    private void disablePlugin() {
        Bukkit.getConsoleSender().sendMessage(PLUGIN_DISABLED_MESSAGE);
        if (schedulerTask != null) {
            schedulerTask.cancel();
        }
    }

    @Override
    public void defineMobWatcherTask(PluginProvider pluginProvider, EventMob mob) {
        mobWatcherTask = new MobWatcherTask(pluginProvider, mob);
    }
}
