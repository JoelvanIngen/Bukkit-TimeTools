package com.github.joelvaningen.timetools.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum TTConfig {
    CONFIG("config.yml"),
    MESSAGES("messages.yml");

    private final String fileName;

    TTConfig(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile(File folder) {
        return new File(folder, getFileName());
    }

    public YamlConfiguration load() {
        return YamlConfiguration.loadConfiguration(new File(fileName));
    }
}
