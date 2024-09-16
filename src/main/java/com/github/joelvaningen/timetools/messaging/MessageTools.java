package com.github.joelvaningen.timetools.messaging;

import com.github.joelvaningen.timetools.TimeTools;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTools {
    TimeTools plugin;

    public MessageTools(TimeTools plugin) {
        this.plugin = plugin;
    }

    public void messageAllPlayers(Server server, String message, @Nullable HashMap<String, String> placeholders) {
        message = applyPlaceholders(message, placeholders);

        // Parse MiniMessage
        Component parsed = parseMiniMessage(message);

        // Send to all players
        for (Player player : server.getOnlinePlayers()) {
            player.sendMessage(parsed);
        }
    }

    public void sendMessage(Audience target, String message, @Nullable HashMap<String, String> placeholders) {
        message = applyPlaceholders(message, placeholders);

        Component parsed = parseMiniMessage(message);
        target.sendMessage(parsed);
    }

    private Component parseMiniMessage(String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        return miniMessage.deserialize(message);
    }

    private String applyPlaceholders(@NotNull String message, @Nullable HashMap<String, String> placeholders) {
        message = applyYamlPlaceholders(message, placeholders);

        if (placeholders == null) { return message; }

        for (String key : placeholders.keySet()) {
            message = message.replace(key, placeholders.get(key));
        }
        return message;
    }

    private String applyYamlPlaceholders(@NotNull String message, @Nullable HashMap<String, String> ignorePlaceholders) {
        // Open messages.yml
        YamlConfiguration messageConfig = plugin.getConfigLoader().getMessages();

        // Regex magic
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            if (ignorePlaceholders != null && ignorePlaceholders.containsKey(placeholder)) { continue; }

            String value = messageConfig.getString("custom-placeholders." + placeholder);

            if (value == null) {
                plugin.getLogger().warning("Could not find custom placeholder " + placeholder + " in messages.yml");
                continue;
            }

            message = message.replaceFirst("\\$\\{" + placeholder + "}", value);
        }

        return message;
    }
}
