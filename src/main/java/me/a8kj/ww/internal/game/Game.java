package me.a8kj.ww.internal.game;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import lombok.SneakyThrows;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.game.mechanic.GameMechanic;
import me.a8kj.ww.parent.entity.mob.EventMob;

/**
 * Represents a game that can handle various game mechanics,
 * mob events, and maintains game state and phase information.
 */
public class Game implements EventGame {

    private GameState gameState;
    private NextPhase nextGamePhase;
    private EventMob eventMob;

    // Logger to print debug and information logs.
    private final Logger logger = Bukkit.getLogger();

    /**
     * Applies a specified game mechanic to the game, provided that
     * the current game state and next phase allow it.
     *
     * @param gameMechanic The game mechanic to apply.
     * @throws IllegalArgumentException If the game mechanic cannot be applied under current conditions.
     */
    @Override
    @SneakyThrows
    public void applyGameMechanic(GameMechanic gameMechanic) {
        if (!gameMechanic.canApplyGameMechanic(gameState, nextGamePhase)) {
            throw new IllegalArgumentException("Cannot apply game mechanic with these conditions!");
        }

        gameMechanic.apply(this);
        logger.info("[DEBUG-MODE] The mechanic operation is complete, and the " +
                gameMechanic.getClass().getSimpleName() + " has been successfully applied within the game.");
    }

    /**
     * Gets the current state of the game.
     *
     * @return The current {@link GameState}.
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Gets the next phase of the game.
     *
     * @return The current {@link NextPhase}.
     */
    @Override
    public NextPhase getNextGamePhase() {
        return nextGamePhase;
    }

    /**
     * Gets the event mob associated with the game.
     *
     * @return The current {@link EventMob}.
     */
    @Override
    public EventMob getEventMob() {
        return eventMob;
    }

    /**
     * Sets the current state of the game.
     *
     * @param gameState The new {@link GameState} to set.
     */
    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Sets the next phase of the game.
     *
     * @param nextGamePhase The new {@link NextPhase} to set.
     */
    @Override
    public void setNextGamePhase(NextPhase nextGamePhase) {
        this.nextGamePhase = nextGamePhase;
    }

    /**
     * Sets the event mob for the game.
     *
     * @param eventMob The new {@link EventMob} to set.
     */
    @Override
    public void setEventMob(EventMob eventMob) {
        this.eventMob = eventMob;
    }
}
