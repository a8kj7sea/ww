package me.a8kj.ww.internal.schedules.stragtegies;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

import org.bukkit.Bukkit;

import me.a8kj.ww.parent.entity.schedule.EventStrategy;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

public class RemoveStrategy extends EventStrategy {

    public RemoveStrategy(ScheduledEvent scheduledEvent) {
        super(Objects.requireNonNull(scheduledEvent,
                () -> "ScheduledEvent cannot be null. Please provide a valid event."));
    }

    @Override
    public void execute(PriorityQueue<ScheduledEvent> eventQueue, Set<ScheduledEvent> scheduledEvents) {
        if (!scheduledEvents.contains(scheduledEvent)) {
            Bukkit.getLogger().info("[DEBUG-MODE] You cannot remove this scheduled event cuz not found!");
            return;
        }

        scheduledEvents.remove(scheduledEvent);
        eventQueue.poll();
        Bukkit.getLogger().info("[DEBUG-MODE] You have removed this scheduled event! " + scheduledEvent.toString());
    }

}
