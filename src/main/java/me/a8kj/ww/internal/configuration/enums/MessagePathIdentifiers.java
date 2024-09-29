package me.a8kj.ww.internal.configuration.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.a8kj.ww.parent.configuration.PathIdentifier;

@RequiredArgsConstructor
@Getter
public enum MessagePathIdentifiers implements PathIdentifier {
        // System messages
        SYSTEM_NO_PERM("messages.system.no-perm", ""),
        SYSTEM_ONLY_PLAYER("messages.system.only-player", ""),
        SYSTEM_UNKNOWN_COMMAND("messages.system.unknown-command", ""),

        // Location messages
        LOCATION_EXIST("messages.location.exist-location", ""),
        LOCATION_ADDED("messages.location.added-location", ""),
        LOCATION_LIST_EMPTY("messages.location.list-empty", ""),
        LOCATION_LIST_OPENED("messages.location.list-location", ""),
        LOCATION_SAVING_ERROR("messages.location.saving-error", ""),

        // Command messages
        COMMAND_STOP_GAME_FAILURE("messages.command.stop-game.failure", ""),
        COMMAND_STOP_GAME_SUCCESS("messages.command.stop-game.success", ""),
        COMMAND_RELOAD_SUCCESS("messages.command.reload.success", ""),
        COMMAND_SCHEDULE_SUCCESS("messages.command.schedule.success", ""),
        COMMAND_SCHEDULE_DUPLICATE_EVENT("messages.command.schedule.duplicate_event", ""),
        COMMAND_SCHEDULE_INVALID_TIME("messages.command.schedule.invalid_time", ""),
        COMMAND_SCHEDULE_INVALID_DAY("messages.command.schedule.invalid_day", ""),
        COMMAND_SCHEDULE_WRONG_USAGE("messages.command.schedule.wrong_usage", ""),
        COMMAND_SCHEDULE_LIST_EMPTY("messages.command.schedule.list-empty", ""),
        COMMAND_SCHEDULE_LIST_SUCCESS("messages.command.schedule.list-success", ""),
        COMMAND_START_GAME_FAILURE("messages.command.start-game.failure", ""),
        COMMAND_START_GAME_SUCCESS("messages.command.start-game.success", ""),

        // Game logic messages
        GAME_LOGIC_START_GAME_MOB_SUMMONED("messages.game-logic.start-game.mob-summoned", ""),
        GAME_LOGIC_START_GAME_ANNOUNCE_PLAYERS("messages.game-logic.start-game.announce-players", Arrays.asList("")),
        GAME_LOGIC_START_GAME_ANNOUNCE_ADMIN("messages.game-logic.start-game.announce-admin", ""),
        GAME_LOGIC_COMBAT_INGAME_ATTACKED_BROADCAST("messages.game-logic.combat-ingame.attacked-broadcast", ""),
        GAME_LOGIC_COMBAT_INGAME_NOT_ALLOWED_DAMAGE("messages.game-logic.combat-ingame.not-allowed-damage", ""),
        GAME_LOGIC_END_GAME_COMMAND_ANNOUNCE_MESSAGE("messages.game-logic.end-game.reason.command.announce-message",
                        Arrays.asList("")),
        GAME_LOGIC_END_GAME_DEATH_ANNOUNCE_MESSAGE("messages.game-logic.end-game.reason.death.announce-message",
                        Arrays.asList("")),
        GAME_LOGIC_END_GAME_DEATH_ANNOUNCE_ADMIN("messages.game-logic.end-game.reason.death.announce-admin", ""),
        GAME_LOGIC_END_GAME_DEATH_DROPS("messages.game-logic.end-game.reason.death.drops", Arrays.asList(""));

        private final String path; // The configuration path for the event
        private final Object type; // The expected type of the value at the path

}
