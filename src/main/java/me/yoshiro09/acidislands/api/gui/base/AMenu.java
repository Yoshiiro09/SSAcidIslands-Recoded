package me.yoshiro09.acidislands.api.gui.base;

import me.yoshiro09.acidislands.api.gui.base.enums.ESlotType;
import me.yoshiro09.acidislands.api.gui.base.player.PlayerCache;
import me.yoshiro09.acidislands.utils.ItemsManager;
import me.yoshiro09.acidislands.utils.MessagesSender;
import me.yoshiro09.acidislands.utils.NBTKeys;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public abstract class AMenu implements InventoryHolder {

    protected final UUID uuid;
    protected final PlayerCache playerCache;
    protected Inventory inventory;

    public AMenu(PlayerCache playerCache) {
        this.uuid = UUID.randomUUID();
        this.playerCache = playerCache;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent event);

    public abstract void handleClose(InventoryCloseEvent event);

    public abstract void handleDrag(InventoryDragEvent event);

    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), MessagesSender.translateColors(getMenuName().replace("%page%", "1"), true, playerCache.getOwner()));
        this.setMenuItems();
        playerCache.getOwner().openInventory(inventory);
    }

    public void setButton(ESlotType type, LinkedHashMap<String, String> placeholders) {
        // Controlla se il pulsante è abilitato dal file di configurazione.
        if (!isButtonEnabled(type)) return;

        // Crea l'oggetto ItemStack del pulsante.
        ItemStack itemStack = ItemsManager.getItemStack(Material.getMaterial(getButtonItem(type)), getButtonDisplayName(type), null, getButtonCustomModelData(type), playerCache.getOwner());
        itemStack.setItemMeta(ItemsManager.addNBT(itemStack.getItemMeta(), NBTKeys.BUTTON_UTILITY, type.toString()));
        // Applica i placeholder alla lore del pulsante.
        itemStack = ItemsManager.addLore(itemStack, getButtonLore(type), placeholders, playerCache.getOwner());
        inventory.setItem(getButtonSlot(type), itemStack);
    }

    public void setButtons(ESlotType type, LinkedHashMap<String, String> placeholders) {
        // Controlla se il pulsante è abilitato dal file di configurazione.
        if (!isButtonEnabled(type)) return;

        // Crea l'oggetto ItemStack del pulsante, trasformando i placeholder in valori reali e lo carica negli slot.
        ItemStack itemStack = ItemsManager.getItemStack(Material.getMaterial(getButtonItem(type)), getButtonDisplayName(type), null, getButtonCustomModelData(type), playerCache.getOwner());
        itemStack.setItemMeta(ItemsManager.addNBT(itemStack.getItemMeta(), NBTKeys.BUTTON_UTILITY, type.toString()));
        itemStack = ItemsManager.addLore(itemStack, getButtonLore(type), placeholders, playerCache.getOwner());
        for (int slot : getButtonSlots(type)) inventory.setItem(slot, itemStack);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public abstract String getButtonItem(ESlotType button);

    public abstract boolean isButtonEnabled(ESlotType button);

    public abstract String getButtonDisplayName(ESlotType button);

    public abstract List<String> getButtonLore(ESlotType button);

    public abstract int getButtonSlot(ESlotType button);

    public abstract List<Integer> getButtonSlots(ESlotType button);

    public abstract int getButtonCustomModelData(ESlotType button);
}
