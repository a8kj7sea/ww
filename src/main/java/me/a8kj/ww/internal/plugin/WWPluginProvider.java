package me.a8kj.ww.internal.plugin;

import java.util.*;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
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
import me.a8kj.ww.internal.menu.LocationsMenu;
import me.a8kj.ww.internal.menu.SchedulesMenu;
import me.a8kj.ww.internal.schedules.EventsScheduler;
import me.a8kj.ww.internal.schedules.SchedulesManager;
import me.a8kj.ww.internal.schedules.handlers.LoadHandler;
import me.a8kj.ww.internal.schedules.handlers.SaveHandler;
import me.a8kj.ww.internal.task.SchedulerTask;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.menu.Menu;
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

    private final Set<ScheduledEvent> scheduledEvents = Sets.newHashSet();

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
     * Registers all necessary menu(s) for the plugin.
     */
    private void registerMenu() {
        this.menus.put("schedules", new SchedulesMenu(this));
        this.menus.put("locations", new LocationsMenu(this));
    }

    /**
     * Registers all necessary command(s) for the plugin.
     */
    private void registerCommands() {
        this.getPlugin().getCommand("werewolf").setExecutor(new LGCommand(this));
        this.getPlugin().getCommand("werewolf").setTabCompleter(new LGCommand(this));
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
            logger.warning("[DEBUG-MODE] Please set game by adding spawn locations!");
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
            logger.warning("[DEBUG-MODE] There is no schedules event to load!");
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
        if (!eventRetriever.getBoolean(EventPathIdentifiers.END_GAME_EVENT)) {
            plugin.getServer().getPluginManager().registerEvents(new EndGameListener(this), plugin);
        }
        if (!eventRetriever.getBoolean(EventPathIdentifiers.START_GAME_EVENT)) {
            plugin.getServer().getPluginManager().registerEvents(new StartGameListener(this), plugin);
        }
        if (!eventRetriever.getBoolean(EventPathIdentifiers.ANNOUNCE_DROP_EVENT)) {
            plugin.getServer().getPluginManager().registerEvents(new AnnounceDropListener(), plugin);
        }
        if (!eventRetriever.getBoolean(EventPathIdentifiers.MOB_MOVE_EVENT)) {
            plugin.getServer().getPluginManager().registerEvents(new MobMoveListener(this), plugin);
        }
        if (!eventRetriever.getBoolean(EventPathIdentifiers.SPAWN_MOB_EVENT)) {
            plugin.getServer().getPluginManager().registerEvents(new SpawnMobListener(this), plugin);
        }
    }

    /**
     * Registers core listeners that are always needed.
     */
    private void registerCoreListeners() {
        plugin.getServer().getPluginManager().registerEvents(new MenuListeners(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MythicMobDeathListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new OtherListeners(this), plugin);
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
        Bukkit.getConsoleSender().sendMessage("\u00a7cWW-Plugin has been disabled!");
        if (schedulerTask != null) {
            schedulerTask.cancel();
        }
    }

}
