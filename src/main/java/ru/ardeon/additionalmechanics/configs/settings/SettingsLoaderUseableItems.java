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
        SNOWBALL_DAMAGE5("items.snowball.damage", 2),
        SNOWBALL_DAMAGE8("items.snowball.damage", 2),
        SNOWBALL_DAMAGE9("items.snowball.damage", 2),
        SNOWBALL_DAMAGE54("items.snowball.damage", 2)
        ;

        private final String path;
        private Object value;
        private static Map<String, UseableItem> items = new HashMap<>();

        public static void updateItems(){
            SettingItems.items.clear();
            SettingItems.items.put("snowball", new SettingItems
                    .UseableItem(SettingItems.SNOWBALL_MATERIAL, SettingItems.SNOWBALL_SKILL_NAME, SettingItems.SNOWBALL_MODEL_DATA));
        }

        public static Set<String> getItems() {
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