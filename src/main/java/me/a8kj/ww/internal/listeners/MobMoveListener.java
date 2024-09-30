package me.a8kj.ww.internal.listeners;

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
 * Listener for handling mob movement events.
 */
@RequiredArgsConstructor
@Getter
public class MobMoveListener implements Listener {

    private final PluginProvider pluginProvider;

    /**
     * Handles the MobMoveEvent when a mob attempts to move.
     *
     * @param event The MobMoveEvent containing details about the mob movement.
     */
    @EventHandler
    public void onMobMove(MobMoveEvent event) {
        EventMob eventMob = event.getEventMob();

        // Validate that the EventMob and its associated entity are present
        if (eventMob == null || eventMob.getBukkitEntity() == null || !eventMob.getBukkitEntity().isPresent()) {
            throw new IllegalStateException("EventMob or its Bukkit entity is not available.");
        }

        Location currentLocation = event.getToLocation();

        // Retrieve settings to get the region name
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        String regionName = settingsRetriever.getString(SettingsPathIdentifiers.REGION_NAME);

        // Custom logic for finding the safe location inside the region, 7 blocks away
        // from the border
        Location safeLocation = WorldGuardUtils.findSafeLocationInsideRegion(currentLocation, regionName, 7);

        // Check if the mob is outside the region
        if (!WorldGuardUtils.isInRegion(eventMob.getBukkitEntity().get(), regionName)) {
            event.setCancelled(true); // Cancel the event if the mob is outside the region

            // Now teleport the entity to the safe location since the event is canceled
            if (safeLocation != null) {
                // Pass the safe location to the event's teleportation handler
                event.setTeleportationHandler((location) -> {
                    eventMob.getBukkitEntity().get().teleport(safeLocation);
                });
            }
        }
    }
}
