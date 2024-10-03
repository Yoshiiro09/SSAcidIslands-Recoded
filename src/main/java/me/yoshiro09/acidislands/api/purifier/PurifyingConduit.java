package me.yoshiro09.acidislands.api.purifier;

import me.yoshiro09.acidislands.api.files.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SerializableAs("PurifyingConduit")
public class PurifyingConduit implements ConfigurationSerializable {
    private final UUID uuid;
    private final UUID owner;
    private final int x, y, z;
    private final String world;
    private int level;

    public PurifyingConduit(UUID uuid, UUID owner, Location location, int level) {
        this.uuid = Objects.isNull(uuid) ? UUID.randomUUID() : uuid;
        this.owner = owner;
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld().getName();
        this.level = level;
    }

    public PurifyingConduit(UUID uuid, UUID owner, String world, int x, int y, int z, int level) {
        this.uuid = Objects.isNull(uuid) ? UUID.randomUUID() : uuid;
        this.owner = owner;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.level = level;
    }

    public static PurifyingConduit deserialize(Map<String, Object> map) {
        final UUID uuid = UUID.fromString((String) map.get("uuid"));
        final UUID owner = UUID.fromString((String) map.get("owner"));
        final String world = (String) map.get("world");
        final int x = (int) map.get("x");
        final int y = (int) map.get("y");
        final int z = (int) map.get("z");
        final int level = (int) map.get("level");

        return new PurifyingConduit(uuid, owner, world, x, y, z, level);
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> result = new HashMap<>();
        result.put("uuid", this.uuid.toString());
        result.put("owner", this.owner.toString());
        result.put("world", this.world);
        result.put("x", this.x);
        result.put("y", this.y);
        result.put("z", this.z);
        result.put("level", this.level);

        return result;
    }

    @Override
    public String toString() {
        return String.format("{\"uuid\":\"%s\", \"owner\":\"%s\", \"world\":\"%s\", \"x\":\"%s\", \"y\":\"%s\", \"z\":\"%s\", \"level\":%d}", this.uuid, this.owner, this.world, this.x, this.y, this.z, this.level);
    }

    public void save() {
        final FileManager cache = FileManager.getFileManager(FileManager.FileType.CACHE_PURIFYINGCONDUIT);
        cache.getYamlConfiguration().set(this.uuid.toString(), this);
        cache.save();
    }
}
