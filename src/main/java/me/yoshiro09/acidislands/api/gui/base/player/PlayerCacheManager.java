package me.yoshiro09.acidislands.api.gui.base.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerCacheManager {

    private static final List<PlayerCache> playerCaches = new ArrayList<PlayerCache>();

    public static PlayerCache getPlayerCache(Player player) {
        for (PlayerCache pc : playerCaches)
            if (pc.getOwner().getUniqueId().equals(player.getUniqueId())) return pc;
        final PlayerCache pc = new PlayerCache(player);
        playerCaches.add(pc);
        return pc;
    }

    public static void removePlayerCache(Player player) {
        playerCaches.remove(getPlayerCache(player));
    }
}
