package me.a8kj.ww.internal.command.subcommand.other;

import org.bukkit.entity.Player;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.configuration.files.MessagesFile;
import me.a8kj.ww.internal.configuration.retrievers.messages.MessageRetriever;
import me.a8kj.ww.internal.manager.ConfigurationManager;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Sub-command for reloading plugin configuration files.
 * 
 * <p>
 * This command allows an administrator with the required permission to
 * reload all the configuration files of the plugin. It will send a success
 * message to the player upon completion.
 * </p>
 */
public class ReloadConfig extends PlayerSubCommand {

    /**
     * Constructor to initialize the ReloadConfig sub-command.
     * 
     * @param pluginProvider The {@link PluginProvider} instance for accessing
     *                       the plugin's resources and configurations.
     */
    public ReloadConfig(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    /**
     * Retrieves the permission required to execute this command.
     * 
     * @return The permission node as a string, in this case, "ww.admin.*".
     */
    @Override
    public String getPermission() {
        return "ww.admin.*";
    }

    /**
     * Provides the usage information for this command.
     * 
     * @return A string representing the correct usage of this command.
     */
    @Override
    public String getUsage() {
        return "/lg reload";
    }

    /**
     * Provides a short description of this sub-command.
     * 
     * @return A brief description of what the command does.
     */
    @Override
    public String getDescription() {
        return "Used to reload configuration files in the plugin.";
    }

    /**
     * Executes the command to reload all configurations.
     * 
     * <p>
     * This method reloads all the configuration files associated with the
     * plugin by invoking the {@link Configuration#load()} method. It then
     * sends a success message to the player who executed the command.
     * </p>
     * 
     * @param args   The arguments passed with the command.
     * @param source The player who executed the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        for (Configuration configuration : getConfigurationManager().getAllConfigurations()) {
            configuration.load();
        }

        // Send success message to the player
        MessageRetriever messageRetriever = getMessageRetriever();
        source.sendMessage(messageRetriever.getMessage(MessagePathIdentifiers.COMMAND_RELOAD_SUCCESS));
    }
}
