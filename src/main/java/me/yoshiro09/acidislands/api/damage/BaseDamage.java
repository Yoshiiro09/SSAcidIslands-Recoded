package me.yoshiro09.acidislands.api.damage;

import me.yoshiro09.acidislands.api.damage.enums.DamageType;
import org.bukkit.entity.Player;

public abstract class BaseDamage {
    protected final Player player;
    protected final DamageType type;

    public BaseDamage(Player player, DamageType type) {
        this.player = player;
        this.type = type;
    }

    public abstract void hit();
    public abstract void reset();
}
