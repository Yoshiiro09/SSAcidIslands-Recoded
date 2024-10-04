package me.yoshiro09.acidislands.listeners;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.island.Island;
import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import me.yoshiro09.acidislands.api.settings.enums.SettingsKey;
import me.yoshiro09.acidislands.utils.MessagesSender;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class PurifyingConduitListener implements Listener {

    @EventHandler
    public void onConduitPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.CONDUIT) return;

        final Island island = SuperiorSkyblockAPI.getGrid().getIslandAt(event.getBlock().getLocation());
        if (Objects.isNull(island)) {
            if (!event.getPlayer().hasPermission("acidislands.purifyingconduit.bypass-place")) {
                event.setCancelled(true);
                MessagesSender.sendMessage(event.getPlayer(), AcidIslandsAPI.getInstance().getSettingsHandler().getSetting(SettingsKey.PURIFYINGCONDUITS_MSG_PLACE_ONLY_IN_ISLAND));
            }
            return;
        }

        final PurifyingConduit purifyingConduit = new PurifyingConduit(null, island.getUniqueId(), event.getBlock().getLocation(), 1);
        purifyingConduit.save();
        AcidIslandsAPI.getInstance().getPurifyingConduitManager().addPurifyingConduit(purifyingConduit);
        System.out.printf("DEBUG-0: %s%n", purifyingConduit);
    }

    @EventHandler
    public void onConduitBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.CONDUIT) return;
        final PurifyingConduit purifyingConduit = AcidIslandsAPI.getInstance().getPurifyingConduitManager().removePurifyingConduitFromLocation(event.getBlock().getLocation());
    }

    @EventHandler
    public void onIslandDelete(IslandDisbandEvent event) {
        AcidIslandsAPI.getInstance().getPurifyingConduitManager().removePurifyingConduitsInIsland(event.getIsland().getUniqueId());
    }
}
