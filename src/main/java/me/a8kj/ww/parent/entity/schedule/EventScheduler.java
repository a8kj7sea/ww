package me.a8kj.ww.parent.entity.schedule;

/**
 * The EventScheduler interface defines the contract for creating and
 * managing scheduled events.
 */
public interface EventScheduler {

    /**
     * Schedules a specified event with the given type.
     * 
     * @param scheduledEvent the event to be scheduled.
     * @param type           the type of scheduling action (add or remove).
     */
    void schedule(ScheduledEvent scheduledEvent, ScheduledType type);

    /**
     * Enum representing the types of scheduling actions.
     */
    enum ScheduledType {
        ADD, REMOVE;
    }
}
