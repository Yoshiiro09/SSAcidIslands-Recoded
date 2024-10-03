package me.yoshiro09.acidislands.api;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.files.FileManager;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import me.yoshiro09.acidislands.api.rain.AcidRainHandler;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AcidIslandsAPI {
    private static AcidIslandsAPI instance;

    private final SettingsHandler settingsHandler;
    private final AcidRainHandler acidRainHandler;

    private final List<PurifyingConduit> purifyingConduitsList;

    public AcidIslandsAPI() throws IllegalAccessException {
        final String className = getClass().getSimpleName();
        // Prima istanza della classe
        if (Objects.isNull(instance)) {
            instance = this;

            this.settingsHandler = new SettingsHandler();
            this.settingsHandler.loadSettings();
            this.acidRainHandler = new AcidRainHandler();
            this.purifyingConduitsList = new ArrayList<>();

            AcidIslandsMain.getInstance().getPlugin().getLogger().info(String.format("[ACIDISLANDS-RECODED] %s: Classe inizializzata!", className));
        } else {
            throw new IllegalAccessException(String.format("Non puoi creare un'altra istanza della classe %s. Per favore, usa il metodo ‘%s.getInstance()’.", className, className));
        }
    }

    public void loadPurifyingConduits() {
        this.purifyingConduitsList.clear();

        final FileConfiguration pcFile = FileManager.getFileManager(FileManager.FileType.CACHE_PURIFYINGCONDUIT).getYamlConfiguration();
        for (String conduitId : pcFile.getKeys(false))
            purifyingConduitsList.add((PurifyingConduit) pcFile.get(conduitId, PurifyingConduit.class));
    }

    public void addPurifyingConduit(PurifyingConduit conduit) {
        this.purifyingConduitsList.add(conduit);
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public AcidRainHandler getAcidRainHandler() {
        return acidRainHandler;
    }

    public static AcidIslandsAPI getInstance() {
        return instance;
    }
}
