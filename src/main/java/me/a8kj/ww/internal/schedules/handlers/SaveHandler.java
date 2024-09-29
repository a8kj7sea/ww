//
//
package me.a8kj.ww.internal.schedules.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.NonNull;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;
import me.a8kj.ww.parent.entity.schedule.manager.EventHandler;

/**
 * The {@code SaveHandler} class is responsible for saving scheduled events
 * to the configuration.
 * 
 * <p>
 * This class extends {@link EventHandler} and implements the logic to handle
 * saving scheduled events to the configuration file.
 * </p>
 */
public class SaveHandler extends EventHandler {

    /**
     * Constructs a new {@code SaveHandler} with the given configuration and
     * scheduled events.
     *
     * @param configuration   the configuration object containing the schedule
     *                        section
     * @param scheduledEvents the set of scheduled events to be saved
     */
    public SaveHandler(@NonNull Configuration configuration, @NonNull Set<ScheduledEvent> scheduledEvents) {
        super(configuration, scheduledEvents);
    }

    /**
     * Saves the scheduled events to the configuration file.
     * 
     * <p>
     * This method checks if the set of scheduled events is not null or empty.
     * If there are events to save, it adds them to the configuration and
     * saves the changes. A debug message is logged upon successful saving.
     * </p>
     */
    @Override
    public void handle() {

        // Check if there are scheduled events to save
        if (scheduledEvents == null || scheduledEvents.isEmpty()) {
            // If there are no events, silently return
            return;
        }

        // Get the YAML configuration from the Configuration object
        YamlConfiguration yamlConfiguration = configuration.getYamConfiguration();

        // Convert the set of scheduled events to a list
        List<ScheduledEvent> events = new ArrayList<>(scheduledEvents);

        // Set the list of events in the configuration under the schedules section
        yamlConfiguration.set(schedulesSection, events);

        // Save the configuration to the file
        configuration.save();

        // Log a debug message confirming successful saving of events
        Bukkit.getLogger().info("[DEBUG-MODE] Scheduled events saved successfully!");
    }
}
