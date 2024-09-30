package me.yoshiro09.acidislands.api.damage.types;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.damage.BaseDamage;
import me.yoshiro09.acidislands.api.damage.enums.DamageType;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import org.bukkit.entity.Player;

public class DefaultDamage extends BaseDamage {
    private final double damageMultiplier;
    private double lastDamage;

    public DefaultDamage(Player player, DamageType type) {
        super(player, type);
        final SettingsHandler settingsHandler = AcidIslandsAPI.getInstance().getSettingsHandler();

        this.damageMultiplier = settingsHandler.getSettingAsDouble(SettingsKey.DAMAGE_DEFAULT_MULTIPLIER);
        this.lastDamage = settingsHandler.getSettingAsDouble(SettingsKey.DAMAGE_DEFAULT_FIRSTDAMAGE);
    }

    @Override
    public void hit() {
        this.player.damage(this.lastDamage);
        this.lastDamage *= this.damageMultiplier;
    }
}
