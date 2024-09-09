package com.github.joelvaningen.timetools.command;

import com.github.joelvaningen.timetools.TimeTools;
import com.github.joelvaningen.timetools.time.MinecraftTime;
import org.bukkit.entity.Player;

public class CommandTimeGet extends BaseTimeCommand {
    private final TimeTools plugin;

    public CommandTimeGet(TimeTools plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(Player player, String[] args) {
        MinecraftTime time = new MinecraftTime(player.getWorld().getTime());

        plugin.getMessageTools().sendMessage(player,
                "Current time is " + time.toString() + " (" + time.getTicks() + "/24000 ticks).");
        return true;
    }
}
