package me.a8kj.ww.parent.command.impl;

import org.bukkit.entity.Player;

import lombok.NonNull;
import me.a8kj.ww.parent.command.PluginCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Abstract class representing a command specifically for players.
 * 
 * <p>This class extends the {@link PluginCommand} class, providing
 * a foundation for player-specific commands in the plugin.</p>
 * 
 * <p>Subclasses must implement the command logic for specific player commands.</p>
 */
public abstract class PlayerCommand extends PluginCommand<Player> {
    
    /**
     * Constructs a PlayerCommand with the specified plugin provider.
     *
     * @param pluginProvider The PluginProvider instance used for accessing plugin functionalities.
     * @throws NullPointerException if pluginProvider is null.
     */
    public PlayerCommand(@NonNull PluginProvider pluginProvider) {
        super(pluginProvider);
    }
}
