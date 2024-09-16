package com.github.joelvaningen.timetools.command;

import com.github.joelvaningen.timetools.TimeTools;
import com.github.joelvaningen.timetools.time.MinecraftTime;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandTimeSet extends BaseTimeCommand {
    private final TimeTools plugin;

    private final Map<String, Long> timeToTicks = new HashMap<>();

    public Collection<String> getValidTimeStrings() {
        return timeToTicks.keySet();
    }

    public Collection<String> getExampleClockTimes() {
        Collection<String> clockTimes = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            clockTimes.add(String.format("%02d:00", hour));
        }

        return clockTimes;
    }

    public Collection<String> getExampleTargetTicks() {
        Collection<String> clockTimes = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            clockTimes.add(String.valueOf(hour * 1_000));
        }

        return clockTimes;
    }

    public CommandTimeSet(TimeTools plugin) {
        this.plugin = plugin;

        populateHashMaps();
    }

    private void populateHashMaps() {
        timeToTicks.put("dawn", 0L);
        timeToTicks.put("day", 1_000L);
        timeToTicks.put("midday", 6_000L);
        timeToTicks.put("dusk", 12_000L);
        timeToTicks.put("night", 13_000L);
        timeToTicks.put("midnight", 18_000L);
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (!ensurePermissions(player)) {
            plugin.getMessageTools().sendMessage(player, plugin.getConfigLoader().getMessages().getString("no-permission"), null);
            return true;
        }

        String timeString = args[0];

        if (!tryExecuteReportSuccess(player, timeString)) {
            return false;
        }

        announceNewTime(player);
        return true;
    }

    public boolean tryExecuteReportSuccess(Player player, String timeString) {
        return applyTimeString(player, timeString) ||
                applyClockTime(player, timeString) ||
                applyTicksAmount(player, timeString);
    }

    private boolean applyTimeString(Player player, String timeString) {
        Long targetTicks = timeToTicks.get(timeString);
        if (targetTicks == null) {
            return false;
        }

        player.getWorld().setTime(targetTicks);
        return true;
    }

    private boolean applyClockTime(Player player, String clockTime) {
        String[] components = clockTime.split(":");
        if (!(components.length == 2)) {
            return false;
        }

        int hours, minutes;
        try {
            hours = Integer.parseInt(components[0]);
            minutes = Integer.parseInt(components[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (hours < 0 || hours > 23) { return false; }
        if (minutes < 0 || minutes > 59) { return false; }

        long targetTicks = MinecraftTime.fromComponents(hours, minutes).getTicks();
        player.getWorld().setTime(targetTicks);
        return true;
    }

    private boolean applyTicksAmount(Player player, String amount) {
        long targetTicks;
        try {
            targetTicks = Long.parseLong(amount);
        } catch (NumberFormatException e) {
            return false;
        }

        player.getWorld().setTime(targetTicks);
        return true;
    }

    private void announceNewTime(Player player) {
        MinecraftTime time = new MinecraftTime(player.getWorld().getTime());
        String message = player.getName() + " set the time to " + time.toString() + " (" + time.getTicks() + "/24000 ticks).";
        plugin.getMessageTools().messageAllPlayers(player.getServer(), message, null);
    }

    private boolean ensurePermissions(Player player) {
        return player.hasPermission(getPermission());
    }

    public @NotNull String getPermission() {
        return "timetools.time.set";
    }
}
