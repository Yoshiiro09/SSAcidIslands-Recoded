package me.yoshiro09.acidislands.api.damage.types;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.damage.BaseDamage;
import me.yoshiro09.acidislands.api.damage.enums.DamageType;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonDamage extends BaseDamage {
    private final int duration;
    private final int level;

    public PoisonDamage(Player player, DamageType type) {
        super(player, type);
        final SettingsHandler settingsHandler = AcidIslandsAPI.getInstance().getSettingsHandler();

        this.duration = settingsHandler.getSettingAsInt(SettingsKey.DAMAGE_POISON_DURATION);
        this.level = settingsHandler.getSettingAsInt(SettingsKey.DAMAGE_POISON_LEVEL);
    }

    @Override
    public void hit() {
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, this.duration, this.level));
    }
}
