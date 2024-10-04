package me.a8kj.ww.internal.command.subcommand.setup;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.LocationsUtils;
import me.a8kj.ww.parent.utils.StringUtils;
import me.a8kj.ww.parent.utils.WorldGuardUtils;

/**
 * Sub-command for adding a spawn location.
 * 
 * <p>
 * This class allows a player to add their current location as a spawn location
 * in the configuration.
 * </p>
 */
public class AddSpawnLocationSub extends PlayerSubCommand {

    /**
     * Constructor for initializing the sub-command.
     *
     * @param pluginProvider the plugin provider instance for accessing plugin
     *                       resources.
     */
    public AddSpawnLocationSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    /**
     * Retrieves the permission required to execute this command.
     * 
     * @return The permission string required.
     */
    @Override
    public String getPermission() {
        return "ww.admin.*";
    }

    /**
     * Retrieves the command usage description.
     * 
     * @return The usage string for the command.
     */
    @Override
    public String getUsage() {
        return "/lg addloc";
    }

    /**
     * Provides a brief description of the command's purpose.
     * 
     * @return The description of the command.
     */
    @Override
    public String getDescription() {
        return "Used to add spawn location entity!";
    }

    /**
     * Executes the command logic for adding a spawn location.
     * 
     * <p>
     * Checks if the player's current location already exists in the configuration.
     * If it does not, the location is saved and a success message is sent.
     * Otherwise, a message indicating the location already exists is sent.
     * </p>
     * 
     * @param args   The command arguments.
     * @param source The player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        Location location = source.getLocation();
        final String section = "spawn-locations";

        if (!WorldGuardUtils.isInRegion(location,
                getSettingsRetriever().getString(SettingsPathIdentifiers.REGION_NAME))) {
            source.sendMessage(StringUtils.legacyColorize("&cYou cannot add location that out the warzone region!"));
            return;
        }
        boolean success = LocationsUtils.savePlayerLocation(location, section, getLocationsFile());

        if (!success) {
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.LOCATION_EXIST));
            return;
        }
        source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.LOCATION_ADDED));
    }
}
