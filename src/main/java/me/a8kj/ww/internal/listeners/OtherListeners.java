package me.a8kj.ww.internal.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import io.lumine.mythic.bukkit.MythicBukkit;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.WorldGuardUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Listener for handling entity spawn events.
 */
@RequiredArgsConstructor
@Getter
public class OtherListeners implements Listener {
    private final PluginProvider pluginProvider;
    private final MythicBukkit mythicBukkit = MythicBukkit.inst();

    /**
     * Handles the entity spawning events to restrict the spawning of entities.
     *
     * @param event the event triggered when an entity spawns
     */
    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        // Check if the entity is a player and ignore if it is
        if (entity instanceof Player) {
            return;
        }

        // Check if the entity is within the specified WorldGuard region
        if (!WorldGuardUtils.isInRegion(entity, getRegionName())) {
            return; // Allow spawning outside the region
        }

        // Allow specific entities to spawn
        if (entity.getType() == EntityType.WITHER_SKELETON || entity.getType() == EntityType.WOLF) {
            return; // Allow these mobs to spawn
        }

        // Cancel spawning for all other entities
        event.setCancelled(true);
    }

    /**
     * Retrieves the region name from the configuration.
     *
     * @return the name of the region
     */
    private String getRegionName() {
        return getSetting(SettingsPathIdentifiers.REGION_NAME);
    }

    /**
     * Retrieves a string setting from the configuration based on the given path
     * identifier.
     *
     * @param pathIdentifier the path identifier for the setting
     * @return the string value of the setting
     */
    private String getSetting(SettingsPathIdentifiers pathIdentifier) {
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        return settingsRetriever.getString(pathIdentifier);
    }
}
