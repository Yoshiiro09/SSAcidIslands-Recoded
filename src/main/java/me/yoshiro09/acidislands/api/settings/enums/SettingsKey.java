package me.yoshiro09.acidislands.api.settings.enums;

public enum SettingsKey {
    DAMAGE_TYPE("damage.type", "DEFAULT"),
    DAMAGE_DEFAULT_FIRSTDAMAGE("damage.default.first-damage", "0.5"),
    DAMAGE_DEFAULT_MULTIPLIER("damage.default.multiplier", "1.2"),
    DAMAGE_POISON_DURATION("damage.poison.duration", "200"),
    DAMAGE_POISON_LEVEL("damage.poison.level", "2"),

    ACIDRAIN_WORLD("rain.world", "Isola"),
    ACIDRAIN_RARITY_PERCENTAGE("rain.rarity-percentage", "0.6");

    final String defaultValue;
    final String path;

    SettingsKey(String path, String defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
