package com.github.joelvaningen.timetools.command;

import com.github.joelvaningen.timetools.TimeTools;
import com.github.joelvaningen.timetools.time.MinecraftTime;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTimeGet extends BaseTimeCommand {
    private final TimeTools plugin;

    public CommandTimeGet(TimeTools plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (!ensurePermission(player)) {
            return false;
        }

        MinecraftTime time = new MinecraftTime(player.getWorld().getTime());

        plugin.getMessageTools().sendMessage(player,
                "Current time is " + time.toString() + " (" + time.getTicks() + "/24000 ticks).");
        return true;
    }

    public boolean ensurePermission(Player player) {
        return player.hasPermission(getPermission());
    }

    public @NotNull String getPermission() {
        return "timetools.time";
    }
}
