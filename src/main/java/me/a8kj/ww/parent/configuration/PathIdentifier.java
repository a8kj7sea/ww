package me.a8kj.ww.parent.configuration;

/**
 * Interface for defining path identifiers used in configuration retrieval.
 * <p>
 * This interface should be implemented by enums or classes that provide a
 * unique path and its associated type for configuration values.
 * </p>
 */
public interface PathIdentifier {

    /**
     * Retrieves the path as a String.
     *
     * @return The path associated with the identifier.
     */
    String getPath();

    /**
     * Retrieves the type associated with the path identifier.
     *
     * @return An Object representing the type of the value at the specified path.
     */
    Object getType();
}
