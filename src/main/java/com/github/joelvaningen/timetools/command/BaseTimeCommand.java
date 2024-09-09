package com.github.joelvaningen.timetools.command;

import org.bukkit.entity.Player;

public abstract class BaseTimeCommand {
    public abstract boolean execute(Player player, String[] args);
}
