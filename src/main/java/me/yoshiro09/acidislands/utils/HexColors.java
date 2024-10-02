package me.yoshiro09.acidislands.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexColors {

    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");

    public static String colorize(String message) {
        // Crea un matcher per trovare i codici colore nel messaggio
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', message));
        StringBuffer buffer = new StringBuffer();

        // Sostituisce ogni codice colore trovato con il corrispondente colore
        while (matcher.find()) matcher.appendReplacement(buffer, ChatColor.of(matcher.group(1)).toString());

        // Restituisce il messaggio con i codici colore sostituiti
        return matcher.appendTail(buffer).toString();
    }

    public static TextComponent colorizeTextComponent(String coloredMessage) {
        // Divide il messaggio in parti basate sui codici colore
        String[] parts = coloredMessage.split("&");
        ComponentBuilder builder = new ComponentBuilder();

        // Processa ogni parte del messaggio
        for (String part : parts) {
            if (part.length() >= 2) {
                char colorCode = part.charAt(1);

                // Se la parte inizia con un codice colore esadecimale
                if (part.startsWith("#") && part.length() >= 8 && part.matches("#[0-9a-fA-F]{6}.*")) {
                    String hexColor = part.substring(0, 7);
                    String text = part.substring(7);

                    // Aggiunge il testo al costruttore del componente e imposta il colore
                    builder.append(text);
                    builder.color(ChatColor.of(hexColor));
                }
                // Se la parte inizia con un codice colore standard di Minecraft
                else if (ChatColor.getByChar(colorCode) != null) {
                    ChatColor mcColor = ChatColor.getByChar(colorCode);
                    String text = part.substring(2);

                    // Aggiunge il testo al costruttore del componente e imposta il colore
                    builder.append(text);
                    builder.color(ChatColor.of(new Color(mcColor.getColor().getRGB())));
                }
                // Se la parte non inizia con un codice colore
                else {
                    builder.append(part);
                }
            } else {
                builder.append(part);
            }
        }

        // Crea il componente di testo colorato
        BaseComponent[] components = builder.create();
        return new TextComponent(components);
    }
}