package me.a8kj.ww.parent.configuration;

import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import lombok.NonNull;

/**
 * Interface for handling retrieval of configuration values.
 *
 * @param <E> The enum type used for path identifiers, which must implement
 *            PathIdentifier.
 */
@SuppressWarnings("unchecked")
public interface ConfigValueRetriever<E extends Enum<E> & PathIdentifier> {

    /**
     * Retrieves the YAML configuration instance.
     *
     * @return The YamlConfiguration instance used for fetching values.
     */
    YamlConfiguration getYamlConfiguration();

    /**
     * Validates if the specified path exists in the configuration.
     *
     * @param path The path to validate.
     * @throws IllegalStateException if the path does not exist.
     */
    default void validatePathExists(String path) {
        if (!getYamlConfiguration().contains(path)) {
            throw new IllegalStateException(
                    "Cannot find path identifier. Please delete the config file and reload the server!");
        }
    }

    /**
     * Validates the type of the path identifier against the expected type.
     *
     * @param pathId The identifier for the path.
     * @param type   The expected type class.
     * @throws IllegalArgumentException if the type does not match.
     */
    default void validateType(@NonNull E pathId, Class<?> type) {
        if (!type.isInstance(pathId.getType())) {
            throw new IllegalArgumentException("Please choose a valid argument type!");
        }
    }

    /**
     * Retrieves the value from the configuration at the specified path.
     *
     * This method is generic and allows for fetching values of various types,
     * based on the specified enum path identifier and the expected type class.
     *
     * @param pathId The identifier for the path.
     * @param type   The class of the expected type.
     * @param <T>    The type of the value to be retrieved.
     * @return The value at the specified path cast to the expected type.
     */
    default <T> T getValue(@NonNull E pathId, Class<T> type) {
        validateType(pathId, type); // Validate the type of pathId
        String path = pathId.getPath(); // Get the path from the identifier
        validatePathExists(path); // Ensure the path exists in the configuration
        return type.cast(getYamlConfiguration().get(path)); // Retrieve and cast the value
    }

    /**
     * Retrieves a String value from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The String value at the specified path.
     */
    default String getString(@NonNull E pathId) {
        return getValue(pathId, String.class); // Delegate to getValue for String type
    }

    /**
     * Retrieves a Boolean value from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The Boolean value at the specified path.
     */
    default boolean getBoolean(@NonNull E pathId) {
        return getValue(pathId, Boolean.class); // Delegate to getValue for Boolean type
    }

    /**
     * Retrieves a List of String values from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The List of String values at the specified path.
     */
    default List<String> getStringList(@NonNull E pathId) {
        return getValue(pathId, List.class); // Delegate to getValue for List<String> type
    }

    /**
     * Retrieves an Integer value from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The Integer value at the specified path.
     */
    default Integer getInteger(@NonNull E pathId) {
        return getValue(pathId, Integer.class); // Delegate to getValue for Integer type
    }

    /**
     * Retrieves a Double value from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The Double value at the specified path.
     */
    default Double getDouble(@NonNull E pathId) {
        return getValue(pathId, Double.class); // Delegate to getValue for Double type
    }

    /**
     * Retrieves a List of Integer values from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The List of Integer values at the specified path.
     */
    default List<Integer> getIntegerList(@NonNull E pathId) {
        return getValue(pathId, (Class<List<Integer>>) (Class<?>) List.class); // Delegate to getValue for List<Integer>
                                                                               // type
    }

    /**
     * Retrieves a List of Double values from the configuration.
     *
     * @param pathId The identifier for the path.
     * @return The List of Double values at the specified path.
     */
    default List<Double> getDoubleList(@NonNull E pathId) {
        return getValue(pathId, (Class<List<Double>>) (Class<?>) List.class); // Delegate to getValue for List<Double>
                                                                              // type
    }
}
