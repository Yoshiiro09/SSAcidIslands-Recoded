package me.yoshiro09.acidislands.api;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduitManager;
import me.yoshiro09.acidislands.api.rain.AcidRainHandler;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;

import java.util.Objects;

public class AcidIslandsAPI {
    private static AcidIslandsAPI instance;

    private final SettingsHandler settingsHandler;
    private final AcidRainHandler acidRainHandler;
    private final PurifyingConduitManager purifyingConduitManager;

    public AcidIslandsAPI() throws IllegalAccessException {
        final String className = getClass().getSimpleName();
        // Prima istanza della classe
        if (Objects.isNull(instance)) {
            instance = this;

            this.settingsHandler = new SettingsHandler();
            this.settingsHandler.loadSettings();
            this.acidRainHandler = new AcidRainHandler();
            this.purifyingConduitManager = new PurifyingConduitManager();
            this.purifyingConduitManager.loadPurifyingConduits();

            AcidIslandsMain.getInstance().getPlugin().getLogger().info(String.format("[ACIDISLANDS-RECODED] %s: Classe inizializzata!", className));
        } else {
            throw new IllegalAccessException(String.format("Non puoi creare un'altra istanza della classe %s. Per favore, usa il metodo ‘%s.getInstance()’.", className, className));
        }
    }

    public static AcidIslandsAPI getInstance() {
        return instance;
    }

    public PurifyingConduitManager getPurifyingConduitManager() {
        return purifyingConduitManager;
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public AcidRainHandler getAcidRainHandler() {
        return acidRainHandler;
    }
}
