package me.a8kj.ww.internal.game;

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

    @Override
    @SneakyThrows
    public void applyGameMechanic(GameMechanic gameMechanic) {
        if (!gameMechanic.canApplyGameMechanic(gameState, nextGamePhase)) {
            throw new IllegalArgumentException("Cannot apply game mechaninc right now !");
        }

        gameMechanic.apply(this);

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

}
