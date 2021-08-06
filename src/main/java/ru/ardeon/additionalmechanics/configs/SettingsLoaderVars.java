package ru.ardeon.additionalmechanics.configs;

import org.bukkit.configuration.file.FileConfiguration;

public class SettingsLoaderVars implements SettingsLoader{
    public static final String fileName = "vars.yml";
    private Configuration configuration;

    @Override
    public void loadAndSetDefaultIfNotExists(FileConfiguration config) {
        for(SettingsLoaderVars.SettingVars setting : SettingsLoaderVars.SettingVars.values()){
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
        for(SettingsLoaderVars.SettingVars setting : SettingsLoaderVars.SettingVars.values()){
            config.set(setting.path, setting.value);
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public enum SettingVars{
        DONATE_CURRENT("donate.current", "0"),
        DONATE_NEED("donate.need", "2000"),
        BD_NAME("SQLite.Filename", "vars");

        private final String path;
        private Object value;

        SettingVars(String path, Object value) {
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
