package com.github.joelvaningen.timetools.command;

import com.github.joelvaningen.timetools.TimeTools;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TimeCommand implements CommandExecutor, TabCompleter {
    private final TimeTools plugin;

    private final BaseTimeCommand commandTimeGet;
    private final CommandTimeSet commandTimeSet;

    public TimeCommand(TimeTools plugin) {
        this.plugin = plugin;

        commandTimeGet = new CommandTimeGet(plugin);
        commandTimeSet  = new CommandTimeSet(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!ensurePlayer(commandSender)) { return true; }
        Player player = (Player) commandSender;

        return parseCommand(player, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 1) { return null;}

        List<String> completions = new ArrayList<>();
        completions.addAll(commandTimeSet.getValidTimeStrings());
        completions.addAll(commandTimeSet.getExampleClockTimes());
        completions.addAll(commandTimeSet.getExampleTargetTicks());

        List<String> validCompletions = new ArrayList<>();
        for (String completion : completions) {
            if (completion.startsWith(args[0])) {
                validCompletions.add(completion);
            }
        }

        return validCompletions;
    }

    private boolean ensurePlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            plugin.getMessageTools().sendMessage(sender, plugin.getConfigLoader().getMessages().getString("static.not-player"));
            return false;
        }
        return true;
    }

    private boolean parseCommand(Player player, String[] args) {
        if (args.length == 0) {
            return commandTimeGet.execute(player, args);
        } else if (args.length == 1) {
            return commandTimeSet.execute(player, args);
        }
        return false;
    }
}

