package me.yoshiro09.acidislands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import me.yoshiro09.acidislands.api.AcidIslandsAPI;
import me.yoshiro09.acidislands.listeners.TouchedWaterListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AcidIslandsMain extends PluginModule {
    private static AcidIslandsMain instance;
    private JavaPlugin plugin;

    public AcidIslandsMain() {
        super("acidislands", "Yoshiiro_");
        instance = this;
    }

    @Override
    public void onEnable(SuperiorSkyblock superiorSkyblock) {
        this.plugin = (JavaPlugin) superiorSkyblock;
        try {
            new AcidIslandsAPI().loadSettings();
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
        return new Listener[]{new TouchedWaterListener()};
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
