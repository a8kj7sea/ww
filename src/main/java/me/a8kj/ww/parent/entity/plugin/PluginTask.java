package me.a8kj.ww.parent.entity.plugin;

import org.bukkit.scheduler.BukkitRunnable;

import lombok.*;

/**
 * Abstract class for scheduled tasks in a Bukkit environment.
 * Extends BukkitRunnable for easy scheduling.
 */
@RequiredArgsConstructor
@Getter
public abstract class PluginTask extends BukkitRunnable {

    protected final PluginProvider pluginProvider; 

    /**
     * Executes the scheduled task.
     * Calls the {@link #check()} method defined by subclasses.
     */
    @Override
    public void run() {
        check(); // Execute the task
    }

    /**
     * Defines the task's specific behavior.
     * Must be implemented by subclasses.
     */
    public abstract void check();
}
