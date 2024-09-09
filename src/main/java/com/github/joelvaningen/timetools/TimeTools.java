package com.github.joelvaningen.timetools;

import com.github.joelvaningen.timetools.command.TimeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeTools extends JavaPlugin {
    public void onEnable() {
        TimeCommand cmdExecutor = new TimeCommand(this);
        getCommand("time").setExecutor(cmdExecutor);
        getCommand("time").setTabCompleter(cmdExecutor);
    }
}