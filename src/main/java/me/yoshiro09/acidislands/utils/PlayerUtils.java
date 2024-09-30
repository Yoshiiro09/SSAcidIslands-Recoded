package me.yoshiro09.acidislands.utils;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Optional;

public class PlayerUtils {
    private static final EnumSet<Material> WATER_TYPES = createWaterMaterials();

    public static boolean isInWater(Player player) {
        Block playerBlock = player.getLocation().getBlock();
        return isBlockInWater(playerBlock) || isBlockInWater(player.getEyeLocation().getBlock())
                || (player.getWorld().hasStorm() && AcidIslandsAPI.getInstance().getAcidRainHandler().isAcidRainActive());
    }

    private static boolean isBlockInWater(Block block) {
        return WATER_TYPES.contains(block.getType());
    }

    private static EnumSet<Material> createWaterMaterials() {
        EnumSet<Material> waterMaterials = EnumSet.noneOf(Material.class);
        Optional.ofNullable(Material.matchMaterial("WATER")).ifPresent(waterMaterials::add);
        Optional.ofNullable(Material.matchMaterial("STATIONARY_WATER")).ifPresent(waterMaterials::add);
        return waterMaterials;
    }

    public static boolean isSameLocation(Location loc1, Location loc2) {
        return loc1.getBlockX() == loc2.getBlockX()
                && loc1.getBlockY() == loc2.getBlockY()
                && loc1.getBlockZ() == loc2.getBlockZ()
                && loc1.getWorld().getName().equals(loc2.getWorld().getName());
    }

    public static boolean canTakeDamage(Player player) {
        GameMode gameMode = player.getGameMode();
        return (gameMode != GameMode.CREATIVE && gameMode != GameMode.SPECTATOR);
    }
}
