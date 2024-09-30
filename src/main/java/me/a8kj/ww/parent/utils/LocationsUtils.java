package me.a8kj.ww.parent.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

import lombok.NonNull;

public class LocationsUtils {

    /**
     * Loads a list of locations from the specified section of a YamlConfiguration.
     * 
     * Each location is expected to be stored as a key in the format "world,x,y,z".
     * The method parses these keys, extracts the world and coordinates, and
     * creates Location objects that are added to the returned list.
     * 
     * @param config  the YamlConfiguration containing the locations
     * @param section the section within the configuration to load locations from
     * @return a List of Location objects parsed from the configuration section
     */
    public static List<Location> loadLocations(YamlConfiguration config, String section) {
        List<Location> locations = Lists.newArrayList();

        if (config.contains(section)) {
            for (String key : config.getConfigurationSection(section).getKeys(false)) {
                String locationString = key;
                String[] parts = locationString.split(",");
                if (parts.length == 4) {
                    String world = parts[0];
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    int z = Integer.parseInt(parts[3]);
                    locations.add(new Location(Bukkit.getWorld(world), x, y, z));
                }
            }
        }
        return locations;
    }

    /**
     * Converts the coordinates of the given location into a formatted string.
     *
     * @param location the location object containing the coordinates
     * @return a string representation of the coordinates in the format "x, y, z"
     *         with each value rounded to two decimal places
     * @throws NullPointerException if the location parameter is null
     */
    public static String getLocationCordsAsString(@NonNull Location location) {
        return String.format("%.2f , %.2f , %.2f", location.getX(), location.getY(), location.getZ());
    }

}
