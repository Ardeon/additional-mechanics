package ru.ardeon.additionalmechanics.configs;

import org.bukkit.configuration.file.FileConfiguration;

public interface SettingsLoader {
    public void loadAndSetDefaultIfNotExists(FileConfiguration config);
    public String getFileName();
    public void save(FileConfiguration config);
    public void setConfiguration(Configuration configuration);
}
