package me.yoshiro09.acidislands.listeners;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.rain.AcidRainHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class AcidRainListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        final AcidRainHandler acidRainHandler = AcidIslandsAPI.getInstance().getAcidRainHandler();
        if (!acidRainHandler.isTheRightWorld(event.getWorld())) return;

        acidRainHandler.updateAcidRainStatus(event.toWeatherState());
    }
}
