package me.a8kj.ww.internal.listeners.mob;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
 */
@RequiredArgsConstructor
@Getter
public class EntityDamageByEntityListener implements Listener {

    private final PluginProvider pluginProvider;

    /**
     * Handles the EntityDamageByEntityEvent to manage damage interactions.
     *
     * @param event the event triggered when an entity is damaged by another entity
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (isInvalidEvent(event))
            return;

        String mobName = getMobName();
        String regionName = getRegionName();

        if (event.getEntity() instanceof Player victim) {
            handlePlayerDamage(event, victim, mobName, regionName);
        } else if (event.getEntity() instanceof ActiveMob || event.getEntity() instanceof Mob) {
            handleMobDamage(event, event.getEntity(), mobName, regionName);
        }
    }

    /**
     * Checks if the event is valid for processing.
     *
     * @param event the event to check
     * @return true if the event is invalid, false otherwise
     */
    private boolean isInvalidEvent(EntityDamageByEntityEvent event) {
        return event.getDamager() == null || event.getEntity() == null || event.getDamageSource() == null
                || event.isCancelled() || event.getDamage() <= 0.0;
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

    /**
     * Handles damage dealt to a player by a mob.
     *
     * @param event      the damage event
     * @param victim     the player being damaged
     * @param mobName    the name of the mob
     * @param regionName the name of the region
     */
    private void handlePlayerDamage(EntityDamageByEntityEvent event, Player victim, String mobName, String regionName) {
        Entity damager = event.getDamageSource().getCausingEntity();

        if (!(damager instanceof ActiveMob || damager instanceof Mob) || damager.getName() == null)
            return;
        if (!damager.getName().equalsIgnoreCase(mobName))
            return;

        if (!WorldGuardUtils.isInRegion(victim, regionName)) {
            event.setCancelled(true);
        }

        float health = (float) ((Damageable) damager).getHealth();
        String healthFormatted = String.format("%.1f", health);
        PlayerUtils.broadcastMessage(
                getMessage(MessagePathIdentifiers.GAME_LOGIC_COMBAT_INGAME_ATTACKED_BROADCAST, healthFormatted));
    }

    /**
     * Handles damage dealt to a mob by a player.
     *
     * @param event      the damage event
     * @param entity     the mob being damaged
     * @param mobName    the name of the mob
     * @param regionName the name of the region
     */
    private void handleMobDamage(EntityDamageByEntityEvent event, Entity entity, String mobName,
            String regionName) {
        if (entity.getName() == null || !entity.getName().equalsIgnoreCase(mobName))
            return;

        if (event.getDamageSource().getCausingEntity() instanceof Player damager) {
            if (!WorldGuardUtils.isInRegion(damager, regionName)) {
                damager.sendMessage(getMessage(MessagePathIdentifiers.GAME_LOGIC_COMBAT_INGAME_NOT_ALLOWED_DAMAGE));
                event.setCancelled(true);
            }
        }
    }

    /**
     * Retrieves a message from the configuration, replacing placeholders as needed.
     *
     * @param pathIdentifier the message path identifier
     * @param replacements   the replacements for placeholders in the message
     * @return the formatted message
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
