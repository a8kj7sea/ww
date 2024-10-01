package me.a8kj.ww.parent.entity.schedule;

import java.util.Set;

/**
 * The EventScheduler interface defines the contract for creating and
 * managing scheduled events.
 */
public interface EventScheduler {

    /**
     * Schedules a specified event with the given type.
     *
     * @param scheduledEvent the event to be scheduled.
     * @param type           the type of scheduling action (ADD or REMOVE).
     */
    void schedule(ScheduledEvent scheduledEvent, ScheduledType type);

    /**
     * Retrieves all currently scheduled events.
     *
     * @return a set of scheduled events.
     */
    Set<ScheduledEvent> getScheduledEvents();

    /**
     * Enum representing the types of scheduling actions.
     */
    enum ScheduledType {
        ADD, REMOVE; // Actions to add or remove scheduled events
    }
}
