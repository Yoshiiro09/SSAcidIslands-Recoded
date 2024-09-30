package me.yoshiro09.acidislands.listeners;

import me.yoshiro09.acidislands.api.damage.task.AcidDamageTask;
import me.yoshiro09.acidislands.utils.PlayerUtils;
import org.bukkit.GameMode;
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
    public void onPlayerMove(PlayerMoveEvent e) {
        final Location from = e.getFrom();
        final Location to = e.getTo();

        if (Objects.isNull(to) || PlayerUtils.isSameLocation(from, to) || !PlayerUtils.canTakeDamage(e.getPlayer())) return;

        checkForWater(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!PlayerUtils.canTakeDamage(e.getPlayer())) return;
        checkForWater(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e) {
        AcidDamageTask.stopTask(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent e) {
        AcidDamageTask.stopTask(e.getEntity());
    }

    private void checkForWater(Player player) {
        if (PlayerUtils.isInWater(player)) {
            AcidDamageTask.createTask(player);
        }
    }

}

