package me.yoshiro09.acidislands.api.files;

import me.yoshiro09.acidislands.AcidIslandsMain;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private static final Map<FileType, FileManager> files = new HashMap<>();
    private final String filePath;
    private final File file;
    private YamlConfiguration yamlConfiguration;
    public FileManager(String filePath) {
        this.filePath = filePath;
        this.file = new File(AcidIslandsMain.getInstance().getModuleFolder(), filePath);
    }

    public static void loadFiles() {
        for (FileType type : FileType.values())
            files.put(type, new FileManager(type.getPath()).loadConfiguration());
    }

    public static FileManager getFileManager(FileType type) {
        return files.get(type);
    }

    public FileManager loadConfiguration() {
        final AcidIslandsMain module = AcidIslandsMain.getInstance();
        if (!file.exists()) module.saveResource(filePath);
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        return this;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public void save() {
        try {
            yamlConfiguration.save(file);
            yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum FileType {
        CONFIG("config.yml"), CACHE_PURIFYINGCONDUIT("cache/purifyingConduit.yml");

        private final String path;

        FileType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
