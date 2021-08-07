package ru.ardeon.additionalmechanics.configs.settings;

import org.bukkit.configuration.file.FileConfiguration;
import ru.ardeon.additionalmechanics.configs.Configuration;
import ru.ardeon.additionalmechanics.configs.SettingsLoader;

public class SettingsLoaderPortals implements SettingsLoader {
    public static final String fileName = "portals.yml";
    private Configuration configuration;

    @Override
    public void loadAndSetDefaultIfNotExists(FileConfiguration config) {
        for(SettingsLoaderPortals.SettingPortals setting : SettingsLoaderPortals.SettingPortals.values()){
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
        for(SettingsLoaderPortals.SettingPortals setting : SettingsLoaderPortals.SettingPortals.values()){
            config.set(setting.path, setting.value);
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public enum SettingPortals{
        UNUSED("unused", "0");

        private final String path;
        private Object value;

        SettingPortals(String path, Object value) {
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
    }
}
