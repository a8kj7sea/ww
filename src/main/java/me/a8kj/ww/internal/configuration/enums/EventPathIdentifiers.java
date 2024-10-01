package me.a8kj.ww.internal.configuration.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.PathIdentifier;

/**
 * Enum for defining path identifiers related to events in the configuration.
 * Implements the PathIdentifier interface.
 */
@RequiredArgsConstructor
@Getter
public enum EventPathIdentifiers implements PathIdentifier {

    // Mob Events
    SPAWN_MOB_EVENT("events.mob.spawn-mob-event.api-usage", Boolean.class),
    MOB_MOVE_EVENT("events.mob.mob-move-event.api-usage", Boolean.class),
    ANNOUNCE_DROP_EVENT("events.mob.announce-drop-event.api-usage", Boolean.class),

    // Game Events
    END_GAME_EVENT("events.game.end-game-event.api-usage", Boolean.class),
    START_GAME_EVENT("events.game.start-game-event.api-usage", Boolean.class);

    private final String path; // The configuration path for the event
    private final Object type; // The expected type of the value at the path
}
