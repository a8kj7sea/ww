package me.a8kj.ww.parent.configuration;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handles the management of configuration files for the plugin.
 */
public class Configuration {

    private final File file;
    private FileConfiguration configurationFile;

    /**
     * Constructs a Configuration instance.
     *
     * @param plugin          the JavaPlugin instance
     * @param child           the name of the configuration file
     * @param saveDefaultData whether to save default data if the file does not
     *                        exist
     */
    public Configuration(JavaPlugin plugin, String child, boolean saveDefaultData) {
        // Check if child ends with .yml and append if not
        if (!child.endsWith(".yml") || !child.endsWith(".yaml")) {
            child += ".yml";
        }

        this.file = new File(plugin.getDataFolder(), child);
        createFile(plugin, saveDefaultData);
        load();
    }

    private void createFile(JavaPlugin plugin, boolean saveDefaultData) {
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            if (saveDefaultData) {
                plugin.saveResource(file.getName(), saveDefaultData);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Saves the current configuration to the file.
     */
    public void save() {
        try {
            configurationFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the configuration from the file.
     */
    public void load() {
        configurationFile = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Retrieves the loaded configuration file.
     *
     * @return the FileConfiguration instance
     */
    public FileConfiguration getConfigurationFile() {
        return configurationFile;
    }
}
