package me.yoshiro09.acidislands.api.damage.types;

import me.yoshiro09.acidislands.api.damage.BaseDamage;
import me.yoshiro09.acidislands.api.damage.enums.DamageType;
import org.bukkit.entity.Player;

public class NoneDamage extends BaseDamage {
    public NoneDamage(Player player, DamageType type) {
        super(player, type);
    }

    @Override
    public void hit() {
    }

    @Override
    public void reset() {}
}
