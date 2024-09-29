package me.a8kj.ww.internal.configuration.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.PathIdentifier;

/**
 * Enum for defining path identifiers related to settings in the configuration.
 * Implements the PathIdentifier interface.
 */
@RequiredArgsConstructor
@Getter
public enum SettingsPathIdentifiers implements PathIdentifier {

    // Placeholder API settings
    PLACEHOLDER_API_SUPPORT("placholder-api.support", Boolean.class),

    // Region settings
    REGION_NAME("region-settings.region-name", String.class),

    // Mob settings
    MOB_NAME("mob-settings.name", String.class);

    private final String path; // The configuration path for the event
    private final Object type; // The expected type of the value at the path
}
