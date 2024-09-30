package me.a8kj.ww.parent.configuration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handles the management of configuration files for the plugin.
 */
public class Configuration {

    private final File file;
    private FileConfiguration configurationFile;
    private final Logger logger;

    /**
     * Constructs a Configuration instance.
     *
     * @param plugin          the JavaPlugin instance
     * @param child           the name of the configuration file
     * @param saveDefaultData whether to save default data if the file does not
     *                        exist
     */
    public Configuration(JavaPlugin plugin, String child, boolean saveDefaultData) {
        this.logger = plugin.getLogger(); // Initialize the logger
        // Check if child ends with .yml and append if not
        if (!child.endsWith(".yml") && !child.endsWith(".yaml")) {
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
                try {
                    plugin.saveResource(file.getName(), saveDefaultData);
                    logger.info("Default config file created: " + file.getName());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to save default config file: " + file.getName(), e);
                }
            } else {
                try {
                    file.createNewFile();
                    logger.info("Config file created: " + file.getName());
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to create config file: " + file.getName(), e);
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
            logger.info("Config file saved: " + file.getName());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save config file: " + file.getName(), e);
        }
    }

    /**
     * Loads the configuration from the file.
     */
    public void load() {
        configurationFile = YamlConfiguration.loadConfiguration(file);
        logger.info("Config file loaded: " + file.getName());
    }

    /**
     * Retrieves the loaded configuration file.
     *
     * @return the FileConfiguration instance
     */
    public FileConfiguration getConfigurationFile() {
        return configurationFile;
    }

    /**
     * Retrieves the loaded yaml configuration file.
     *
     * @return the YamlConfiguration instance
     */
    public YamlConfiguration getYamConfiguration() {
        return (YamlConfiguration) getConfigurationFile();
    }
}
