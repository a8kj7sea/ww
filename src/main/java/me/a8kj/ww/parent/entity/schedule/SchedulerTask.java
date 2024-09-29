package me.a8kj.ww.parent.entity.schedule;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * An abstract class representing a scheduled task in a Bukkit environment.
 *
 * <p>This class extends BukkitRunnable, allowing for easy scheduling of tasks
 * using the Bukkit scheduler. It provides a framework for tasks that need to
 * perform periodic checks or actions.</p>
 */
public abstract class SchedulerTask extends BukkitRunnable {

    /**
     * Executes the scheduled task.
     *
     * <p>This method is called by the Bukkit scheduler when the task is
     * triggered. It invokes the {@link #check()} method, which must be
     * implemented by subclasses to define the specific task behavior.</p>
     */
    @Override
    public void run() {
        check(); // Call the check method to perform the task's specific actions
    }

    /**
     * Defines the specific behavior of the scheduled task.
     *
     * <p>Subclasses must implement this method to provide the functionality 
     * that should be executed when the task runs.</p>
     */
    public abstract void check();
}
