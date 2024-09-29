package me.a8kj.ww.internal.schedules;

import java.util.*;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import me.a8kj.ww.internal.schedules.stragtegies.AddStrategy;
import me.a8kj.ww.internal.schedules.stragtegies.RemoveStrategy;
import me.a8kj.ww.parent.entity.schedule.EventScheduler;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * The {@code EventsScheduler} class implements the {@link EventScheduler}
 * interface
 * for managing scheduled events using a priority queue.
 *
 * <p>
 * This class provides functionality to add or remove scheduled events,
 * ensuring they are processed in the order of their execution time.
 * </p>
 */
@Getter
public class EventsScheduler implements EventScheduler {

    /**
     * A priority queue that holds scheduled events, ordered by their execution
     * time.
     */
    private final PriorityQueue<ScheduledEvent> scheduledEventsQueue = new PriorityQueue<>(
            Comparator.comparing(ScheduledEvent::getExecutionTime));

    /**
     * A set that contains all scheduled events to ensure uniqueness.
     */
    private final Set<ScheduledEvent> scheduledEventsSet;

    /**
     * Constructs a new {@code EventsScheduler} with the specified set of scheduled
     * events.
     *
     * @param scheduledEventsSet the set of scheduled events to be managed
     */
    public EventsScheduler(@NonNull Set<ScheduledEvent> scheduledEventsSet) {
        this.scheduledEventsSet = scheduledEventsSet;
    }

    /**
     * Schedules a new event or removes an existing event based on the specified
     * type.
     *
     * <p>
     * This method delegates the execution of adding or removing events to the
     * respective
     * strategy classes, {@link AddStrategy} and {@link RemoveStrategy}.
     * An exception is thrown if the specified type is invalid.
     * </p>
     *
     * @param scheduledEvent the event to be scheduled or removed
     * @param type           the type of operation (ADD or REMOVE)
     * @throws IllegalArgumentException if the specified type is not valid
     */
    @SneakyThrows
    @Override
    public void schedule(@NonNull ScheduledEvent scheduledEvent, @NonNull ScheduledType type) {
        switch (type) {
            case ADD -> new AddStrategy(scheduledEvent).execute(scheduledEventsQueue, scheduledEventsSet);
            case REMOVE -> new RemoveStrategy(scheduledEvent).execute(scheduledEventsQueue, scheduledEventsSet);
            default -> throw new IllegalArgumentException("Invalid ScheduledType!");
        }
    }
}
