package me.a8kj.ww.internal.listeners.mob.optional;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.api.event.mob.impl.MobMoveEvent;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.WorldGuardUtils;

/**
 * Listener for handling mob movement events. This listener ensures that mobs
 * stay within a specified WorldGuard region. If a mob attempts to move outside
 * of
 * the defined region, it will be teleported to a safe location inside the
 * region.
 */
@RequiredArgsConstructor
@Getter
public class MobMoveListener implements Listener {

    private final PluginProvider pluginProvider;

    /**
     * Handles the {@link MobMoveEvent} when a mob attempts to move.
     * Ensures that the mob stays within the configured region.
     *
     * @param event The MobMoveEvent containing details about the mob movement.
     */
    @EventHandler
    public void onMobMove(MobMoveEvent event) {
        EventMob eventMob = event.getEventMob();

        // Ensure the EventMob and its associated Bukkit entity are valid
        if (eventMob == null || eventMob.getBukkitEntity() == null || !eventMob.getBukkitEntity().isPresent()) {
            throw new IllegalStateException("EventMob or its Bukkit entity is not available.");
        }

        Location currentLocation = event.getToLocation();

        // Retrieve the region name from the plugin's settings
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        String regionName = settingsRetriever.getString(SettingsPathIdentifiers.REGION_NAME);

        // Check if the mob is moving outside of the specified region
        if (!WorldGuardUtils.isInRegion(eventMob.getBukkitEntity().get(), regionName)) {
            event.setCancelled(true); // Cancel the move event if the mob is outside the region

            // Find a safe location inside the region, 5 blocks away from the region's
            // border
            Location safeLocation = WorldGuardUtils.findSafeLocationInsideRegion(currentLocation, regionName, 5);
            if (safeLocation != null) {
                event.setTeleportationHandler((location) -> eventMob.getBukkitEntity().get().teleport(safeLocation));
            }
        } else {
            // Ensure the mob stays within a defined distance from the region border
            Location borderSafeLocation = WorldGuardUtils.findSafeLocationInsideRegion(currentLocation, regionName, 5);
            if (borderSafeLocation != null) {
                // Check if currentLocation is within the safe distance from the borders
                if (Math.abs(currentLocation.getX() - borderSafeLocation.getX()) < 5 ||
                        Math.abs(currentLocation.getZ() - borderSafeLocation.getZ()) < 5) {
                    // If it's too close to the border, teleport it back
                    event.setTeleportationHandler(
                            (location) -> eventMob.getBukkitEntity().get().teleport(borderSafeLocation));
                }
            }
        }
    }
}
