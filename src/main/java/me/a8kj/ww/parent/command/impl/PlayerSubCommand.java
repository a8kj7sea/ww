package me.a8kj.ww.parent.command.impl;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.command.SubCommand;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Abstract class for player-specific sub-commands.
 * 
 * <p>
 * Implements {@link SubCommand} to define sub-commands executable by players.
 * </p>
 */
@RequiredArgsConstructor
@Getter
public abstract class PlayerSubCommand implements SubCommand<Player> {
    protected final PluginProvider pluginProvider;

}
