package me.yoshiro09.acidislands.api.damage.types;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.damage.BaseDamage;
import me.yoshiro09.acidislands.api.damage.enums.DamageType;
import me.yoshiro09.acidislands.api.settings.SettingsHandler;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;

public class PoisonDamage extends BaseDamage {
    // private final int duration;
    // private final int level;
    private final List<String> levels;
    private long startTime;

    public PoisonDamage(Player player, DamageType type) {
        super(player, type);
        final SettingsHandler settingsHandler = AcidIslandsAPI.getInstance().getSettingsHandler();

        // this.duration = settingsHandler.getSettingAsInt(SettingsKey.DAMAGE_POISON_DURATION);
        // this.level = settingsHandler.getSettingAsInt(SettingsKey.DAMAGE_POISON_LEVEL);
        this.levels = settingsHandler.getSettingAsList(SettingsKey.DAMAGE_POISON_LEVELS);
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void hit() {
        final PotionEffect potion = getPotionEffect();
        if (Objects.isNull(potion)) return;

        if (player.getHealth() <= 0.5) player.damage(player.getHealth());
        else this.player.addPotionEffect(potion);
    }

    private PotionEffect getPotionEffect() {
        final int[] foundArgs = new int[]{-1, -1};
        int secondsPassed = (int) ((System.currentTimeMillis() - this.startTime) / 1000F);
        int diff = Integer.MAX_VALUE;

        for (String level : levels) {
            try {
                String[] args = level.split("/");
                int afterSeconds = Integer.parseInt(args[0]);
                int potionLevel = Integer.parseInt(args[1]);
                int potionDuration = Integer.parseInt(args[2]);

                // If che controlla se il livello che sta controllando è già passato o se è già stato trovato uno che si avvicina di più a quello richiesto dal tempo trascorso
                if (afterSeconds > secondsPassed || diff <= secondsPassed - afterSeconds) continue;

                diff = secondsPassed - afterSeconds;
                foundArgs[0] = potionDuration;
                foundArgs[1] = potionLevel;
            } catch (Exception ign) {}
        }

        return foundArgs[0] == -1 ? null : new PotionEffect(PotionEffectType.POISON, foundArgs[0] * 20, foundArgs[1]);
    }
}
