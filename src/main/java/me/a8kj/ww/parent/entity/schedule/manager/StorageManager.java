package me.a8kj.ww.parent.entity.schedule.manager;

import java.util.*;

import me.a8kj.ww.parent.configuration.Configuration;

/**
 * The StorageManager interface manages the storage and processing of
 * scheduled events.
 */
public interface StorageManager {

    /**
     * Retrieves the configuration associated with scheduled events.
     *
     * @return the Configuration instance
     */
    public Configuration getConfiguration();

    /**
     * Handles the processing of a scheduled event by its name.
     *
     * @param name the name of the scheduled event to process
     */
    public void handleProcessByName(String name);

    /**
     * Retrieves a map of scheduled event handlers indexed by their names.
     *
     * @return a map of event handlers
     */
    public Map<String, EventHandler> getHandlers();

    /**
     * Retrieves the registry that contains all scheduled event storage details.
     *
     * @return the HandlerRegistry instance
     */
    public HandlerRegistry getRegistry();
}
