package me.yoshiro09.acidislands.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.gui.UpgradeGUI;
import me.yoshiro09.acidislands.api.gui.base.player.PlayerCache;
import me.yoshiro09.acidislands.api.gui.base.player.PlayerCacheManager;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import me.yoshiro09.acidislands.utils.MessagesSender;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PurifyingConduitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onConduitPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.CONDUIT) return;

        final AcidIslandsAPI api = AcidIslandsAPI.getInstance();
        final Island island = SuperiorSkyblockAPI.getGrid().getIslandAt(event.getBlock().getLocation());
        if (Objects.isNull(island)) {
            if (!event.getPlayer().hasPermission("acidislands.purifyingconduit.bypass-place")) {
                event.setCancelled(true);
                MessagesSender.sendMessage(event.getPlayer(), AcidIslandsAPI.getInstance().getSettingsHandler().getSetting(SettingsKey.PURIFYINGCONDUITS_MSG_PLACE_ONLY_IN_ISLAND), event.getPlayer());
            }
            return;
        }

        if (!island.isMember(SuperiorSkyblockAPI.getPlayer(event.getPlayer()))) {
            MessagesSender.sendMessage(event.getPlayer(), api.getSettingsHandler().getSetting(SettingsKey.PURIFYINGCONDUITS_MSG_CANT_INTERACT_OTHER_ISLANDS), event.getPlayer());
            event.setCancelled(true);
            return;
        }

        final PurifyingConduit purifyingConduit = new PurifyingConduit(null, island.getUniqueId(), event.getBlock().getLocation(), 1);
        purifyingConduit.save();
        api.getPurifyingConduitManager().addPurifyingConduit(purifyingConduit);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onConduitBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.CONDUIT) return;

        final AcidIslandsAPI api = AcidIslandsAPI.getInstance();
        final Island island = SuperiorSkyblockAPI.getGrid().getIslandAt(event.getBlock().getLocation());
        if (Objects.nonNull(island) && !island.isMember(SuperiorSkyblockAPI.getPlayer(event.getPlayer()))) {
            MessagesSender.sendMessage(event.getPlayer(), api.getSettingsHandler().getSetting(SettingsKey.PURIFYINGCONDUITS_MSG_CANT_INTERACT_OTHER_ISLANDS), event.getPlayer());
            event.setCancelled(true);
            return;
        }

        final PurifyingConduit purifyingConduit = api.getPurifyingConduitManager().removePurifyingConduitFromLocation(event.getBlock().getLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onConduitInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock().getType() != Material.CONDUIT) return;

        final AcidIslandsAPI api = AcidIslandsAPI.getInstance();
        final PurifyingConduit pc = api.getPurifyingConduitManager().getPurifyingConduitFromLocation(event.getClickedBlock().getLocation());
        if (Objects.isNull(pc)) return;

        final PlayerCache playerCache = PlayerCacheManager.getPlayerCache(event.getPlayer());
        playerCache.setEditingPurifyingConduit(pc);
        new UpgradeGUI(playerCache).open();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onIslandDelete(IslandDisbandEvent event) {
        AcidIslandsAPI.getInstance().getPurifyingConduitManager().removePurifyingConduitsInIsland(event.getIsland().getUniqueId());
    }
}
