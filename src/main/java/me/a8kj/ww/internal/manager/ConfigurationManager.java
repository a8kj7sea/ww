package me.a8kj.ww.internal.manager;

import java.util.Map;
import java.util.Collection;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.Configuration;

/**
 * Manages a collection of configurations.
 *
 * <p>
 * This class allows for registering, unregistering, and retrieving
 * configuration instances by their names. Configurations are stored in a
 * case-insensitive manner, ensuring that duplicate names with different
 * cases are not registered.
 * </p>
 *
 * <p>
 * Additional functionalities include checking if a configuration
 * is registered and clearing all registered configurations.
 * </p>
 */

@RequiredArgsConstructor
@Getter
public class ConfigurationManager {

    private final Map<String, Configuration> configurations;

    /**
     * Registers a configuration with the given name.
     *
     * @param name          The name of the configuration.
     * @param configuration The configuration instance to register.
     */
    public void register(@NonNull String name, @NonNull Configuration configuration) {
        configurations.putIfAbsent(name.toLowerCase(), configuration);
    }

    /**
     * Unregisters a configuration by its name.
     *
     * @param name The name of the configuration to unregister.
     */
    public void unregister(@NonNull String name) {
        configurations.remove(name.toLowerCase());
    }

    /**
     * Retrieves a configuration by its name.
     *
     * @param name The name of the configuration to retrieve.
     * @return The configuration instance, or null if not found.
     */
    public Configuration getConfiguration(@NonNull String name) {
        return configurations.get(name.toLowerCase());
    }

    /**
     * Retrieves all registered configurations.
     *
     * @return A collection of all registered configurations.
     */
    public Collection<Configuration> getAllConfigurations() {
        return configurations.values();
    }

    /**
     * Checks if a configuration is registered by its name.
     *
     * @param name The name of the configuration to check.
     * @return True if the configuration is registered, false otherwise.
     */
    public boolean isRegistered(@NonNull String name) {
        return configurations.containsKey(name.toLowerCase());
    }

    /**
     * Clears all registered configurations.
     */
    public void clear() {
        configurations.clear();
    }
}
