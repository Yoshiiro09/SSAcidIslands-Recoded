package me.yoshiro09.acidislands.api.mob;

import me.yoshiro09.acidislands.AcidIslandsMain;
import me.yoshiro09.acidislands.api.files.FileManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class AcidBuffMobManager {
    private final List<AcidBuffedMob> buffedMobList;

    public AcidBuffMobManager() {
        buffedMobList = new ArrayList<>();
    }

    public void loadSettings() {
        final ConfigurationSection buffedMobsConfig = FileManager.getFileManager(FileManager.FileType.CONFIG).getYamlConfiguration().getConfigurationSection("acid_buffed_mobs");
        final Logger logger = AcidIslandsMain.getInstance().getLogger();

        if (Objects.isNull(buffedMobsConfig)) return;

        for (String mobName : buffedMobsConfig.getKeys(false)) {
            EntityType entityType = null;
            try {
                entityType = EntityType.valueOf(mobName);
            } catch (IllegalArgumentException e) {}

            if (Objects.isNull(entityType)) {
                logger.warning(String.format("[ACIDISLANDS] [ACIDBUFFEDMOBS] Entità %s sconosciuta, il plugin lo ignorerà.", mobName));
                continue;
            }

            final ConfigurationSection mobConfig = buffedMobsConfig.getConfigurationSection(mobName);
            if (Objects.isNull(mobConfig)) {
                logger.info(String.format("[ACIDISLANDS] [ACIDBUFFEDMOBS] L'entità %s non ha la sua sezione nel config.", mobName));
                continue;
            }

            final List<AcidBuffedMob.Effect> effects = new ArrayList<>();
            final List<String> effectsList = mobConfig.getStringList("effects");

            for (String effectName : effectsList) {
                try {
                    final String[] splittedEffect = effectName.split(":");
                    final PotionEffectType effectType = PotionEffectType.getByName(splittedEffect[0]);
                    final int level = Integer.parseInt(splittedEffect[1]);

                    if (Objects.nonNull(effectType)) effects.add(new AcidBuffedMob.Effect(effectType, level));
                    else logger.warning(String.format("[ACIDISLANDS] [ACIDBUFFEDMOBS] Effetto %s sconosciuto per l'entità %s.", effectName, mobName));
                } catch (Exception e) {
                    logger.warning(String.format("[ACIDISLANDS] [ACIDBUFFEDMOBS] Errore nel parsing dell'effetto %s per l'entità %s: %s", effectName, mobName, e.getMessage()));
                }
            }
            buffedMobList.add(new AcidBuffedMob(entityType, effects));
        }
        logger.info(String.format("[ACIDISLANDS] [ACIDBUFFEDMOBS] Caricati %s mob potenziati.", buffedMobList.size()));
    }

    public AcidBuffedMob getBuffedMob(EntityType entityType) {
        System.out.println("Dimensioni lista mob: " + buffedMobList.size());
        for (final AcidBuffedMob buffedMob : buffedMobList) {
            System.out.println(String.format("%s - %s | Uguali: %s", entityType, buffedMob.getEntityType(), buffedMob.getEntityType().equals(entityType)));
            if (buffedMob.getEntityType().equals(entityType)) return buffedMob;
        }
        return null;
    }

    public List<AcidBuffedMob> getBuffedMobList() {
        return buffedMobList;
    }
}
