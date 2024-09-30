package me.a8kj.ww.api.event.game.impl;

import javax.annotation.Nonnull;
import lombok.Getter;
import me.a8kj.ww.api.event.game.AbstractGameEvent;
import me.a8kj.ww.parent.entity.game.EventGame;

/**
 * Represents an event that signifies the end of a game.
 * This event is triggered when a game concludes, with a specific reason for
 * ending.
 */
@Getter
public class EndGameEvent extends AbstractGameEvent {

    /** Enumeration of possible reasons for ending the game. */
    public enum EndReason {
        DEATH, COMMAND;
    }

    private final EndReason endReason;

    /**
     * Constructs an EndGameEvent with the specified EventGame and reason for
     * ending.
     *
     * @param eventGame The EventGame associated with this event.
     * @param endReason The reason for the game's end.
     */
    public EndGameEvent(@Nonnull EventGame eventGame, @Nonnull EndReason endReason) {
        super(eventGame);
        this.endReason = endReason;
    }
}
