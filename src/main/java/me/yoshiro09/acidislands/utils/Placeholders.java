package me.yoshiro09.acidislands.utils;

import java.util.LinkedHashMap;

public class Placeholders {
    public static LinkedHashMap<String, String> createPlaceholdersMap(String... strPlaceholder) {
        LinkedHashMap<String, String> placeholders = new LinkedHashMap<>();
        String key = null;
        boolean isKey = false;
        for (String placeholder : strPlaceholder) {
            if (isKey) {
                placeholders.put(key, placeholder);
            } else {
                key = placeholder;
            }
            isKey = !isKey;
        }
        return placeholders;
    }
}