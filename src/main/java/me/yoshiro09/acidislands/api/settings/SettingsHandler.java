package me.yoshiro09.acidislands.api.settings;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SettingsHandler {
    private final static String CONFIG_FILE_NAME = "config.yml";
    private final Map<SettingsKey, String> settings;

    public SettingsHandler() {
        this.settings = new HashMap<>();
    }

    public void loadSettings() {
        settings.clear();

        final AcidIslandsMain module = AcidIslandsMain.getInstance();
        final JavaPlugin plugin = module.getPlugin();
        YamlConfiguration config = getConfigFile(module);

        for (SettingsKey key : SettingsKey.values()) {
            final String value = config.getString(key.getPath());
            // Impostazione non trovata nel file di configurazione, procede a caricare il valore predefinito.
            if (Objects.isNull(value)) {
                this.settings.put(key, key.getDefaultValue());
                plugin.getLogger().warning(String.format("[ACIDISLANDS-RECODED] %s: Impostazione non trovata nel config [%s]. Sto per usare il suo valore predefinito. [%s]", key, key.getPath(), key.getDefaultValue()));
                continue;
            }
            this.settings.put(key, value);
        }

        plugin.getLogger().info(String.format("[ACIDISLANDS-RECODED] Caricate %s impostazioni.", this.settings.size()));
    }

    private YamlConfiguration getConfigFile(AcidIslandsMain module) {
        File file = new File(module.getModuleFolder(), CONFIG_FILE_NAME);
        if(!file.exists())  module.saveResource(CONFIG_FILE_NAME);
        return YamlConfiguration.loadConfiguration(file);
    }

    public String getSetting(SettingsKey key) {
        return this.settings.get(key);
    }

    public int getSettingAsInt(SettingsKey key) {
        return Integer.parseInt(this.settings.get(key));
    }

    public boolean getSettingAsBoolean(SettingsKey key) {
        return Boolean.parseBoolean(this.settings.get(key));
    }

    public double getSettingAsDouble(SettingsKey key) {
        return Double.parseDouble(this.settings.get(key));
    }

    public float getSettingAsFloat(SettingsKey key) {
        return Float.parseFloat(this.settings.get(key));
    }

    public long getSettingAsLong(SettingsKey key) {
        return Long.parseLong(this.settings.get(key));
    }
}
