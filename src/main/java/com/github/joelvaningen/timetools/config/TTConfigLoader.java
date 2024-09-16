package com.github.joelvaningen.timetools.config;

import com.github.joelvaningen.timetools.TimeTools;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TTConfigLoader {
    private final TimeTools plugin;
    private final YamlConfiguration config;
    private final YamlConfiguration messages;

    public TTConfigLoader(TimeTools plugin) {
        this.plugin = plugin;

        ensureConfigFolderExists();
        ensureConfigFilesExist();

        this.config = YamlConfiguration.loadConfiguration(TTConfig.CONFIG.getFile(plugin.getDataFolder()));
        this.messages = YamlConfiguration.loadConfiguration(TTConfig.MESSAGES.getFile(plugin.getDataFolder()));
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration getMessages() { return messages; }

    private void ensureConfigFolderExists() {
        if (!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdirs()) {
            throw new IllegalStateException("Could not create data folder");
        }
    }

    private void ensureConfigFilesExist() {
        for (TTConfig config : TTConfig.values()) {
            File file = config.getFile(plugin.getDataFolder());
            if (!file.exists()) {
                createConfigFile(config);
            }
        }
    }

    private void createConfigFile(TTConfig config) {
        plugin.saveResource(config.getFileName(), false);
    }
}
