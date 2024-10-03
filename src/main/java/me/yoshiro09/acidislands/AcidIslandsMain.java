package me.yoshiro09.acidislands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.api.files.FileManager;
import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import me.yoshiro09.acidislands.listeners.AcidRainListener;
import me.yoshiro09.acidislands.listeners.PurifyingConduitListener;
import me.yoshiro09.acidislands.listeners.TouchedWaterListener;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AcidIslandsMain extends PluginModule {
    private static AcidIslandsMain instance;
    private JavaPlugin plugin;

    static {
        ConfigurationSerialization.registerClass(PurifyingConduit.class, "PurifyingConduit");
    }

    public AcidIslandsMain() {
        super("acidislands", "Yoshiiro_");
        instance = this;
    }

    @Override
    public void onEnable(SuperiorSkyblock superiorSkyblock) {
        this.plugin = (JavaPlugin) superiorSkyblock;
        try {
            FileManager.loadFiles();
            final AcidIslandsAPI api = new AcidIslandsAPI();
            api.loadPurifyingConduits();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onReload(SuperiorSkyblock superiorSkyblock) {}

    @Override
    public void onDisable(SuperiorSkyblock superiorSkyblock) {}

    @Override
    public Listener[] getModuleListeners(SuperiorSkyblock superiorSkyblock) {
        return new Listener[]{new TouchedWaterListener(), new AcidRainListener(), new PurifyingConduitListener()};
    }

    @Override
    public SuperiorCommand[] getSuperiorCommands(SuperiorSkyblock superiorSkyblock) {
        return new SuperiorCommand[0];
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands(SuperiorSkyblock superiorSkyblock) {
        return new SuperiorCommand[0];
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public static AcidIslandsMain getInstance() {
        return instance;
    }
}
