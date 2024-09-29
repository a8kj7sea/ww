package me.a8kj.ww.parent.entity.schedule;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class SchedulerTask extends BukkitRunnable {

    @Override
    public void run() {
        check();
    }

    public abstract void check();
}
