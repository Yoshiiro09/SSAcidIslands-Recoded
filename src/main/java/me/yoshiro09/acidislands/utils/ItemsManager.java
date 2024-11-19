package me.yoshiro09.acidislands.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.yoshiro09.acidislands.AcidIslandsMain;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemsManager {

    public static ItemStack getItemStack(Material material, String displayName, List<String> lore, int customModelData, Player parseTo) {
        ItemStack itemStack = new ItemStack(material);
        return getItemStack(itemStack, displayName, lore, customModelData, parseTo);
    }

    public static ItemStack getItemStack(ItemStack itemStack, String displayName, List<String> lore, int customModelData, Player parseTo) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        // Display Name non nullo e non vuoto quindi aggiungo i colori e imposto il nuovo nome dell'oggetto.
        if (Objects.nonNull(displayName) && !displayName.isBlank())
            itemMeta.setDisplayName(MessagesSender.translateColors(displayName, true, parseTo));

        // Lore non nulla e non vuota quindi aggiungo i colori e imposto la nuova lore dell'oggetto.
        if (Objects.nonNull(lore) && lore.size() > 0) {
            for (int i = 0; i < lore.size(); i++) {
                // Se parsePlaceholders è true, converto le variabili di PlaceholderAPI.
                lore.set(i, MessagesSender.translateColors(lore.get(i), true, parseTo));
            }
            itemMeta.setLore(lore);
        }
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack addLore(ItemStack itemStack, List<String> lore, Map<String, String> placeholders, Player parseTo) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        // Inizializza una nuova lore se l'oggetto non ne ha una.
        final List<String> newLore = (Objects.isNull(itemMeta.getLore())) ? new ArrayList<>() : itemMeta.getLore();

        // Scorre le linee della lore da aggiungere e sostituisce le variabili interne.
        for (String line : lore) {
            // Se placeholders non è null, sostituisce le variabili interne.
            if (Objects.nonNull(placeholders))
                for (String key : placeholders.keySet()) {
                    String value = placeholders.get(key);
                    line = line.replace(key, value);
                }
            newLore.add(MessagesSender.translateColors(line, true, parseTo));
        }
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemMeta addNBT(final ItemMeta itemMeta, final NBTKeys key, final String value) {
        if (Objects.nonNull(itemMeta))
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(AcidIslandsMain.getInstance().getPlugin(), key.toString().toLowerCase()), PersistentDataType.STRING, value);
        return itemMeta;
    }

    public static String getNBTValue(final ItemMeta itemMeta, final NBTKeys key) {
        final NamespacedKey namespacedKey = new NamespacedKey(AcidIslandsMain.getInstance().getPlugin(), key.toString().toLowerCase());
        if (itemMeta.getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING)) {
            return itemMeta.getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }

    public static boolean areSimilar(ItemStack item1, ItemStack item2) {
        // Almeno uno dei due oggetti è nullo.
        if (Objects.isNull(item1) || Objects.isNull(item2)) return false;
        // I due oggetti sono fatti di materiali diversi.
        if (item1.getType() != item2.getType()) return false;

        // I due oggetti hanno un itemMeta, quindi controllo se hanno un nome e una lore.
        if (item1.hasItemMeta() && item2.hasItemMeta()) {
            final ItemMeta meta1 = item1.getItemMeta();
            final ItemMeta meta2 = item2.getItemMeta();

            // Uno dei due oggetti ha un nome e l'altro no.
            if (meta1.hasDisplayName() != meta2.hasDisplayName()) return false;
            // Entrambi hanno un nome quindi procedo a confrontarli.
            if (meta1.hasDisplayName() && meta2.hasDisplayName())
                if (!meta1.getDisplayName().equals(meta2.getDisplayName())) return false;
            // Uno dei due oggetti ha una lore e l'altro no.
            if (meta1.hasLore() != meta2.hasLore()) return false;
            // Entrambi hanno una lore quindi procedo a confrontarli.
            if (meta1.hasLore() && meta2.hasLore())
                if (!meta1.getLore().equals(meta2.getLore())) return false;
            // Uno dei due oggetti ha incantamenti e l'altro no.
            if (meta1.hasEnchants() != meta2.hasEnchants()) return false;
            // Entrambi hanno incantamenti quindi procedo a confrontarli.
            if (meta1.hasEnchants() && meta2.hasEnchants())
                if (!meta1.getEnchants().equals(meta2.getEnchants())) return false;
        } else {
            // Se solo uno dei due oggetti ha un itemMeta non è detto che siano diversi.
            // Procedo a controllare se il nome dell'itemMeta è vuoto.
            if (!item1.hasItemMeta() && item2.hasItemMeta() && !item2.getItemMeta().getDisplayName().isEmpty()) return false;
            if (!item2.hasItemMeta() && item1.hasItemMeta() && !item1.getItemMeta().getDisplayName().isEmpty()) return false;
        }
        // Arrivato fin qui, i due oggetti sono uguali.
        return true;
    }
}
