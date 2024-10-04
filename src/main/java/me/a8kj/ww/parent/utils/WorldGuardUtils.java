package me.a8kj.ww.parent.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WorldGuardUtils {

    public static boolean isInRegion(Entity entity, String regionName) {
        Location location = entity.getLocation();
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(BukkitAdapter.adapt(entity.getWorld()));

        if (regionManager != null) {
            BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
            ApplicableRegionSet applicableRegions = regionManager.getApplicableRegions(vector);

            for (ProtectedRegion region : applicableRegions) {
                if (region.getId().equalsIgnoreCase(regionName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInRegion(Location location, String regionName) {
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(BukkitAdapter.adapt(location.getWorld()));

        if (regionManager != null) {
            BlockVector3 vector = BlockVector3.at(location.getX(), location.getY(), location.getZ());
            ApplicableRegionSet applicableRegions = regionManager.getApplicableRegions(vector);

            for (ProtectedRegion region : applicableRegions) {
                if (region.getId().equalsIgnoreCase(regionName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("removal")
    public static Location findSafeLocationInsideRegion(Location currentLocation, String regionName, int distance) {
        World world = currentLocation.getWorld();
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(BukkitAdapter.adapt(world));

        if (regionManager == null) {
            return null; // RegionManager not found
        }

        BlockVector3 currentVector = BlockVector3.at(currentLocation.getX(), currentLocation.getY(),
                currentLocation.getZ());
        ApplicableRegionSet applicableRegions = regionManager.getApplicableRegions(currentVector);

        // Find the specific region by name
        ProtectedRegion region = null;
        for (ProtectedRegion applicableRegion : applicableRegions) {
            if (applicableRegion.getId().equalsIgnoreCase(regionName)) {
                region = applicableRegion;
                break;
            }
        }

        if (region == null) {
            return null; // Region not found
        }

        // Get the region boundaries (min and max points)
        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();

        // Clone the current location to adjust
        Location safeLocation = currentLocation.clone();

        // Adjust X coordinate to stay at least 'distance' blocks away from the min/max
        // boundaries
        if (safeLocation.getX() - min.getX() <= distance) {
            safeLocation.setX(min.getX() + distance);
        } else if (max.getX() - safeLocation.getX() <= distance) {
            safeLocation.setX(max.getX() - distance);
        }

        // Adjust Z coordinate to stay at least 'distance' blocks away from the min/max
        // boundaries
        if (safeLocation.getZ() - min.getZ() <= distance) {
            safeLocation.setZ(min.getZ() + distance);
        } else if (max.getZ() - safeLocation.getZ() <= distance) {
            safeLocation.setZ(max.getZ() - distance);
        }

        // Set the Y-coordinate to the highest block at the adjusted (X, Z) position
        safeLocation.setY(safeLocation.getWorld().getHighestBlockYAt(safeLocation));

        return safeLocation;
    }

}
