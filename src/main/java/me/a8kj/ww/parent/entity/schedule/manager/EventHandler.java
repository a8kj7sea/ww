package me.a8kj.ww.parent.entity.schedule.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.Configuration;

/**
 * Abstract class representing a handler for scheduled events.
 * 
 * <p>
 * This class serves as a base for implementing specific event handling logic
 * based on the provided configuration.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public abstract class EventHandler {

    /** The configuration for scheduled events. */
    protected final Configuration configuration;

    /** The section name for schedules in the configuration. */
    protected static final String schedulesSection = "schedules";

    /**
     * Handles the scheduled event logic.
     * 
     * <p>
     * This method must be implemented by subclasses to define specific handling
     * behavior for the event.
     * </p>
     */
    public abstract void handle();
}
