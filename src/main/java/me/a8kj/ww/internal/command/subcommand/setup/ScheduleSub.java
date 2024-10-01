package me.a8kj.ww.internal.command.subcommand.setup;

import java.time.DayOfWeek;

import org.bukkit.entity.Player;

import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.entity.schedule.EventScheduler.ScheduledType;
import me.a8kj.ww.parent.entity.schedule.helper.TimeMatcher;
import me.a8kj.ww.parent.entity.schedule.ScheduledEvent;

/**
 * Command to schedule events for players.
 *
 * <p>
 * This class extends {@link PlayerSubCommand} and allows players to
 * schedule events on specific days at designated times.
 * </p>
 */
public class ScheduleSub extends PlayerSubCommand {

    public ScheduleSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    @Override
    public String getUsage() {
        return "/lg schedule <day> <HH:MM>"; // Command usage description
    }

    @Override
    public String getDescription() {
        return "Used to schedule an event on a specific day at a specific time"; // Command description
    }

    /**
     * Executes the command to schedule an event.
     *
     * <p>
     * This method validates the input arguments, parses the day and time,
     * and schedules the event if the inputs are valid.
     * </p>
     *
     * @param args   the command arguments.
     * @param source the player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        if (args.length != 3) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_WRONG_USAGE));
            return;
        }

        String day = args[1].toUpperCase();
        String time = args[2];

        try {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
            TimeMatcher timeMatcher = new TimeMatcher(time);

            if (!timeMatcher.matches()) {
                source.sendMessage(
                        getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_INVALID_TIME));
                return;
            }

            String[] timeParts = time.split(":");
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);

            ScheduledEvent scheduledEvent = new ScheduledEvent(dayOfWeek.name(), hours, minutes);

            if (getPluginProvider().getEventScheduler().getScheduledEvents().contains(scheduledEvent)) {
                source.sendMessage(
                        getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_DUPLICATE_EVENT));
                return;
            }

            getPluginProvider().getEventScheduler().schedule(scheduledEvent, ScheduledType.ADD);
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_SUCCESS));

        } catch (IllegalArgumentException e) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_INVALID_DAY));
        } catch (Exception e) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.COMMAND_SCHEDULE_INVALID_TIME));
        }
    }
}
