package me.yoshiro09.acidislands.api.mob;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AcidBuffedMob {
    private final EntityType entityType;
    private final List<Effect> effects;

    public AcidBuffedMob(EntityType entityType, List<Effect> effects) {
        this.entityType = entityType;
        this.effects = effects;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void applyEffectsIfMatch(Entity entity) {
        if (entity.getType() != this.entityType || !(entity instanceof LivingEntity)) return;

        LivingEntity livingEntity = (LivingEntity) entity;
        for (Effect effect : effects) livingEntity.addPotionEffect(new PotionEffect(effect.getType(), Integer.MAX_VALUE, effect.getLevel()));
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 1));
    }

    public static class Effect {
        private final PotionEffectType type;
        private final int level;

        public Effect(PotionEffectType type, int level) {
            this.type = type;
            this.level = level;
        }

        public PotionEffectType getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }
    }
}
