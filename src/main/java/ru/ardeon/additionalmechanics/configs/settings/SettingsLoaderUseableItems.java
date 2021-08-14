package ru.ardeon.additionalmechanics.configs.settings;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.configs.Configuration;
import ru.ardeon.additionalmechanics.configs.SettingsLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SettingsLoaderUseableItems implements SettingsLoader {
    public static final String fileName = "useableItems.yml";
    private Configuration configuration;

    @Override
    public void loadAndSetDefaultIfNotExists(FileConfiguration config) {
        for(SettingsLoaderUseableItems.SettingItems setting : SettingsLoaderUseableItems.SettingItems.values()){
            Object object = config.get(setting.path);
            if (object!=null){
                setting.value = object;
            } else {
                config.set(setting.path, setting.value);
            }
        }
        configuration.saveToFile();
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void save(FileConfiguration config) {
        for(SettingsLoaderUseableItems.SettingItems setting : SettingsLoaderUseableItems.SettingItems.values()){
            config.set(setting.path, setting.value);
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }



    public enum SettingItems{
        SNOWBALL_MATERIAL("items.snowball.material", "stick"),
        SNOWBALL_DAMAGE("items.snowball.damage", 2),
        SNOWBALL_COOLDOWN("items.snowball.cooldown", 20),
        SNOWBALL_SKILL_NAME("items.snowball.skillName", "arenaSnowball"),
        SNOWBALL_MODEL_DATA("items.snowball.modelData", 500),

        SNOW_WALL_MATERIAL("items.snowWall.material", "stick"),
        SNOW_WALL_COOLDOWN("items.snowWall.cooldown", 800),
        SNOW_WALL_SKILL_NAME("items.snowWall.skillName", "snowWall"),
        SNOW_WALL_MODEL_DATA("items.snowWall.modelData", 501),

        AGRO_MATERIAL("items.agro.material", "RED_DYE"),
        AGRO_COOLDOWN("items.agro.cooldown", 600),
        AGRO_SKILL_NAME("items.agro.skillName", "agro"),
        AGRO_MODEL_DATA("items.agro.modelData", 502),

        EXPLOSION_MATERIAL("items.explosion.material", "FIREWORK_STAR"),
        EXPLOSION_COOLDOWN("items.explosion.cooldown", 700),
        EXPLOSION_SKILL_NAME("items.explosion.skillName", "explosion"),
        EXPLOSION_MODEL_DATA("items.explosion.modelData", 503),
        EXPLOSION_POWER("items.explosion.power", 2),

        ARC_LIGHT_MATERIAL("items.arcLight.material", "stick"),
        ARC_LIGHT_COOLDOWN("items.arcLight.cooldown", 30),
        ARC_LIGHT_SKILL_NAME("items.arcLight.skillName", "arcLight"),
        ARC_LIGHT_MODEL_DATA("items.arcLight.modelData", 504),
        ARC_LIGHT_DAMAGE("items.arcLight.damage", 5.0d),
        ARC_LIGHT_BOUNCE("items.arcLight.bounce", 5),
        ARC_LIGHT_LENGTH("items.arcLight.length", 6.0d),
        ARC_LIGHT_RADIUS("items.arcLight.radius", 4.0d),

        FIREBALL_MATERIAL("items.fireball.material", "QUARTZ"),
        FIREBALL_COOLDOWN("items.fireball.cooldown", 40),
        FIREBALL_SKILL_NAME("items.fireball.skillName", "fireballWithEffect"),
        FIREBALL_MODEL_DATA("items.fireball.modelData", 505),

        RAGE_MATERIAL("items.rage.material", "BLACK_DYE"),
        RAGE_COOLDOWN("items.rage.cooldown", 400),
        RAGE_SKILL_NAME("items.rage.skillName", "rage"),
        RAGE_MODEL_DATA("items.rage.modelData", 506),

        SOUL_AGRO_MATERIAL("items.soulAgro.material", "BROWN_DYE"),
        SOUL_AGRO_COOLDOWN("items.soulAgro.cooldown", 400),
        SOUL_AGRO_SKILL_NAME("items.soulAgro.skillName", "soulAgro"),
        SOUL_AGRO_MODEL_DATA("items.soulAgro.modelData", 507),

        SCARECROW_MATERIAL("items.scarecrow.material", "PLAYER_HEAD"),
        SCARECROW_COOLDOWN("items.scarecrow.cooldown", 400),
        SCARECROW_SKILL_NAME("items.scarecrow.skillName", "scarecrow"),
        SCARECROW_MODEL_DATA("items.scarecrow.modelData", 508)
        ;

        private final String path;
        private Object value;
        private static Map<String, UseableItem> items = new HashMap<>();

        public static void updateItems(){
            SettingItems.items.clear();
            SettingItems.items.put("snowball", new SettingItems
                    .UseableItem(SettingItems.SNOWBALL_MATERIAL, SettingItems.SNOWBALL_SKILL_NAME, SettingItems.SNOWBALL_MODEL_DATA));
            SettingItems.items.put("snowWall", new SettingItems
                    .UseableItem(SettingItems.SNOW_WALL_MATERIAL, SettingItems.SNOW_WALL_SKILL_NAME, SettingItems.SNOW_WALL_MODEL_DATA));
            SettingItems.items.put("arcLight", new SettingItems
                    .UseableItem(SettingItems.ARC_LIGHT_MATERIAL, SettingItems.ARC_LIGHT_SKILL_NAME, SettingItems.ARC_LIGHT_MODEL_DATA));
            SettingItems.items.put("agro", new SettingItems
                    .UseableItem(SettingItems.AGRO_MATERIAL, SettingItems.AGRO_SKILL_NAME, SettingItems.AGRO_MODEL_DATA));
            SettingItems.items.put("explosion", new SettingItems
                    .UseableItem(SettingItems.EXPLOSION_MATERIAL, SettingItems.EXPLOSION_SKILL_NAME, SettingItems.EXPLOSION_MODEL_DATA));
            SettingItems.items.put("fireballWithEffect", new SettingItems
                    .UseableItem(SettingItems.FIREBALL_MATERIAL, SettingItems.FIREBALL_SKILL_NAME, SettingItems.FIREBALL_MODEL_DATA));
            SettingItems.items.put("rage", new SettingItems
                    .UseableItem(SettingItems.RAGE_MATERIAL, SettingItems.RAGE_SKILL_NAME, SettingItems.RAGE_MODEL_DATA));
            SettingItems.items.put("scarecrow", new SettingItems
                    .UseableItem(SettingItems.SCARECROW_MATERIAL, SettingItems.SCARECROW_SKILL_NAME, SettingItems.SCARECROW_MODEL_DATA));
            SettingItems.items.put("soulAgro", new SettingItems
                    .UseableItem(SettingItems.SOUL_AGRO_MATERIAL, SettingItems.SOUL_AGRO_SKILL_NAME, SettingItems.SOUL_AGRO_MODEL_DATA));
        }

        public static Set<String> getItems() {
            if (items.isEmpty()){
                updateItems();
            }
            return items.keySet();
        }

        public static Material getMaterialOfItem(String ItemName) {
            if (items.isEmpty()){
                updateItems();
            }
            return items.get(ItemName).material.getMaterial();
        }

        public static int getModelOfItem(String ItemName) {
            if (items.isEmpty()){
                updateItems();
            }
            return items.get(ItemName).modelData.getInt();
        }

        public static String getSkillNameOfItem(String ItemName) {
            if (items.isEmpty()){
                updateItems();
            }
            return items.get(ItemName).skillName.getString();
        }

        private static class UseableItem{
            SettingItems material, skillName, modelData;

            public UseableItem(SettingItems material, SettingItems skillName, SettingItems modelData){
                this.material = material;
                this.modelData = modelData;
                this.skillName = skillName;
            }
        }

        SettingItems(String path, Object value) {
            this.path = path;
            this.value = value;
        }

        public boolean getBool() {
            try {
                return (boolean) value;
            } catch (ClassCastException e){
                return false;
            }
        }

        public String getString() {
            return value.toString();
        }

        public int getInt() {
            try {
                return (int) value;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        public double getDouble() {
            try {
                return (double) value;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        public Material getMaterial() {
            try {
                return Material.valueOf(value.toString().toUpperCase());
            } catch (IllegalArgumentException e) {
                AdditionalMechanics.getLoggerADM().warn("Материал установлен неправильно " + path);
            }
            return Material.STONE;
        }
    }
}