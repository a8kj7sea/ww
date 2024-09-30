package me.a8kj.ww.parent.command.impl;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.files.LocationsFile;
import me.a8kj.ww.internal.configuration.files.MessagesFile;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.internal.configuration.retrievers.messages.MessageRetriever;
import me.a8kj.ww.internal.manager.ConfigurationManager;
import me.a8kj.ww.parent.command.SubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Abstract class representing a sub-command for players.
 *
 * <p>
 * This class implements {@link SubCommand} and provides utility methods
 * for accessing plugin configurations and resources related to sub-commands
 * that are executed by players.
 * </p>
 * 
 * <p>
 * Sub-classes must implement the specific logic for the sub-command
 * being defined.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public abstract class PlayerSubCommand implements SubCommand<Player> {

    /**
     * The plugin provider used to access the plugin's core resources and services.
     */
    protected final PluginProvider pluginProvider;

    /**
     * Gets the {@link ConfigurationManager} to manage plugin configurations.
     * 
     * @return The configuration manager.
     */
    protected ConfigurationManager getConfigurationManager() {
        return pluginProvider.getConfigurationManager();
    }

    /**
     * Retrieves the {@link JavaPlugin} instance associated with this plugin.
     * 
     * @return The JavaPlugin instance.
     */
    protected JavaPlugin getPlugin() {
        return pluginProvider.getPlugin();
    }

    /**
     * Retrieves the {@link SettingsFile} that contains the plugin's settings.
     * 
     * @return The settings configuration file.
     */
    protected SettingsFile getSettingsFile() {
        return (SettingsFile) getConfigurationManager().getConfiguration("settings");
    }

    /**
     * Retrieves the {@link LocationsFile} that contains the locations.
     * 
     * @return The locations configuration file.
     */
    protected LocationsFile getLocationsFile() {
        return (LocationsFile) getConfigurationManager().getConfiguration("locations");
    }

    /**
     * Retrieves the {@link MessagesFile} that contains the plugin's messages.
     * 
     * @return The messages configuration file.
     */
    protected MessagesFile getMessagesFile() {
        return (MessagesFile) getConfigurationManager().getConfiguration("messages");
    }

    /**
     * Creates a new {@link MessageRetriever} for accessing messages from the
     * messages configuration file.
     * 
     * @return A new instance of MessageRetriever.
     */
    protected MessageRetriever getMessageRetriever() {
        return new MessageRetriever(getMessagesFile().getYamConfiguration());
    }

    /**
     * Creates a new {@link SettingsRetriever} for accessing settings from the
     * settings configuration file.
     * 
     * @return A new instance of SettingsRetriever.
     */
    protected SettingsRetriever getSettingsRetriever() {
        return new SettingsRetriever(getSettingsFile().getYamConfiguration());
    }
}
