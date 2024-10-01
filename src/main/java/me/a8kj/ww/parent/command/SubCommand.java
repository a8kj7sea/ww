package me.a8kj.ww.parent.command;

import org.bukkit.permissions.Permissible;
import me.a8kj.ww.parent.utils.StringUtils;

/**
 * Represents a subcommand that can be executed within a command framework.
 *
 * <p>
 * This interface defines the structure for subcommands, including methods to
 * retrieve command usage, description, and permission checks. It also
 * provides a method for executing the subcommand with specific arguments
 * and a source that is permissible (e.g., a player or console).
 * </p>
 *
 * @param <Source> The type of the source that can execute this command,
 *                 extending from {@link Permissible}.
 */
public interface SubCommand<Source extends Permissible> {

    /**
     * Gets the permission required to execute this subcommand.
     *
     * @return The permission string, or null if no permission is required.
     */
    default String getPermission() {
        return null;
    }

    /**
     * Gets the usage format of the subcommand.
     *
     * @return A string representing how to use the subcommand.
     */
    String getUsage();

    /**
     * Gets the description of the subcommand.
     *
     * @return A string describing what the subcommand does.
     */
    String getDescription();

    /**
     * Gets formatted information about the subcommand, including
     * usage and description, colored for display.
     *
     * @return A string formatted with color codes for display.
     */
    default String getInfo() {
        return StringUtils.legacyColorize(" &8» &6" + getUsage() + " &8- &7" + getDescription());
    }

    /**
     * Executes the subcommand with the provided arguments and source.
     *
     * @param args   The arguments passed to the subcommand.
     * @param source The source from which the command is executed (e.g., player or
     *               console).
     */
    void execute(String[] args, Source source);
}
