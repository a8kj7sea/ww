package me.a8kj.ww.parent.entity.schedule.manager;

import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

/**
 * The {@code HandlerRegistry} class manages the registration
 * and storage of scheduled event handlers.
 */
public class HandlerRegistry {

    /** A map of scheduled event handlers indexed by their names. */
    @Getter
    private final Map<String, EventHandler> handlers;

    /**
     * Constructor to initialize the registry with by pass a set of handlers.
     */
    public HandlerRegistry(@NonNull Map<String, EventHandler> handlers) {
        this.handlers = handlers;
    }

    /**
     * Registers a new event handler with the specified name.
     *
     * @param name    the name of the event handler
     * @param handler the event handler to register
     * @throws IllegalArgumentException if the name or handler is null
     */
    public void register(@NonNull String name, @NonNull EventHandler handler) {
        String lowerCaseName = name.toLowerCase();
        if (handlers.containsKey(lowerCaseName)) {
            throw new IllegalArgumentException("Handler with this name already registered: " + name);
        }
        handlers.put(lowerCaseName, handler);
    }

    /**
     * Unregisters the event handler with the specified name.
     *
     * @param name the name of the event handler to unregister
     * @throws IllegalArgumentException if the name is null or if no handler is
     *                                  found
     */
    public void unregister(@NonNull String name) {
        String lowerCaseName = name.toLowerCase();
        if (!handlers.containsKey(lowerCaseName)) {
            throw new IllegalArgumentException("No handler found with this name: " + name);
        }
        handlers.remove(lowerCaseName);
    }

    /**
     * Retrieves the event handler associated with the specified name.
     *
     * @param name the name of the event handler to retrieve
     * @return the event handler associated with the specified name, or null if not
     *         found
     * @throws IllegalArgumentException if the name is null
     */
    public EventHandler get(@NonNull String name) {
        String lowerCaseName = name.toLowerCase();
        if (!handlers.containsKey(lowerCaseName)) {
            throw new IllegalArgumentException("No handler found with this name: " + name);
        }
        return handlers.get(lowerCaseName);
    }

    /**
     * Checks if a handler is registered with the specified name.
     *
     * @param name the name of the event handler to check
     * @return true if a handler is registered with the specified name, false
     *         otherwise
     * @throws IllegalArgumentException if the name is null
     */
    public boolean contains(@NonNull String name) {
        String lowerCaseName = name.toLowerCase();
        return handlers.containsKey(lowerCaseName);
    }

    /**
     * Clears all registered event handlers from the registry.
     */
    public void clear() {
        handlers.clear();
    }
}
