package me.a8kj.ww.internal.listeners.mob;

import org.bukkit.event.EventHandler;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.api.event.game.impl.EndGameEvent.EndReason;
import me.a8kj.ww.api.event.mob.AnnounceDropEvent;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.internal.manager.GameManager;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Listener for MythicMob death events.
 * This class handles the logic when a MythicMob dies,
 * specifically checking if the mob matches a configured name
 * and announcing its drops if it does.
 */
@RequiredArgsConstructor
@Getter
public class MythicMobDeathListener {

    private final PluginProvider pluginProvider;

    /**
     * Handles the MythicMobDeathEvent when a MythicMob dies.
     *
     * @param event The event containing information about the dead mob.
     * @throws IllegalStateException if the active mob cannot be fetched.
     */
    @EventHandler
    public void onMythicMobDeath(MythicMobDeathEvent event) {
        ActiveMob activeMob = event.getMob();
        if (activeMob == null) {
            throw new IllegalStateException("Error while fetching active mob instance!");
        }

        String name = activeMob.getType().getInternalName();

        // Retrieve settings for comparison
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        String mobNameToCheck = settingsRetriever.getString(SettingsPathIdentifiers.MOB_NAME);

        // Check if the mob's name matches the configured name
        if (!name.equalsIgnoreCase(mobNameToCheck)) {
            return; // Exit if names do not match
        }

        // HERE LAZM A3ML APPLY END MECHANINC
        GameManager gameManager = pluginProvider.getGameManager();
        gameManager.endGame(EndReason.DEATH);
        // Trigger the announce drop event with the drops from the dead mob
        new AnnounceDropEvent(event.getDrops()).callEvent();
    }
}
