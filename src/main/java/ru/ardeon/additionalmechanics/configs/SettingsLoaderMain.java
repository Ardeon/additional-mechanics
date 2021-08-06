package ru.ardeon.additionalmechanics.configs;

import org.bukkit.configuration.file.FileConfiguration;

public class SettingsLoaderMain implements SettingsLoader{
    public static final String fileName = "config.yml";
    private Configuration configuration;

    @Override
    public void loadAndSetDefaultIfNotExists(FileConfiguration config) {
        for(SettingMain setting : SettingMain.values()){
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
        for(SettingMain setting : SettingMain.values()){
            config.set(setting.path, setting.value);
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public enum SettingMain {
        MOON_WORLD_CHECK("Moon.WorldCheck", "Wild"),
        MOON_MANAGER("Moon.Enable", false),
        //Altar
        ALTAR_ENABLE("altar.enable",true),
        ALTAR_DISCHARGED_TEXT("altar.discharged-text","Заряд алтаря - [§cРазряжен§f]"),
        ALTAR_CHARGE_TEXT("altar.charge-text","Заряд алтаря"),
        ALTAR_MOON_TITLE_TEXT("altar.moon-title-text","Незеритовая луна"),
        ALTAR_MOON_SUBTITLE_TEXT("altar.moon-subtitle-text","перемотка времени невозможна"),
        ALTAR_BROADCAST_TEXT("altar.broadcast-text","§7Кто-то пришёл к алтарю и запустил ход времени"),
        ALTAR_DISCHARGED_MASSAGE("altar.discharged-massage","§3Алтарь разряжен. §7приходите позже"),
        ALTAR_WORLD("altar.world","Wild");


        private final String path;
        private Object value;

        SettingMain(String path, Object value) {
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
    }
}
