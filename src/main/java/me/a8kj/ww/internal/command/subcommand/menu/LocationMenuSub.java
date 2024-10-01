package me.a8kj.ww.internal.command.subcommand.menu;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.menu.LocationsMenu;
import me.a8kj.ww.parent.command.impl.PlayerSubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Command to display a menu of saved locations for players.
 *
 * <p>
 * This class extends {@link PlayerSubCommand} and provides the functionality
 * to show the list of saved locations in an inventory menu.
 * </p>
 */
public class LocationMenuSub extends PlayerSubCommand {

    public LocationMenuSub(PluginProvider pluginProvider) {
        super(pluginProvider);
    }

    @Override
    public String getUsage() {
        return "/lg locations"; // Command usage description
    }

    @Override
    public String getDescription() {
        return "Displays a list of saved locations in your inventory."; // Command description
    }

    /**
     * Executes the command to open the locations menu.
     *
     * @param args   the command arguments.
     * @param source the player executing the command.
     */
    @Override
    public void execute(String[] args, Player source) {
        List<Location> locations = getLocationsFile().getLocations(); // Retrieve saved locations
        if (locations.isEmpty()) {
            // Notify the player if there are no saved locations
            source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.LOCATION_LIST_EMPTY));
            return;
        }

        // Retrieve and open the locations menu
        LocationsMenu locationsMenu = (LocationsMenu) pluginProvider.getMenus().get("locations");

        source.getOpenInventory().close(); // Close the current inventory
        source.updateInventory(); // Update player inventory state
        source.openInventory(locationsMenu.build()); // Open the locations menu
        source.sendMessage(getMessageRetriever().getMessage(MessagePathIdentifiers.LOCATION_LIST_OPENED)); // Notify
                                                                                                           // player
    }
}
