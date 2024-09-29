package me.a8kj.ww.internal.schedules.handlers;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.IllegalPluginAccessException;

import lombok.NonNull;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;
import me.a8kj.ww.parent.entity.schedule.manager.EventHandler;

/**
 * The {@code LoadHandler} class is responsible for loading scheduled events
 * from the
 * configuration and adding them to the list of loaded events.
 * 
 * <p>
 * This class extends {@link EventHandler} and implements the logic to handle
 * loading scheduled events by reading the schedule section from the
 * configuration file.
 * </p>
 */
@SuppressWarnings("unchecked")
public class LoadHandler extends EventHandler {

    /**
     * Constructs a new {@code LoadHandler} with the given configuration and
     * scheduled events.
     *
     * @param configuration   the configuration object containing the schedule
     *                        section
     * @param scheduledEvents the set of scheduled events to be loaded
     */
    public LoadHandler(@NonNull Configuration configuration, @NonNull Set<ScheduledEvent> scheduledEvents) {
        super(configuration, scheduledEvents);
    }

    /**
     * Loads scheduled events from the configuration and adds them to the list of
     * loaded events.
     *
     * <p>
     * This method reads the schedule section from the configuration file and adds
     * all the events to the {@code loadedEvents} set. If the event list is null or
     * empty,
     * it throws an {@link IllegalPluginAccessException}.
     * </p>
     *
     * @throws IllegalPluginAccessException if the scheduled event list is null or
     *                                      empty
     */
    @Override
    public void handle() {

        // Get the YAML configuration from the Configuration object
        YamlConfiguration yamlConfiguration = this.configuration.getYamConfiguration();

        // Retrieve the list of scheduled events from the 'schedules' section
        List<ScheduledEvent> eventList = (List<ScheduledEvent>) yamlConfiguration.getList(schedulesSection);

        // If no events are found, throw an exception
        if (eventList == null || eventList.isEmpty()) {
            throw new IllegalPluginAccessException(
                    "Schedule event list cannot be null or empty. Please ensure you have scheduled events and reload the plugin.");
        }

        // Add the retrieved events to the loaded events set
        loadedEvents.addAll(eventList);

        // Log a debug message confirming successful loading of events
        Bukkit.getLogger().info("[DEBUG-MODE] You have loaded scheduledEvents successfully!");
    }
}
