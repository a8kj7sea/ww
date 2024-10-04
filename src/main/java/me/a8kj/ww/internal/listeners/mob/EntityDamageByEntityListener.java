package me.a8kj.ww.internal.listeners.mob;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.internal.configuration.enums.MessagePathIdentifiers;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.MessagesFile;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.internal.configuration.retrievers.messages.MessageRetriever;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.PlayerUtils;
import me.a8kj.ww.parent.utils.WorldGuardUtils;

/**
 * Listener for handling entity damage events between players and mobs.
 * This listener ensures that damage interactions are restricted
 * to specified regions defined by WorldGuard.
 */
@RequiredArgsConstructor
@Getter
public class EntityDamageByEntityListener implements Listener {

    private final PluginProvider pluginProvider;

    /**
     * Handles the EntityDamageByEntityEvent.
     * Checks if the damage is allowed based on the region and the entities
     * involved.
     *
     * @param event the EntityDamageByEntityEvent triggered
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || event.getDamage() <= 0.0) {
            return;
        }

        Entity victim = event.getEntity();

        // Handle case when the victim is a player
        if (victim instanceof Player player) {
            handlePlayerDamage(event, player);
        }
        // Handle case when the victim is a mob
        else if (victim instanceof Mob || MythicBukkit.inst().getAPIHelper().isMythicMob(victim)) {
            handleMobDamage(event, victim);
        }
    }

    /**
     * Handles damage dealt to a player.
     * Cancels the event if the damager is not allowed to deal damage in the
     * specified region.
     *
     * @param event  the EntityDamageByEntityEvent
     * @param victim the player who is damaged
     */
    private void handlePlayerDamage(EntityDamageByEntityEvent event, Player victim) {
        Entity damager = event.getDamager();

        if (!(damager instanceof Mob || MythicBukkit.inst().getAPIHelper().isMythicMob(damager))) {
            return;
        }

        // Retrieve the mob name from the settings
        String mobNameToCheck = getMobName();

        // Check if the damager is a MythicMob
        ActiveMob activeMob = MythicBukkit.inst().getAPIHelper().getMythicMobInstance(damager);
        String mobName = (activeMob != null) ? activeMob.getType().getInternalName() : damager.getName();

        if (!mobName.equalsIgnoreCase(mobNameToCheck)) {
            return;
        }

        String regionName = getRegionName();
        if (!WorldGuardUtils.isInRegion(victim, regionName)) {
            event.setCancelled(true);
            return;
        }

        // Broadcast damage message
        double health = ((Damageable) damager).getHealth();
        String healthFormatted = String.format("%.1f", health);
        PlayerUtils.broadcastMessage(
                getMessage(MessagePathIdentifiers.GAME_LOGIC_COMBAT_INGAME_ATTACKED_BROADCAST, healthFormatted)
                        .replace("%target_player%", victim.getName()));
    }

    /**
     * Handles damage dealt to a mob.
     * Cancels the event if the attacker is not allowed to damage the mob in the
     * specified region.
     *
     * @param event the EntityDamageByEntityEvent
     * @param mob   the mob that is damaged
     */
    private void handleMobDamage(EntityDamageByEntityEvent event, Entity mob) {
        if (!(event.getDamageSource().getCausingEntity() instanceof Player damager)) {
            return;
        }

        String mobNameToCheck = getMobName();
        String mobName = (mob instanceof Mob) ? mob.getName() : null;

        ActiveMob activeMob = MythicBukkit.inst().getAPIHelper().getMythicMobInstance(mob);
        if (activeMob != null) {
            mobName = activeMob.getType().getInternalName();
        }

        if (mobName == null || !mobName.equalsIgnoreCase(mobNameToCheck)) {
            return;
        }

        String regionName = getRegionName();
        if (!WorldGuardUtils.isInRegion(damager, regionName)) {
            damager.sendMessage(getMessage(MessagePathIdentifiers.GAME_LOGIC_COMBAT_INGAME_NOT_ALLOWED_DAMAGE));
            event.setCancelled(true);
        }
    }

    /**
     * Retrieves the mob name from the settings file.
     *
     * @return the name of the mob as a string
     */
    private String getMobName() {
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        return settingsRetriever.getString(SettingsPathIdentifiers.MOB_NAME);
    }

    /**
     * Retrieves the region name from the settings file.
     *
     * @return the name of the region as a string
     */
    private String getRegionName() {
        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());
        return settingsRetriever.getString(SettingsPathIdentifiers.REGION_NAME);
    }

    /**
     * Retrieves a formatted message from the messages file, replacing placeholders
     * with provided values.
     *
     * @param pathIdentifier the identifier for the message path
     * @param replacements   values to replace placeholders in the message
     * @return the formatted message as a string
     */
    private String getMessage(MessagePathIdentifiers pathIdentifier, String... replacements) {
        MessagesFile messagesFile = (MessagesFile) pluginProvider.getConfigurationManager()
                .getConfiguration("messages");
        MessageRetriever messageRetriever = new MessageRetriever(messagesFile.getYamConfiguration());
        String message = messageRetriever.getMessage(pathIdentifier);
        for (String replacement : replacements) {
            message = message.replace("%mob_hp%", replacement);
        }
        return message;
    }
}
