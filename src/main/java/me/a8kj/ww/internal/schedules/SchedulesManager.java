package me.a8kj.ww.internal.schedules;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.NonNull;
import me.a8kj.ww.parent.configuration.Configuration;
import me.a8kj.ww.parent.entity.schedule.manager.EventHandler;
import me.a8kj.ww.parent.entity.schedule.manager.HandlerRegistry;
import me.a8kj.ww.parent.entity.schedule.manager.StorageManager;

@Getter
public class SchedulesManager implements StorageManager {

    private final Map<String, EventHandler> handlers = Maps.newHashMap();

    private final Configuration configuration;
    private final HandlerRegistry registry;

    public SchedulesManager(@NonNull Configuration configuration) {
        this.configuration = configuration;
        this.registry = new HandlerRegistry(handlers);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void handleProcessByName(String name) {
        if (!registry.contains(name))
            throw new IllegalArgumentException("Couldn't find you option!");

        registry.get(name).handle();
    }

}
