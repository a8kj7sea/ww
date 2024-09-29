package me.a8kj.ww.parent.entity.schedule.manager;

import java.util.Map;

import lombok.Getter;
import lombok.NonNull;

/**
 * The HandlerRegistry class manages the registration of scheduled event
 * handlers.
 */
public class HandlerRegistry {

    @Getter
    final Map<String, EventHandler> handlers;

    /**
     * Constructor to initialize the registry with the provided handlers.
     *
     * @param handlers a map of scheduled event handlers indexed by their names
     */
    public HandlerRegistry(@NonNull Map<String, EventHandler> handlers) {
        this.handlers = handlers;
    }

}
