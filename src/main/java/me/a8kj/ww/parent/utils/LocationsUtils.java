package me.a8kj.ww.parent.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import me.a8kj.ww.parent.configuration.Configuration;

@UtilityClass
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

    /**
     * Converts a player's location into a string format.
     * 
     * @param location The player's location.
     * @return A string representing the location in the format: "world,x,y,z".
     */
    public static String getLocationString(@NonNull Location location) {
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return world + "," + x + "," + y + "," + z;
    }

    /**
     * Checks if the given location string already exists in the configuration.
     * 
     * @param config         The YAML configuration file.
     * @param section        The section where the location is stored.
     * @param locationString The string representation of the location.
     * @return true if the location exists, false otherwise.
     */
    public static boolean locationExists(@NonNull YamlConfiguration config, @NonNull String section,
            @NonNull String locationString) {
        return config.contains(section + "." + locationString);
    }

    /**
     * Attempts to save a player's location to the configuration.
     *
     * @param playerLocation The location of the player.
     * @param section        The section in the configuration where the location
     *                       should be stored.
     * @param configuration  The configuration instance to store the location.
     * @return true if the location is saved successfully, false if the location
     *         already exists.
     * @throws RuntimeException if an error occurs while saving the configuration.
     */
    public static boolean savePlayerLocation(@NonNull Location playerLocation, @NonNull String section,
            @NonNull Configuration configuration) {
        String locationString = getLocationString(playerLocation);
        YamlConfiguration yamlConfiguration = configuration.getYamConfiguration();

        if (locationExists(yamlConfiguration, section, locationString)) {
            return false;
        }

        yamlConfiguration.set(section + "." + locationString, true);

        try {
            configuration.save();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the location to configuration", e);
        }
    }

}
