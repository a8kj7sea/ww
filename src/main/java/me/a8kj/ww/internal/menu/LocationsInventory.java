package me.a8kj.ww.internal.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.a8kj.ww.parent.utils.StringUtils;

public class LocationsInventory {

    /**
     * Creates an inventory and populates it with location items.
     *
     * @param player    the player to whom the inventory will be shown.
     * @param locations a list of locations to display in the inventory.
     */
    public void openLocationsInventory(Player player, List<Location> locations) {

        Inventory inventory = Bukkit.createInventory(null, 4 * 9, StringUtils.legacyColorize("&eLocations - List"));

        // Populate the inventory with location items
        for (int i = 0; i < locations.size() && i < inventory.getSize(); i++) {
            Location location = locations.get(i);
            ItemStack item = createLocationItem(location, i + 1);
            inventory.setItem(i, item);
        }

        // Open the inventory for the player
        player.openInventory(inventory);
    }

    /**
     * Creates an ItemStack representing a location.
     *
     * @param location the location to represent.
     * @param index    the index number of the location.
     * @return the ItemStack for the location.
     */
    private ItemStack createLocationItem(Location location, int index) {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // Set a colorized display name and lore
            meta.setDisplayName(StringUtils.legacyColorize("&e&lLocation &7#" + index));
            List<String> lore = new ArrayList<>();
            lore.add(StringUtils.legacyColorize("&bWorld: &f" + location.getWorld().getName()));
            lore.add(StringUtils.legacyColorize("&bX: &f" + location.getX()));
            lore.add(StringUtils.legacyColorize("&bY: &f" + location.getY()));
            lore.add(StringUtils.legacyColorize("&bZ: &f" + location.getZ()));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }
}
