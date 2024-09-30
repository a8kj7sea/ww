package me.a8kj.ww.internal.game;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import lombok.SneakyThrows;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.game.mechanic.GameMechanic;
import me.a8kj.ww.parent.entity.mob.EventMob;

public class Game implements EventGame {

    private GameState gameState;
    private NextPhase nextGamePhase;

    private EventMob eventMob;

    private final Logger logger = Bukkit.getLogger();

    @Override
    @SneakyThrows
    public void applyGameMechanic(GameMechanic gameMechanic) {
        if (!gameMechanic.canApplyGameMechanic(gameState, nextGamePhase)) {
            throw new IllegalArgumentException("Cannot apply game mechaninc with these conditions !");
        }

        gameMechanic.apply(this);
        logger.info(
                "[DEBUG-MODE] The mechanic operation is complete, and the %s has been successfully applied within the game."
                        .replace("%s", gameMechanic.getClass().getSimpleName()));
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public NextPhase getNextGamePhase() {
        return nextGamePhase;
    }

    @Override
    public EventMob getEventMob() {
        return eventMob;
    }

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void setNextGamePhase(NextPhase nextGamePhase) {
        this.nextGamePhase = nextGamePhase;
    }

    @Override
    public void setEventMob(EventMob eventMob) {
        this.eventMob = eventMob;
    }

}
