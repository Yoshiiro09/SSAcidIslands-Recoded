package me.yoshiro09.acidislands.api.damage.task;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.damage.BaseDamage;
import me.yoshiro09.acidislands.api.damage.enums.DamageType;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import me.yoshiro09.acidislands.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class AcidDamageTask extends BukkitRunnable {
    private static final Map<UUID, AcidDamageTask> acidDamageTasks = new HashMap<>();

    private final Player player;
    private double lastDamage;
    private final AcidIslandsAPI api;
    private final BaseDamage damageHandler;

    private AcidDamageTask(Player player) {
        this.player = player;
        this.api = AcidIslandsAPI.getInstance();
        this.lastDamage = api.getSettingsHandler().getSettingAsDouble(SettingsKey.DAMAGE_DEFAULT_FIRSTDAMAGE);
        this.damageHandler = DamageType.getBaseDamage(player, api.getSettingsHandler().getSetting(SettingsKey.DAMAGE_TYPE));

        runTaskTimer(AcidIslandsMain.getInstance().getPlugin(), 20L, 20L);
    }

    @Override
    public void run() {
        if (!player.isOnline() || !PlayerUtils.canTakeDamage(player) || !PlayerUtils.isInWater(player)) {
            stopTask(player);
            return;
        }

        damageHandler.hit();
    }

    public static Optional<AcidDamageTask> getTask(Player player) {
        return Optional.ofNullable(acidDamageTasks.get(player.getUniqueId()));
    }

    public static void stopTask(Player player) {
        AcidDamageTask acidDamageTask = acidDamageTasks.remove(player.getUniqueId());
        if (Objects.nonNull(acidDamageTask)) acidDamageTask.cancel();
    }

    public static AcidDamageTask createTask(Player player) {
        return acidDamageTasks.computeIfAbsent(player.getUniqueId(), u -> new AcidDamageTask(player));
    }

}
