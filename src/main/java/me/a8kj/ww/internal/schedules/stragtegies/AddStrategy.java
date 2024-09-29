package me.a8kj.ww.internal.schedules.stragtegies;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

import org.bukkit.Bukkit;

import me.a8kj.ww.parent.entity.schedule.EventStrategy;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

public class AddStrategy extends EventStrategy {

    public AddStrategy(ScheduledEvent scheduledEvent) {
        super(Objects.requireNonNull(scheduledEvent,
                () -> "ScheduledEvent cannot be null. Please provide a valid event."));
    }

    @Override
    public void execute(PriorityQueue<ScheduledEvent> eventQueue, Set<ScheduledEvent> scheduledEvents) {
        if (scheduledEvents.contains(scheduledEvent)) {
            Bukkit.getLogger().info("[DEBUG-MODE] You cannot add this scheduled event is already exist!");
            return;
        }

        scheduledEvents.add(scheduledEvent);
        eventQueue.offer(scheduledEvent);
        Bukkit.getLogger().info(
                "[DEBUG-MODE] You have added new scheduled event! with these information " + scheduledEvent.toString());
    }

}
