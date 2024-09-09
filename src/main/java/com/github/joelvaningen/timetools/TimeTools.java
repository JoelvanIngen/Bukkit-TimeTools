package com.github.joelvaningen.timetools;

import com.github.joelvaningen.timetools.command.TimeCommand;
import com.github.joelvaningen.timetools.config.TTConfigLoader;
import com.github.joelvaningen.timetools.messaging.MessageTools;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TimeTools extends JavaPlugin {
    private TTConfigLoader configLoader;
    private YamlConfiguration config;
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
        this.configLoader = new TTConfigLoader(this);
        this.config = this.configLoader.getConfig();
    }

    private void loadMessageTools() {
        this.messageTools = new MessageTools(this);
    }

    @Override
    public @NotNull YamlConfiguration getConfig() {
        return config;
    }

    public MessageTools getMessageTools() {
        return messageTools;
    }
}