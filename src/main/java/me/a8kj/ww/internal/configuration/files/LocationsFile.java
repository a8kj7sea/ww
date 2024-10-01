package me.a8kj.ww.internal.configuration.files;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.utils.LocationsUtils;

public class LocationsFile extends Configuration {
    public LocationsFile(JavaPlugin plugin) {
        super(plugin, "messages.yml", true);
    }

    public List<Location> getLocations() {
        return LocationsUtils.loadLocations(getYamConfiguration(),
                "spawn-locations");
    }

}
