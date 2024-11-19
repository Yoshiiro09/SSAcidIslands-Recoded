package me.yoshiro09.acidislands.api.rain;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.events.AcidRainStartEvent;
import me.yoshiro09.acidislands.api.events.AcidRainStopEvent;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import me.yoshiro09.acidislands.utils.MessagesSender;
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
        final SettingsHandler settingsHandler = AcidIslandsAPI.getInstance().getSettingsHandler();

        if (!raining) {
            if (this.acidRainActive) {
                this.acidRainActive = false;
                MessagesSender.broadcast(settingsHandler.getSetting(SettingsKey.ACIDRAIN_MSG_ENDED));
                Bukkit.getPluginManager().callEvent(new AcidRainStopEvent(Bukkit.getWorld(this.worldName)));
            }
            return;
        }

        double randomValue = new Random().nextDouble();
        if (randomValue >= rarityPercentage) return;

        this.acidRainActive = true;
        MessagesSender.broadcast(settingsHandler.getSetting(SettingsKey.ACIDRAIN_MSG_STARTED));
        Bukkit.getPluginManager().callEvent(new AcidRainStartEvent(Bukkit.getWorld(this.worldName)));
    }
}
