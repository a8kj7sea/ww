package me.a8kj.ww.internal.command.subcommand.menu;

import java.util.Set;

import org.bukkit.entity.Player;

import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.menu.SchedulesMenu;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * Command to display a menu of scheduled events for players.
 *
 * <p>
 * This class extends {@link PlayerSubCommand} and provides the functionality
 * to show a list of scheduled events in an inventory menu.
 * </p>
 */
public class SchedulesMenuSub extends PlayerSubCommand {

    public SchedulesMenuSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    @Override
    public String getUsage() {
        return "/lg schedules"; // Command usage description
    }

    @Override
    public String getDescription() {
        return "Displays a list of scheduled events in your inventory."; // Command description
    }

    /**
     * Executes the command to open the schedules menu.
     *
     * @param args   the command arguments.
     * @param source the player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        Set<ScheduledEvent> scheduledEvents = pluginProvider.getEventScheduler().getScheduledEvents();

        if (scheduledEvents.isEmpty()) {
            // Notify the player if there are no scheduled events
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_LIST_EMPTY));
            return;
        }

        // Retrieve and open the schedules menu
        SchedulesMenu schedulesMenu = (SchedulesMenu) pluginProvider.getMenus().get("schedules");

        source.getOpenInventory().close(); // Close the current inventory
        source.updateInventory(); // Update the player's inventory state
        source.openInventory(schedulesMenu.build()); // Open the schedules menu
        source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_LIST_SUCCESS)); // Notify
                                                                                                                    // player
    }
}
