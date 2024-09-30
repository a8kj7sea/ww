package me.a8kj.ww.parent.command;

import java.util.Map;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permissible;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import me.a8kj.ww.parent.entity.plugin.PluginProvider;

/**
 * Represents an abstract command that can be executed within a Bukkit plugin.
 *
 * <p>
 * This class implements both the {@link CommandExecutor} and
 * {@link TabCompleter} interfaces,
 * allowing it to handle command execution and tab completion. It manages a
 * collection
 * of subcommands that can be executed under this command.
 * </p>
 *
 * @param <Source> The type of the source that can execute this command,
 *                 extending from {@link Permissible}.
 */
@Getter
public abstract class PluginCommand<Source extends Permissible> implements TabCompleter, CommandExecutor {

    /**
     * A map of subcommands that can be executed under this command.
     * The key is the name of the subcommand, and the value is the
     * corresponding {@link SubCommand}.
     */
    protected final Map<String, SubCommand<Source>> subCommands = Maps.newHashMap();

    /** The provider for accessing plugin-related functionality. */
    protected final PluginProvider pluginProvider;

    /**
     * Constructs a new PluginCommand with the given PluginProvider.
     *
     * @param pluginProvider The provider to access plugin functionalities.
     */
    public PluginCommand(@NonNull PluginProvider pluginProvider) {
        this.pluginProvider = pluginProvider;
        defineSubs(subCommands); // Define subcommands during construction
    }

    /**
     * Defines the subcommands available under this command.
     *
     * <p>
     * Subclasses must implement this method to populate the
     * subCommands map with their specific subcommands.
     * </p>
     *
     * @param subCommands The map where subcommands should be defined.
     */
    public abstract void defineSubs(@NonNull Map<String, SubCommand<Source>> subCommands);
}
