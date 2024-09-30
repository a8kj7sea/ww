package me.a8kj.ww.internal.listeners;

import java.net.http.WebSocket.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.api.event.mob.impl.MobMoveEvent;
import me.a8kj.ww.internal.configuration.enums.SettingsPathIdentifiers;
import me.a8kj.ww.internal.configuration.files.SettingsFile;
import me.a8kj.ww.internal.configuration.retrievers.SettingsRetriever;
import me.a8kj.ww.parent.entity.mob.EventMob;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;
import me.a8kj.ww.parent.utils.WorldGuardUtils;

@RequiredArgsConstructor
@Getter
public class MobMoveListener implements Listener {

    private final PluginProvider pluginProvider;

    @EventHandler
    public void onMobMove(MobMoveEvent event) {
        EventMob eventMob = event.getEventMob();

        if (!eventMob.isValid() || eventMob.getBukkitEntity().isEmpty() || !eventMob.isAlive()) {
            throw new IllegalStateException("EventMob invalid!");
        }

        SettingsFile settingsFile = (SettingsFile) pluginProvider.getConfigurationManager()
                .getConfiguration("settings");
        SettingsRetriever settingsRetriever = new SettingsRetriever(settingsFile.getYamConfiguration());

        Entity entity = eventMob.getBukkitEntity().get();
        boolean inRegion = WorldGuardUtils.isInRegion(entity,
                settingsRetriever.getString(SettingsPathIdentifiers.REGION_NAME));

        if (!inRegion) {

        }

    }
}
