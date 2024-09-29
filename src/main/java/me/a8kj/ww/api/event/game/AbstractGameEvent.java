package me.a8kj.ww.api.event.game;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.game.EventGame;

@RequiredArgsConstructor
@Getter
public abstract class AbstractGameEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final EventGame eventGame;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public void callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        Bukkit.getLogger()
                .info(String.format("[DEBUG-MODE] %s called successfully for game", getClass().getSimpleName()));
    }
}
