package me.a8kj.ww.api.event.game.impl;

import javax.annotation.Nonnull;
import me.a8kj.ww.api.event.game.AbstractGameEvent;
import me.a8kj.ww.parent.entity.game.EventGame;

/**
 * Represents an event that signifies the start of a game.
 * This event is triggered when a game begins.
 */
public class StartGameEvent extends AbstractGameEvent {

    /**
     * Constructs a StartGameEvent with the specified EventGame.
     *
     * @param eventGame The EventGame associated with this event.
     */
    public StartGameEvent(@Nonnull EventGame eventGame) {
        super(eventGame);
    }
}
