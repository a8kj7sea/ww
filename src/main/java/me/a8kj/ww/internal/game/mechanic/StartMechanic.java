package me.a8kj.ww.internal.game.mechanic;

import me.a8kj.ww.internal.plugin.WWPluginProvider;
import me.a8kj.ww.parent.entity.game.EventGame;
import me.a8kj.ww.parent.entity.game.enums.GameState;
import me.a8kj.ww.parent.entity.game.enums.NextPhase;
import me.a8kj.ww.parent.entity.game.mechanic.GameMechanic;

public class StartMechanic extends GameMechanic {

    @Override
    public void apply(EventGame game) {

    }

    @Override
    public boolean canApplyGameMechanic(GameState state, NextPhase phase) {
        return state == GameState.IDLE && phase == NextPhase.START;
    }
}
