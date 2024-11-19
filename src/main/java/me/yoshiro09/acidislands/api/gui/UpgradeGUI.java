package me.yoshiro09.acidislands.api.gui;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.files.FileManager;
import me.yoshiro09.acidislands.api.gui.base.AMenu;
import me.yoshiro09.acidislands.api.gui.base.enums.ESlotType;
import me.yoshiro09.acidislands.api.gui.base.player.PlayerCache;
import me.yoshiro09.acidislands.api.gui.base.player.PlayerCacheManager;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduitLevel;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduitManager;
import me.yoshiro09.acidislands.utils.Placeholders;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class UpgradeGUI extends AMenu {

    private final FileConfiguration menuFile;

    public UpgradeGUI(PlayerCache playerCache) {
        super(playerCache);
        menuFile = FileManager.getFileManager(FileManager.FileType.MENU_PURIFYINGCONDUITUPGRADE).getYamlConfiguration();
    }

    @Override
    public String getMenuName() {
        return menuFile.getString("title");
    }

    @Override
    public int getSlots() {
        return menuFile.getInt("slots");
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        if (Objects.isNull(event.getClickedInventory())
                || event.getClickedInventory().getType() == InventoryType.PLAYER) return;

        final int clickedSlot = event.getSlot();
        if (clickedSlot == getButtonSlot(ESlotType.CLOSE_BUTTON)) {
            playerCache.getOwner().closeInventory();
            PlayerCacheManager.removePlayerCache(playerCache.getOwner());
            return;
        }

//        if (clickedSlot == getButtonSlot((ESlotType.UPGRADE_HOPPER_ITEMS))) {
//            playerCache.getOwner().closeInventory();
//            playerCache.getEditingHopper().upgrade(playerCache.getOwner(), UpgradeType.ITEMMOVING);
//            return;
//        }
    }

    @Override
    public void handleClose(InventoryCloseEvent event) {}

    @Override
    public void handleDrag(InventoryDragEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        final PurifyingConduitManager pcm = AcidIslandsAPI.getInstance().getPurifyingConduitManager();
        final PurifyingConduitLevel actualLevel = pcm.getPurifyingConduitLevel(this.playerCache.getEditingPurifyingConduit().getLevel());
        final PurifyingConduitLevel nextLevel = pcm.getPurifyingConduitLevel(this.playerCache.getEditingPurifyingConduit().getLevel() + 1);

        final String translatedMinus = menuFile.getString("variables.minus");
        final String translatedPlus = menuFile.getString("variables.plus");
        final String translatedMaxed = menuFile.getString("variables.maxed");

        final boolean hasNextLevel = Objects.nonNull(nextLevel);

        int rangeDifference = hasNextLevel ? nextLevel.getRange() - actualLevel.getRange() : 0;


        final LinkedHashMap<String, String> placeholders = Placeholders.createPlaceholdersMap(
                "%next-level%", !hasNextLevel ? translatedMaxed : nextLevel.getLevel() + "",
                "%next-range%", !hasNextLevel ? translatedMaxed : nextLevel.getRange() + "",
                "%upgrade-price%", !hasNextLevel ? translatedMaxed : nextLevel.getPrice() + "",
                "%range-difference%", !hasNextLevel ? translatedMaxed : String.format("%s%s", rangeDifference < 0 ? translatedMinus : translatedPlus, Math.abs(rangeDifference))
        );

//        final Hopper hopper = this.playerCache.getEditingHopper();
//        final Upgrade actualSpeedUpgrade  = UpgradeManager.getUpgradeByLevel(hopper.getLevelHopperSpeedUpgrade(), UpgradeType.HOPPERSPEED);
//        final Upgrade actualMovingUpgrade  = UpgradeManager.getUpgradeByLevel(hopper.getLevelHopperSpeedUpgrade(), UpgradeType.ITEMMOVING);
//
//        final Upgrade nextSpeedUpgrade  = UpgradeManager.getUpgradeByLevel(hopper.getLevelHopperSpeedUpgrade() + 1, UpgradeType.HOPPERSPEED);
//        final Upgrade nextMovingUpgrade = UpgradeManager.getUpgradeByLevel(hopper.getLevelMovingItemsUpgrade() + 1, UpgradeType.ITEMMOVING);
//
//        final Upgrade maxSpeedUpgrade = UpgradeManager.getMaxLevelUpgrade(UpgradeType.HOPPERSPEED);
//        final Upgrade maxMovingUpgrade = UpgradeManager.getMaxLevelUpgrade(UpgradeType.ITEMMOVING);
//
//        final LinkedHashMap<String, String> placeholders = Placeholders.createPlaceholdersMap(
//                "%speedLevel%", hopper.getLevelHopperSpeedUpgrade() + "", "%maxSpeedLevel%", maxSpeedUpgrade.getLevel() + "", "%speed%", actualSpeedUpgrade.getFormattedSpeed() + "",
//                "%nextSpeed%", Objects.isNull(nextSpeedUpgrade) ? "&dʟɪᴠ. ᴍᴀssɪᴍᴏ" : nextSpeedUpgrade.getFormattedSpeed() + "", "%nextSpeedDifference%", Objects.isNull(nextSpeedUpgrade) ? "0" : (((double) actualSpeedUpgrade.getSpeed() - nextSpeedUpgrade.getSpeed()) / 20) + "",
//                "%movingLevel%", hopper.getLevelMovingItemsUpgrade() + "", "%maxMovingLevel%", maxMovingUpgrade.getLevel() + "", "%movingItems%", actualMovingUpgrade.getMovingQuantity() + "",
//                "%nextMovingItems%", Objects.isNull(nextMovingUpgrade) ? "&dʟɪᴠ. ᴍᴀssɪᴍᴏ" : nextMovingUpgrade.getMovingQuantity() + "", "%nextMovingDifference%", Objects.isNull(nextMovingUpgrade) ? "0" : (nextMovingUpgrade.getMovingQuantity() - actualMovingUpgrade.getMovingQuantity()) + "",
//                "%speedUpgradePrice%", Objects.isNull(nextSpeedUpgrade) ? "&dʟɪᴠ. ᴍᴀssɪᴍᴏ" : nextSpeedUpgrade.getPrice() + "", "%movingUpgradePrice%", Objects.isNull(nextMovingUpgrade) ? "&dʟɪᴠ. ᴍᴀssɪᴍᴏ" : nextMovingUpgrade.getPrice() + ""
//        );

        setButtons(ESlotType.BACKGROUND, placeholders);
        setButton(ESlotType.CLOSE_BUTTON, placeholders);
        setButton(ESlotType.UPGRADE, placeholders);
    }

    @Override
    public String getButtonItem(ESlotType button) {
        return menuFile.getString(String.format("pages_buttons.%s.item", button.toString().toLowerCase()));
    }

    @Override
    public boolean isButtonEnabled(ESlotType button) {
        return menuFile.getBoolean(String.format("pages_buttons.%s.enabled", button.toString().toLowerCase()));
    }

    @Override
    public String getButtonDisplayName(ESlotType button) {
        return menuFile.getString(String.format("pages_buttons.%s.display_name", button.toString().toLowerCase()));
    }

    @Override
    public List<String> getButtonLore(ESlotType button) {
        return menuFile.getStringList(String.format("pages_buttons.%s.lore", button.toString().toLowerCase()));
    }

    @Override
    public int getButtonSlot(ESlotType button) {
        return menuFile.getInt(String.format("pages_buttons.%s.slot", button.toString().toLowerCase()));
    }

    @Override
    public List<Integer> getButtonSlots(ESlotType button) {
        return menuFile.getIntegerList(String.format("pages_buttons.%s.slots", button.toString().toLowerCase()));
    }

    @Override
    public int getButtonCustomModelData(ESlotType button) {
        return menuFile.getInt(String.format("pages_buttons.%s.customModelData", button.toString().toLowerCase()));
    }
}
