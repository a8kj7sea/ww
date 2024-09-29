package me.a8kj.ww.api.event.mob;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.entity.mob.EventMob;

@RequiredArgsConstructor
@Getter
public abstract class MobEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final EventMob eventMob;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public void callEvent() {
        Bukkit.getPluginManager().callEvent(this);
        Bukkit.getLogger().info(String.format("[DEBUG-MODE] %s registered Successfully!", getClass().getSimpleName()));
    }

}
