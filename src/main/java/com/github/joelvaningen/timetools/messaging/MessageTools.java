package com.github.joelvaningen.timetools.messaging;

import com.github.joelvaningen.timetools.TimeTools;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MessageTools {
    TimeTools plugin;

    public MessageTools(TimeTools plugin) {
        this.plugin = plugin;
    }

    public void messageAllPlayers(Server server, String message) {
        // Get message string and replace placeholder
        String formatted = formatMessage(message);

        // Parse MiniMessage
        Component parsed = parseMiniMessage(formatted);

        // Send to all players
        for (Player player : server.getOnlinePlayers()) {
            player.sendMessage(parsed);
        }
    }

    public void sendMessage(Player player, String message) {
        String formatted = formatMessage(message);
        Component parsed = parseMiniMessage(formatted);
        player.sendMessage(parsed);
    }

    private String formatMessage(String message) {
        String unformattedMessage = plugin.getConfig().getString("messages.format");
        assert unformattedMessage != null;

        return unformattedMessage.replace("${message}", message);
    }

    private Component parseMiniMessage(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }
}
