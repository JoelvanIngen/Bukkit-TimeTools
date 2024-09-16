package com.github.joelvaningen.timetools.messaging;

import com.github.joelvaningen.timetools.TimeTools;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageTools {
    TimeTools plugin;

    public MessageTools(TimeTools plugin) {
        this.plugin = plugin;
    }

    public void messageAllPlayers(Server server, String message) {
        message = prependPrefix(message);

        // Parse MiniMessage
        Component parsed = parseMiniMessage(message);

        // Send to all players
        for (Player player : server.getOnlinePlayers()) {
            player.sendMessage(parsed);
        }
    }

    public void sendMessage(Audience target, String message) {
        message = prependPrefix(message);

        Component parsed = parseMiniMessage(message);
        target.sendMessage(parsed);
    }

    private @NotNull Component parseMiniMessage(@NotNull String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }

    private @NotNull String prependPrefix(@NotNull String message) {
        return plugin.getConfigLoader().getMessages().getString("prefix") + message;
    }
}
