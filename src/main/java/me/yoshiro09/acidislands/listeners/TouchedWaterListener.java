package me.yoshiro09.acidislands.listeners;

import me.yoshiro09.acidislands.api.damage.task.AcidDamageTask;
import me.yoshiro09.acidislands.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public final class TouchedWaterListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();

        if (Objects.isNull(to) || PlayerUtils.isSameLocation(from, to) || !PlayerUtils.canTakeDamage(event.getPlayer())) return;

        checkForWater(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!PlayerUtils.canTakeDamage(event.getPlayer())) return;
        checkForWater(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event) {
        AcidDamageTask.stopTask(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        AcidDamageTask.stopTask(event.getEntity());
    }

    private void checkForWater(Player player) {
        if (PlayerUtils.isInWater(player) || PlayerUtils.isInRain(player)) AcidDamageTask.createTask(player);
    }

}

