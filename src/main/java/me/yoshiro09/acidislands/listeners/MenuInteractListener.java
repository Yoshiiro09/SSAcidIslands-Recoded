package me.yoshiro09.acidislands.listeners;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.gui.base.AMenu;
import me.yoshiro09.acidislands.api.gui.base.player.PlayerCacheManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuInteractListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder(false);

        if (!(holder instanceof AMenu)) return;
        AMenu menu = (AMenu) holder;
        menu.handleMenu(event);
        updateInventory((Player) event.getWhoClicked());
    }

    @EventHandler
    public void onMenuDrag(InventoryDragEvent event) {
        final InventoryHolder holder = event.getView().getTopInventory().getHolder(false);

        if (!(holder instanceof AMenu)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder(false);

        if (!(holder instanceof AMenu)) return;
        AMenu menu = (AMenu) holder;
        menu.handleClose(event);
        updateInventory((Player) event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerCacheManager.removePlayerCache(event.getPlayer());
    }

    private void updateInventory(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(AcidIslandsMain.getInstance().getPlugin(), () -> player.updateInventory(), 1L);
    }
}
