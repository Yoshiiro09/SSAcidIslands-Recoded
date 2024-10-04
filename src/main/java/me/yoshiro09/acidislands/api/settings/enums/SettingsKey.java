package me.yoshiro09.acidislands.api.settings.enums;

public enum SettingsKey {
    DAMAGE_TYPE("damage.type", "DEFAULT"),
    DAMAGE_DEFAULT_FIRSTDAMAGE("damage.default.first-damage", "0.5"),
    DAMAGE_DEFAULT_MULTIPLIER("damage.default.multiplier", "1.2"),
    DAMAGE_POISON_LEVELS("damage.poison.levels", "0/1/5"),

    ACIDRAIN_WORLD("rain.world", "Isola"),
    ACIDRAIN_RARITY_PERCENTAGE("rain.rarity-percentage", "0.6"),
    ACIDRAIN_MSG_STARTED("rain.messages.acidrain-started", "&e&lAcidIsland | &7Una pioggia acida è iniziata nel mondo &fIsola&7. Mettiti al riparo prima che sia troppo tardi."),
    ACIDRAIN_MSG_ENDED("rain.messages.acidrain-ended", "&e&lAcidIsland | &7La pioggia acida nel mondo &fIsola &7è finalmente terminata."),

    PURIFYINGCONDUITS_MSG_ACTBAR_IN_PURIFIED_WATER("purifyngconduits.messages.actbar-in-purified-water", "&7L'&fAcqua &7intorno a te è stata purificata."),
    PURIFYINGCONDUITS_MSG_PLACE_ONLY_IN_ISLAND("purifyngconduits.messages.place-only-in-island", "&7L'&fAcqua &7intorno a te è stata purificata."),
    ;

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
