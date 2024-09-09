package com.github.joelvaningen.timetools.config;

import com.github.joelvaningen.timetools.TimeTools;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TTConfigLoader extends YamlConfiguration {
    private final TimeTools plugin;
    private final YamlConfiguration config;

    public TTConfigLoader(TimeTools plugin) {
        this.plugin = plugin;

        final File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            throw new IllegalStateException("Could not create data folder");
        }

        final File configFile = new File(dataFolder, "config.yml");
        if (!configFile.exists()) {
            createConfigFile();
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void createConfigFile() {
        plugin.saveResource("config.yml", false);
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
