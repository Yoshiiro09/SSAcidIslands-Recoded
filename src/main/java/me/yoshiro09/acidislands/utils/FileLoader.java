package me.yoshiro09.acidislands.utils;

import me.yoshiro09.acidislands.AcidIslandsMain;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileLoader {

    public static YamlConfiguration getFile(String fileName) {
        final AcidIslandsMain module = AcidIslandsMain.getInstance();
        File file = new File(module.getModuleFolder(), fileName);
        if(!file.exists())  module.saveResource(fileName);
        return YamlConfiguration.loadConfiguration(file);
    }

}
