package com.github.joelvaningen.timetools.messaging;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MessageTools {
    public static void messageAllPlayers(Server server, String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);

        for (Player player : server.getOnlinePlayers()) {
            player.sendMessage(parsed);
        }
    }
}
