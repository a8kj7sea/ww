package me.a8kj.ww.internal.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.WorldGuardUtils;

/**
 * Listener for handling various entity events in the game, including spawning
 * and teleporting.
 */
@RequiredArgsConstructor
@Getter
public class OtherListeners implements Listener {
    private final PluginProvider pluginProvider;

    /**
     * Handles the CreatureSpawnEvent to manage the spawning of creatures.
     *
     * @param event the event triggered when a creature spawns
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        String regionName = getRegionName();
        String mobName = getMobName();

        if (isAllowedToSpawn(entity, mobName)) {
            return; // Allow players and specific MythicMobs to spawn
        }

        if (WorldGuardUtils.isInRegion(entity, regionName)) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles the EntityTeleportEvent to restrict teleportation of specific
     * entities.
     *
     * @param event the event triggered when an entity teleports
     */
    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        Entity entity = event.getEntity();

        if (entity == null || entity.getName() == null || entity.getCustomName() == null) {
            return;
        }

        if (!entity.getName().equalsIgnoreCase(getMobName())) {
            return;
        }

        if (!WorldGuardUtils.isInRegion(event.getTo(), getRegionName())) {
            event.setCancelled(true);
        }
    }

    /**
     * Handles the EntitySpawnEvent to manage the spawning of general entities.
     *
     * @param event the event triggered when an entity spawns
     */
    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {
        Entity entity = event.getEntity();

        if (entity == null || entity.getName() == null || entity.getCustomName() == null) {
            return;
        }

        if (isAllowedToSpawn(entity, getMobName())) {
            return;
        }

        if (WorldGuardUtils.isInRegion(entity, getRegionName())) {
            event.setCancelled(true);
        }
    }

    /**
     * Checks if the entity is allowed to spawn based on its type and name.
     *
     * @param entity  the entity being spawned
     * @param mobName the name of the mob to check against
     * @return true if the entity is allowed to spawn, false otherwise
     */
    private boolean isAllowedToSpawn(Entity entity, String mobName) {
        // Allow players to spawn
        if (entity instanceof Player) {
            return true;
        }

        // Check if the entity is a MythicMob
        ActiveMob activeMob = MythicBukkit.inst().getMobManager().getActiveMob(entity.getUniqueId()).orElse(null);
        if (activeMob != null && activeMob.getType().getInternalName().equalsIgnoreCase(mobName)) {
            return true; // Allow MythicMob to spawn if the name matches
        }

        return false;
    }

    /**
     * Retrieves the mob name from the configuration.
     *
     * @return the name of the mob
     */
    private String getMobName() {
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        return settingsRetriever.getString(SettingsPathIdentifiers.MOB_NAME);
    }

    /**
     * Retrieves the region name from the configuration.
     *
     * @return the name of the region
     */
    private String getRegionName() {
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        return settingsRetriever.getString(SettingsPathIdentifiers.REGION_NAME);
    }
}
