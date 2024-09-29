package me.a8kj.ww.parent.entity.plugin;

import java.util.logging.*;

import org.bukkit.plugin.java.JavaPlugin;

public interface PluginProvider {

    Logger getLogger();

    JavaPlugin getPlugin();

    default void onStart() {
    }

    default void onStop() {
    }
}
