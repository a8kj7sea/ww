package me.a8kj.ww.internal.command.subcommand.other;

import java.util.Map;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import me.a8kj.ww.parent.command.SubCommand;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.StringUtils;

/**
 * Command to display help information for plugin commands.
 *
 * <p>
 * This class extends {@link PlayerSubCommand} and provides players with
 * information about available commands in the plugin.
 * </p>
 */
public class HelpSub extends PlayerSubCommand {

    Map<String, SubCommand<Player>> subCommands = Maps.newHashMap();

    public HelpSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    @Override
    public String getUsage() {
        return "/lg help"; // Command usage description
    }

    @Override
    public String getDescription() {
        return "Used to help you about commands in the plugin"; // Command description
    }

    /**
     * Executes the command to display available commands to the player.
     *
     * @param args   the command arguments.
     * @param source the player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        StringBuilder helpMessage = new StringBuilder();
        String headerFooter = "&b======================================"; // Define a
                                                                          // header/footer
                                                                          // for the message

        helpMessage.append(headerFooter).append("\n");

        // Iterate through the subcommands using entrySet()
        for (SubCommand<Player> subCommand : subCommands.values()) {
            helpMessage.append(subCommand.getInfo()).append("\n");
        }

        helpMessage.append(headerFooter);

        // Send the help message to the player
        source.sendMessage(StringUtils.legacyColorize(helpMessage.toString()));
    }

    /**
     * Defines the available subcommands for the help command.
     *
     * @param subCommands a map of subcommands to be displayed in the help message.
     */
    public void defineSubCommands(Map<String, SubCommand<Player>> subCommands) {
        this.subCommands = subCommands;
    }
}
