package me.a8kj.ww.parent.entity.schedule.manager;

import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * Abstract class representing a handler for scheduled events.
 * 
 * <p>
 * This class serves as a base for implementing specific event handling logic
 * based on the provided configuration.
 * </p>
 * 
 * <p>
 * Each subclass of this abstract class should implement the {@link #handle()}
 * method to define the specific behavior for handling scheduled events.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public abstract class EventHandler {

    /** The configuration object used for scheduled events. */
    protected final @NonNull Configuration configuration;

    /** The set of currently loaded scheduled events. */
    protected final @NonNull Set<ScheduledEvent> scheduledEvents;

    /** The section name in the configuration file where schedules are defined. */
    protected static final String schedulesSection = "schedules";

    /**
     * Handles the logic for processing scheduled events.
     * 
     * <p>
     * This method must be implemented by subclasses to define the specific
     * behavior required for handling the scheduled events.
     * </p>
     */
    public abstract void handle();
}
