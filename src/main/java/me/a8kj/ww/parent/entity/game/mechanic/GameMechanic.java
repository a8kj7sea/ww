package me.a8kj.ww.parent.entity.game.mechanic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Abstract class representing a game mechanic in the Werewolf game.
 * Mechanics encapsulate specific actions or processes that can influence
 * the game's state and behavior.
 */

@RequiredArgsConstructor
@Getter
public abstract class GameMechanic {

    private final PluginProvider pluginProvider;

    /**
     * Applies the mechanic to the specified game instance.
     * This method may alter the game's state and overall behavior
     * based on the specific mechanic's implementation.
     * 
     * @param game the current EventGame instance to which the mechanic is applied
     */
    public abstract void apply(EventGame game);

    /**
     * Checks if the mechanic can be applied in the current context.
     * This decision is based on the current game state and the next
     * phase that the game is transitioning into.
     * 
     * @param state the current GameState of the game
     * @param phase the NextPhase that the game will enter next
     * @return true if the mechanic can be applied; false otherwise
     */
    public abstract boolean canApplyGameMechanic(GameState state, NextPhase phase);

}
