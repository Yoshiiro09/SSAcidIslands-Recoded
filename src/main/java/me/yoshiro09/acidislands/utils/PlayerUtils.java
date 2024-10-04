package me.yoshiro09.acidislands.utils;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

public class PlayerUtils {
    private static final EnumSet<Material> WATER_TYPES = createWaterMaterials();

    public static boolean isInWater(Player player) {
        Block playerBlock = player.getLocation().getBlock();

        final Entity riding = player.getVehicle();
        if (Objects.nonNull(riding) && riding instanceof Boat) return false;

        return isBlockInWater(playerBlock) || isBlockInWater(player.getEyeLocation().getBlock());
    }

    public static boolean isInRain(Player player) {
        if (!player.getWorld().hasStorm() || !AcidIslandsAPI.getInstance().getAcidRainHandler().isAcidRainActive())
            return false;

        boolean isProtectedFromRain = false;
        for (int y = player.getEyeLocation().getBlockY(); y <= player.getWorld().getMaxHeight(); y++) {
            Block blockAbove = player.getWorld().getBlockAt(player.getLocation().getBlockX(), y, player.getLocation().getBlockZ());
            if (blockAbove.getType().isSolid()) {
                isProtectedFromRain = true;
                break;
            }
        }

        return !isProtectedFromRain;
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
