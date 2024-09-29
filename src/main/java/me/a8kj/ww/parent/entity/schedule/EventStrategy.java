package me.a8kj.ww.parent.entity.schedule;

import java.util.PriorityQueue;
import java.util.Set;

import lombok.*;

/**
 * Abstract base class for defining event strategies.
 */
@RequiredArgsConstructor
@Getter
public abstract class EventStrategy {

    protected final @NonNull ScheduledEvent scheduledEvent;

    /**
     * Executes the strategy logic for the event.
     *
     * @param eventQueue      the priority queue of scheduled events
     * @param scheduledEvents the set of all scheduled events
     */
    public abstract void execute(@NonNull PriorityQueue<ScheduledEvent> eventQueue,
            @NonNull Set<ScheduledEvent> scheduledEvents);
}
