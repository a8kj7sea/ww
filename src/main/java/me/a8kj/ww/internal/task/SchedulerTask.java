package me.a8kj.ww.internal.task;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

import org.bukkit.Bukkit;

import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.internal.schedules.EventsScheduler;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.plugin.PluginTask;
import me.a8kj.ww.parent.entity.schedule.EventScheduler.ScheduledType;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * A scheduled task for managing event execution in the game.
 *
 * <p>
 * This task checks for scheduled events and starts a game if conditions are
 * met, such as having enough players and no ongoing game.
 * </p>
 */
public class SchedulerTask extends PluginTask {

    /**
     * Constructs a new SchedulerTask with the given plugin provider.
     *
     * @param pluginProvider The plugin provider to access game and event
     *                       management.
     */
    public SchedulerTask(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    /**
     * Checks the scheduled events and starts a game if conditions are met.
     *
     * <p>
     * This method iterates through the scheduled events and checks if the next
     * event is due. If it is, it checks if a game can be started based on the
     * number of online players and the current game state.
     * </p>
     */
    @Override
    public void check() {
        EventsScheduler eventScheduler = (EventsScheduler) pluginProvider.getEventScheduler();
        PriorityQueue<ScheduledEvent> scheduledEventsQueue = eventScheduler.getScheduledEventsQueue();
        LocalDateTime now = LocalDateTime.now();

        while (!scheduledEventsQueue.isEmpty()) {
            ScheduledEvent nextEvent = scheduledEventsQueue.peek();

            // Break the loop if no more events or the next event is not due
            if (nextEvent == null || !nextEvent.getExecutionTime().isBefore(now)) {
                break;
            }

            GameManager gameManager = pluginProvider.getGameManager();
            if (!gameManager.checkSetup()) {
                Bukkit.getLogger().warning(
                        "[DEBUG-MODE] Couldn't start event right now; please ensure you have setup spawn locations.");
                return; // Exit early if setup is incomplete
            }

            // Proceed if there is no current game
            if (!gameManager.getCurrentGame().isPresent()) {
                if (Bukkit.getOnlinePlayers().size() <= 1) {
                    Bukkit.getLogger().warning("[DEBUG-MODE] Not enough players to start the event!");
                    return; // Exit early if not enough players
                }

                // Remove and execute the event
                eventScheduler.schedule(nextEvent, ScheduledType.REMOVE);
                gameManager.createGame();
                gameManager.startGame();
            } else {
                EventGame game = gameManager.getCurrentGame().get();

                // Check if the current game is in progress
                if (game.getGameState() == GameState.INGAME) {
                    Bukkit.getLogger().warning("[DEBUG-MODE] An event is currently running!");
                    return; // Exit early if an event is running
                }

                // Remove and execute the event
                eventScheduler.schedule(nextEvent, ScheduledType.REMOVE);
                gameManager.createGame();
                gameManager.startGame();
            }
        }
    }
}
