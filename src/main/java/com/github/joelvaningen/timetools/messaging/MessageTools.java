package com.github.joelvaningen.timetools.messaging;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MessageTools {
    public static void messageAllPlayers(Server server, String message) {
        for (Player player : server.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }
}
