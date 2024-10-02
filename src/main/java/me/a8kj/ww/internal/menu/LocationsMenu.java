package me.a8kj.ww.internal.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.files.LocationsFile;
import me.a8kj.ww.parent.entity.menu.Menu;
import me.a8kj.ww.parent.entity.menu.attributes.MenuSettings;
import me.a8kj.ww.parent.entity.menu.enums.MenuSize;
import me.a8kj.ww.parent.utils.ItemStackBuilder;
import me.a8kj.ww.parent.utils.LocationsUtils;

/**
 * Represents a menu that displays scheduled locations.
 *
 * <p>
 * This class extends {@link Menu} and is responsible for defining the
 * contents and settings of the menu. It presents a user-friendly interface
 * for viewing locations.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public class LocationsMenu extends Menu {

    private final LocationsFile locationsFile;

    /**
     * Populates the menu contents based on the available locations.
     *
     * @param contents a map that associates slot numbers with item builders.
     * @throws IllegalStateException if the locations list is empty.
     */
    @Override
    public void defineContents(Map<Integer, ItemStackBuilder> contents) {

        List<Location> locations = locationsFile.getLocations();

        if (locations == null) {
            locations = new ArrayList<>(); // Initialize to an empty list if null
            Bukkit.getLogger().warning("Locations were not loaded; using an empty list.");
        }

        if (locations.isEmpty()) {
            Bukkit.getLogger().warning("Locations list cannot be empty!");
            return;
        }

        final String spacing = "        "; // Spacing for item lore

        int slot = 0;
        for (Location location : locations) {
            contents.put(slot, createLocationItem(slot, location, spacing));
            slot++;
        }
    }

    /**
     * Creates an item builder for a specific location.
     *
     * @param slot     the slot number for the item.
     * @param location the location to display.
     * @param spacing  the spacing string for lore.
     * @return an ItemStackBuilder configured for the location.
     */
    private ItemStackBuilder createLocationItem(int slot, Location location, String spacing) {
        return new ItemStackBuilder(Material.PAPER)
                .setName("&e&lLocation &7#" + (slot + 1))
                .addLoreLine(spacing)
                .addLoreLine("&6" + LocationsUtils.getLocationString(location))
                .addLoreLine(spacing);
    }

    /**
     * Configures the menu settings, such as size and title.
     *
     * @param menuSettings the settings to be applied to the menu.
     */
    @Override
    public void defineSettings(MenuSettings menuSettings) {
        menuSettings.setMenuSize(MenuSize.ROOMY);
        menuSettings.setTitle("&bLocations - List");
    }

    /**
     * Handles click events within the inventory menu.
     *
     * @param event the inventory click event.
     */
    @Override
    public void handleClick(InventoryClickEvent event) {
        if (isInvalidClickEvent(event)) {
            return;
        }

        InventoryView inventoryView = event.getView();
        if (!inventoryView.getTitle().equalsIgnoreCase(getSettings().getTitle())) {
            return;
        }

        event.setCancelled(true); // Cancel the event if it's a valid click
    }

    /**
     * Checks if the click event is valid.
     *
     * @param event the inventory click event.
     * @return true if the event is invalid; otherwise, false.
     */
    private boolean isInvalidClickEvent(InventoryClickEvent event) {
        return event.getClickedInventory() == null ||
                event.getCurrentItem() == null ||
                event.getAction() == null ||
                event.getClick() == null ||
                event.getInventory() == null ||
                event.getView() == null;
    }
}
