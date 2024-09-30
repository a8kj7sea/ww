package me.a8kj.ww.internal.task;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.ww.api.event.mob.impl.MobMoveEvent;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.schedule.SchedulerTask;

/**
 * Task that monitors the movement of a specific EventMob.
 * Triggers a MobMoveEvent when the EventMob changes its location.
 */
@Getter
public class MobWatcherTask extends SchedulerTask {

    private final EventMob eventMob;
    private Location lastLocation;

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
        // Check if the EventMob is valid
        if (!eventMob.isValid() || eventMob.getBukkitEntity().isEmpty() || !eventMob.isAlive()) {
            this.cancel();
            throw new IllegalStateException("EventMob invalid!");
        }

        Entity entity = eventMob.getBukkitEntity().orElseThrow();
        Location newLocation = entity.getLocation();

        // Check for movement
        if (!lastLocation.equals(newLocation)) {
            new MobMoveEvent(eventMob, lastLocation, newLocation).callEvent();
            lastLocation = newLocation;
        }
    }
}
