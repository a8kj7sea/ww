package me.a8kj.ww.internal.command;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.NonNull;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.configuration.files.MessagesFile;
import me.a8kj.ww.internal.configuration.retrievers.messages.MessageRetriever;
import me.a8kj.ww.parent.command.SubCommand;
import me.a8kj.ww.parent.command.impl.PlayerCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Command class for handling player commands related to the plugin.
 * <p>
 * This class extends {@link PlayerCommand} to manage sub-commands
 * for player actions within the plugin.
 * </p>
 */
public class LGCommand extends PlayerCommand {

    /**
     * Constructs a new LGCommand with the specified PluginProvider.
     *
     * @param pluginProvider The PluginProvider for accessing plugin resources.
     */
    public LGCommand(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    /**
     * Completes the command arguments for tab completion.
     *
     * @param sender  The sender of the command.
     * @param command The command being executed.
     * @param label   The label of the command.
     * @param args    The arguments provided to the command.
     * @return A list of possible completions based on the current arguments.
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(sub -> sub.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if ("schedule".equalsIgnoreCase(args[0]) && args.length == 2) {
            return Arrays.stream(DayOfWeek.values())
                    .map(DayOfWeek::name)
                    .filter(day -> day.startsWith(args[1].toUpperCase()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    /**
     * Executes the command with the given arguments.
     *
     * @param sender  The sender of the command.
     * @param command The command being executed.
     * @param label   The label of the command.
     * @param args    The arguments provided to the command.
     * @return True if the command was successfully executed, false otherwise.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MessagesFile messagesFile = (MessagesFile) this.pluginProvider.getConfigurationManager()
                .getConfiguration("messages");
        MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());

        if (!(sender instanceof Player)) {
            sender.sendMessage(messageRetriever.getMessage(MessagePathIdentifiers.SYSTEM_ONLY_PLAYER));
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("ww.admin.*")) {
            player.sendMessage(messageRetriever.getMessage(MessagePathIdentifiers.SYSTEM_NO_PERM));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(messageRetriever.getMessage(MessagePathIdentifiers.SYSTEM_UNKNOWN_COMMAND));
            return false;
        }

        String subCommand = args[0].toLowerCase();
        SubCommand<Player> subToExecute = subCommands.get(subCommand);

        if (subToExecute == null) {
            player.sendMessage(messageRetriever.getMessage(MessagePathIdentifiers.SYSTEM_UNKNOWN_COMMAND));
            return false;
        }

        if (!player.hasPermission(subToExecute.getPermission()) && subToExecute.getPermission() != null) {
            player.sendMessage(messageRetriever.getMessage(MessagePathIdentifiers.SYSTEM_NO_PERM));
            return false;
        }

        subToExecute.execute(args, player);
        return true;
    }

    /**
     * Defines the sub-commands for this command.
     *
     * @param subCommands A map of sub-command names to their respective SubCommand
     *                    instances.
     */
    @Override
    public void defineSubs(@NonNull Map<String, SubCommand<Player>> subCommands) {
        // Implement sub-command definitions here
    }
}
