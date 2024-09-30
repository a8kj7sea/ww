package me.a8kj.ww.parent.utils;

import org.bukkit.Location;
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
}
