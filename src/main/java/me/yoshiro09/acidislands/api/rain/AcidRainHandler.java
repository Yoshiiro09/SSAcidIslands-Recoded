package me.yoshiro09.acidislands.api.rain;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Random;

public class AcidRainHandler {
    private final String worldName;
    private final double rarityPercentage;
    private boolean acidRainActive;

    public AcidRainHandler() {
        final SettingsHandler settingsHandler = AcidIslandsAPI.getInstance().getSettingsHandler();

        this.worldName = settingsHandler.getSetting(SettingsKey.ACIDRAIN_WORLD);
        this.rarityPercentage = settingsHandler.getSettingAsDouble(SettingsKey.ACIDRAIN_RARITY_PERCENTAGE);
        this.acidRainActive = false;
    }

    public boolean isAcidRainActive() {
        return acidRainActive;
    }

    public boolean isTheRightWorld(World world) {
        return world.getName().equalsIgnoreCase(worldName);
    }

    public void updateAcidRainStatus(boolean raining) {
        if (!raining) {
            if (this.acidRainActive) {
                this.acidRainActive = false;
                Bukkit.broadcast(new TextComponent(String.format("[DEBUG] La pioggia acida nel mondo %s è appena terminata.", this.worldName)));
            }
            return;
        }

        double randomValue = new Random().nextDouble();
        if (randomValue >= rarityPercentage) return;

        this.acidRainActive = true;
        Bukkit.broadcast(new TextComponent(String.format("[DEBUG] La pioggia acida nel mondo %s è appena iniziata. [Percentuale: %s]", this.worldName, this.rarityPercentage * 100)));
    }
}
