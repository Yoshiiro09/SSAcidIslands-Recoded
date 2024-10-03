package me.yoshiro09.acidislands.listeners;

import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PurifyingConduitListener implements Listener {

    @EventHandler
    public void onConduitPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.CONDUIT) return;

        final PurifyingConduit purifyingConduit = new PurifyingConduit(null, event.getPlayer().getUniqueId(), event.getBlock().getLocation(), 1);
        purifyingConduit.save();
        AcidIslandsAPI.getInstance().addPurifyingConduit(purifyingConduit);
        System.out.println(String.format("DEBUG-0: %s", purifyingConduit));
    }
}
