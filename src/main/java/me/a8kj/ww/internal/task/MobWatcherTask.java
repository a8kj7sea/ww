package me.a8kj.ww.internal.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.ww.api.event.mob.impl.MobMoveEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.plugin.PluginTask;

/**
 * Task that monitors the movement of a specific EventMob.
 * Triggers a MobMoveEvent when the EventMob changes its location.
 */
@Getter
public class MobWatcherTask extends PluginTask {

    private final EventMob eventMob;
    private Location lastLocation;

    public enum TaskExecuteType {
        START, STOP;
    }

    /**
     * Constructor for MobWatcherTask.
     *
     * @param pluginProvider The PluginProvider instance.
     * @param eventMob       The EventMob to watch.
     */
    public MobWatcherTask(@NonNull PluginProvider pluginProvider, @NonNull EventMob eventMob) {
        super(pluginProvider);
        this.eventMob = eventMob;
        this.lastLocation = eventMob.getBukkitEntity().get().getLocation();
    }

    @Override
    public void check() {
        // Check if the EventMob is valid and alive
        if (!isEventMobValid()) {
            cancelTaskWithLogging();
            return;
        }

        Entity entity = eventMob.getBukkitEntity().orElseThrow();
        Location newLocation = entity.getLocation();

        // Check for movement
        if (!lastLocation.equals(newLocation)) {
            triggerMobMoveEvent(newLocation);
        }
    }

    private boolean isEventMobValid() {
        return eventMob.isValid() && eventMob.getBukkitEntity().isPresent() && eventMob.isAlive();
    }

    private void cancelTaskWithLogging() {
        this.cancel();
        // Log the cancellation reason
        Bukkit.getLogger().warning("[MobWatcherTask] EventMob is invalid or not alive! Task cancelled.");
    }

    private void triggerMobMoveEvent(Location newLocation) {
        new MobMoveEvent(eventMob, lastLocation, newLocation).callEvent();
        lastLocation = newLocation; // Update the last known location
    }

    public void execute(TaskExecuteType type) {
        switch (type) {
            case START:
                startTask();
                break;
            case STOP:
                this.cancel();
                break;
        }
    }

    private void startTask() {
        this.runTaskTimer(pluginProvider.getPlugin(), 0, 20);
        Bukkit.getLogger().info("[MobWatcherTask] Task started for EventMob: " + eventMob);
    }
}
