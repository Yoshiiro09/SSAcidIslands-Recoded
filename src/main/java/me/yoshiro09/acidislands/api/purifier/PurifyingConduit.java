package me.yoshiro09.acidislands.api.purifier;

import me.yoshiro09.acidislands.api.files.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Conduit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SerializableAs("PurifyingConduit")
public class PurifyingConduit implements ConfigurationSerializable {
    private final UUID uid;
    private final UUID islandId;
    private final int x, y, z;
    private final String world;
    private final int level;

    public PurifyingConduit(UUID uid, UUID islandId, Location location, int level) {
        this.uid = Objects.isNull(uid) ? UUID.randomUUID() : uid;
        this.islandId = islandId;
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld().getName();
        this.level = level;
    }

    public PurifyingConduit(UUID uid, UUID islandId, String world, int x, int y, int z, int level) {
        this.uid = Objects.isNull(uid) ? UUID.randomUUID() : uid;
        this.islandId = islandId;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Location getLocation() {
        final World worldObj = Bukkit.getWorld(world);
        if (Objects.isNull(worldObj)) return null;
        return new Location(worldObj, this.x, this.y, this.z);
    }

    public UUID getIslandId() {
        return islandId;
    }

    public boolean isSameWorld(Location location) {
        return location.getWorld().getName().equals(world);
    }

    public boolean isSameLocation(Location location) {
        return location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z && location.getWorld().getName().equals(world);
    }

    public boolean isActive() {
        final Location location = getLocation();
        if (Objects.isNull(location)) return false;

        final Block block = location.getBlock();
        if (!(block.getState() instanceof Conduit)) return false;
        final Conduit conduit = (Conduit) block.getState();

        return conduit.isActive();
    }

    @Override
    public String toString() {
        return String.format("{\"uid\":\"%s\", \"islandId\":\"%s\", \"world\":\"%s\", \"x\":\"%s\", \"y\":\"%s\", \"z\":\"%s\", \"level\":%d}", this.uid, this.islandId, this.world, this.x, this.y, this.z, this.level);
    }

    public void delete() {
        final FileManager cache = FileManager.getFileManager(FileManager.FileType.CACHE_PURIFYINGCONDUIT);
        cache.getYamlConfiguration().set(this.uid.toString(), null);
        cache.save();
    }

    public void save() {
        final FileManager cache = FileManager.getFileManager(FileManager.FileType.CACHE_PURIFYINGCONDUIT);
        cache.getYamlConfiguration().set(this.uid.toString(), this);
        cache.save();
    }

    public static PurifyingConduit deserialize(Map<String, Object> map) {
        final UUID uid = UUID.fromString((String) map.get("uid"));
        final UUID islandId = UUID.fromString((String) map.get("islandId"));
        final String world = (String) map.get("world");
        final int x = (int) map.get("x");
        final int y = (int) map.get("y");
        final int z = (int) map.get("z");
        final int level = (int) map.get("level");

        return new PurifyingConduit(uid, islandId, world, x, y, z, level);
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new HashMap<>();
        result.put("uid", this.uid.toString());
        result.put("islandId", this.islandId.toString());
        result.put("world", this.world);
        result.put("x", this.x);
        result.put("y", this.y);
        result.put("z", this.z);
        result.put("level", this.level);

        return result;
    }
}
