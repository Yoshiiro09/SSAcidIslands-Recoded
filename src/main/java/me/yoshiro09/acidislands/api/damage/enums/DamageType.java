package me.yoshiro09.acidislands.api.damage.enums;

import me.yoshiro09.acidislands.api.damage.BaseDamage;
import me.yoshiro09.acidislands.api.damage.types.DefaultDamage;
import me.yoshiro09.acidislands.api.damage.types.NoneDamage;
import me.yoshiro09.acidislands.api.damage.types.PoisonDamage;
import org.bukkit.entity.Player;

import java.util.Objects;

public enum DamageType {
    DEFAULT, POISON, NONE;

    public static BaseDamage getBaseDamage(Player player, String damageType) {
        if (Objects.isNull(damageType)) return new DefaultDamage(player, DEFAULT);

        switch (damageType.toUpperCase()) {
            case "POISON": return new PoisonDamage(player, POISON);
            case "NONE": return new NoneDamage(player, NONE);
            default: return new DefaultDamage(player, DEFAULT);
        }
    }
}
