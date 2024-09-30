package me.a8kj.ww.internal.task;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.schedule.SchedulerTask;

@Getter
public class MobWatcherTask extends SchedulerTask {

    private final EventMob eventMob;
    private final Location lastLocation;

    public MobWatcherTask(@NonNull PluginProvider pluginProvider, EventMob eventMob) {
        super(pluginProvider);
        this.eventMob = eventMob;
        this.lastLocation = eventMob.getBukkitEntity().get().getLocation();
    }

    @Override
    public void check() {
        if (!eventMob.isValid() || eventMob == null || eventMob.getBukkitEntity().get() == null) {
            this.cancel();
            throw new IllegalStateException("EventMob invaild !");
        }
        Entity entity = eventMob.getBukkitEntity().orElseThrow();
        Location newLocation = entity.getLocation();

        if (!lastLocation.equals(newLocation)) {

        }
    }

}
