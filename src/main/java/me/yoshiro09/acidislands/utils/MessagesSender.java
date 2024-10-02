package me.yoshiro09.acidislands.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;

public class MessagesSender {

    public static String translateColors(String s, boolean hexa) {
        if (hexa) s = HexColors.colorize(s);
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void broadcast(String message) {
        message = translateColors(message, true);
        Bukkit.broadcast(new TextComponent(message));
    }
}