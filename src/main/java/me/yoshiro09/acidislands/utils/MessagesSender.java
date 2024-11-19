package me.yoshiro09.acidislands.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MessagesSender {

    public static String translateColors(String s, boolean hexa, Player parseTo) {
        if (Objects.nonNull(parseTo)) s = PlaceholderAPI.setPlaceholders(parseTo, s);
        if (hexa) s = HexColors.colorize(s);
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void sendMessage(Player player, String message, Player parseTo) {
        message = translateColors(message, true, parseTo);
        player.sendMessage(message);
    }

    public static void broadcast(String message) {
        message = translateColors(message, true, null);
        Bukkit.broadcast(new TextComponent(message));
    }

    public static void sendActionBar(Player player, String message) {
        message = translateColors(message, true, player);
        player.sendActionBar(new TextComponent(message));
    }
}