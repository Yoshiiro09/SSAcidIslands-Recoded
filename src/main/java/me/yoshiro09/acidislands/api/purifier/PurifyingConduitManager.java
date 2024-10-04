package me.yoshiro09.acidislands.api.purifier;

import me.yoshiro09.acidislands.api.files.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PurifyingConduitManager {
    private final List<PurifyingConduit> purifyingConduitsList;
    private final List<PurifyingConduitLevel> purifyingConduitLevels;

    public PurifyingConduitManager() {
        this.purifyingConduitsList = new ArrayList<>();
        this.purifyingConduitLevels = new ArrayList<>();
    }

    public void loadPurifyingConduits() {
        this.purifyingConduitsList.clear();

        final FileConfiguration pcFile = FileManager.getFileManager(FileManager.FileType.CACHE_PURIFYINGCONDUIT).getYamlConfiguration();
        for (String conduitId : pcFile.getKeys(false))
            purifyingConduitsList.add((PurifyingConduit) pcFile.get(conduitId, PurifyingConduit.class));
    }

    public void addPurifyingConduit(PurifyingConduit conduit) {
        this.purifyingConduitsList.add(conduit);
    }

    public List<PurifyingConduit> removePurifyingConduitsInIsland(UUID islandId) {
        List<PurifyingConduit> conduitsToRemove = this.purifyingConduitsList.stream()
                .filter(pc -> pc.getIslandId().equals(islandId))
                .collect(Collectors.toList());
        conduitsToRemove.forEach(PurifyingConduit::delete);
        this.purifyingConduitsList.removeAll(conduitsToRemove);
        return conduitsToRemove;
    }

    public PurifyingConduit removePurifyingConduitFromLocation(Location location) {
        Iterator<PurifyingConduit> iterator = this.purifyingConduitsList.iterator();
        while (iterator.hasNext()) {
            PurifyingConduit pc = iterator.next();
            if (!pc.isSameLocation(location)) continue;

            pc.delete();
            iterator.remove();
            return pc;
        }
        return null;
    }

    public boolean isPlayerInPurifyingConduitRange(Player player) {
        for (PurifyingConduit pc : this.purifyingConduitsList) {
            if (!pc.isSameWorld(player.getLocation()) || !pc.isActive()) continue;

            final Location pcLocation = pc.getLocation();
            final PurifyingConduitLevel pcl = this.getPurifyingConduitLevel(pc.getLevel());
            if (Objects.isNull(pcLocation) || Objects.isNull(pcl)) continue;

            if (pcLocation.distance(player.getLocation()) <= pcl.getRange()) return true;
        }
        return false;
    }

    public void loadPurifyingConduitLevels() {
        this.purifyingConduitLevels.clear();

        final ConfigurationSection configFile = FileManager.getFileManager(FileManager.FileType.CONFIG).getYamlConfiguration().getConfigurationSection("purifyngconduits.levels");
        for (String strLevel : configFile.getKeys(false)) {
            try {
                final int level = Integer.parseInt(strLevel);
                final int range = configFile.getInt(String.format("%s.range", strLevel));
                final int price = configFile.getInt(String.format("%s.price", strLevel));

                this.purifyingConduitLevels.add(new PurifyingConduitLevel(level, range, price));
            } catch (Exception ign) {}
        }
    }

    public PurifyingConduitLevel getPurifyingConduitLevel(int level) {
        for (PurifyingConduitLevel pcl : this.purifyingConduitLevels) if (pcl.getLevel() == level) return pcl;
        return null;
    }
}
