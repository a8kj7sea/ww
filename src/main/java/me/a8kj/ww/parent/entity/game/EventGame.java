package me.a8kj.ww.parent.entity.game;

import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.game.mechanic.GameMechanic;
import me.a8kj.ww.parent.entity.mob.EventMob;

/**
 * The EventGame interface defines the core behavior of a Werewolf game session.
 * It manages the game state, transitions between phases, and the application of
 * game mechanics to ensure smooth gameplay.
 */
public interface EventGame {

    /**
     * Retrieves the current state of the game.
     * 
     * @return the current GameState of the event, indicating if the game is in
     *         progress or idle.
     */
    GameState getGameState();

    /**
     * Retrieves the next phase the game will transition into.
     * 
     * @return the NextPhase that the game will enter next, guiding the flow of the
     *         game.
     */
    NextPhase getNextGamePhase();

    /**
     * Sets the current state of the game.
     * 
     * @param gameState the new GameState to be set, representing the game's current
     *                  condition, such as INGAME or IDLE.
     */
    void setGameState(GameState gameState);

    /**
     * Sets the event mob for this game instance.
     *
     * @param eventMob The EventMob to be set for the game.
     *                 This mob will be associated with the game's mechanics and
     *                 events.
     */
    void setEventMob(EventMob eventMob);

    /**
     * Retrieves the associated EventMob for the game.
     * 
     * @return the EventMob currently linked to this game session.
     */
    EventMob getEventMob();

    /**
     * Sets the next phase that the game will transition into.
     * 
     * @param nextGamePhase the new phase to be set for the game's progression,
     *                      determining what happens next in the game.
     */
    void setNextGamePhase(NextPhase nextGamePhase);

    /**
     * Applies a specified game mechanic to the current game instance,
     * influencing gameplay dynamics.
     * 
     * @param gameMechanic the GameMechanic to be applied, modifying game behavior
     *                     and player interactions.
     */
    void applyGameMechanic(GameMechanic gameMechanic);
}
