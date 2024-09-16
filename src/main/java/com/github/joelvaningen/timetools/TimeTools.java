package com.github.joelvaningen.timetools;

import com.github.joelvaningen.timetools.command.TimeCommand;
import com.github.joelvaningen.timetools.config.TTConfigLoader;
import com.github.joelvaningen.timetools.messaging.MessageTools;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TimeTools extends JavaPlugin {
    private TTConfigLoader config;
    private MessageTools messageTools;

    public void onEnable() {
        registerCommands();
        loadConfig();
        loadMessageTools();
    }

    private void registerCommands() {
        TimeCommand cmdExecutor = new TimeCommand(this);
        getCommand("time").setExecutor(cmdExecutor);
        getCommand("time").setTabCompleter(cmdExecutor);
    }

    private void loadConfig() {
        this.config = new TTConfigLoader(this);
    }

    private void loadMessageTools() {
        this.messageTools = new MessageTools(this);
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        throw new NotImplementedException("TimeTools().getConfig() was called, but should not be used!");
    }

    public @NotNull TTConfigLoader getConfigLoader() {
        return config;
    }

    public MessageTools getMessageTools() {
        return messageTools;
    }
}