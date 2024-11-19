package me.yoshiro09.acidislands.listeners;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.events.AcidRainStartEvent;
import me.yoshiro09.acidislands.api.mob.AcidBuffMobManager;
import me.yoshiro09.acidislands.api.mob.AcidBuffedMob;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.Objects;

public class BuffedMobsListener implements Listener {

    @EventHandler
    public void onAcidRainStart(AcidRainStartEvent event) {
        final World world = event.getWorld();
        System.out.println(world.getName());
        final AcidBuffMobManager acidBuffMobManager = AcidIslandsAPI.getInstance().getAcidBuffMobManager();

        List<Entity> entities = world.getEntities();
        entities.stream().map(Entity::getType).forEach(System.out::println);
        Bukkit.getScheduler().runTaskAsynchronously(AcidIslandsMain.getInstance().getPlugin(), () -> {
            for (Entity entity : entities) {
                System.out.println("\n\n");
                if (!(entity instanceof LivingEntity)) continue;
                System.out.println(entity.getType() + " è un'entità vivente.");
                final AcidBuffedMob buffedMob = acidBuffMobManager.getBuffedMob(entity.getType());
                if (Objects.isNull(buffedMob)) continue;
                System.out.println(entity.getType() + " è un'entità da potenziare.");
                Bukkit.getScheduler().runTask(AcidIslandsMain.getInstance().getPlugin(), () -> buffedMob.applyEffectsIfMatch(entity));
            }
        });
    }
}
