package me.a8kj.ww.api.event.game.impl;

import me.a8kj.ww.api.event.game.AbstractGameEvent;
import me.a8kj.ww.parent.entity.game.EventGame;

public class StartGameEvent extends AbstractGameEvent {

    public StartGameEvent(EventGame eventGame) {
        super(eventGame);
    }
}
