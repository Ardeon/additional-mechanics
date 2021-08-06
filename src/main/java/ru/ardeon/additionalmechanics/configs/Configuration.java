package ru.ardeon.additionalmechanics.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.Reloadable;

import java.io.*;

public class Configuration implements Reloadable {

    private final File configFile;
    private final FileConfiguration config;
    private final SettingsLoader settingsLoader;

    public FileConfiguration getConfig(){ return config; }

    Configuration(SettingsLoader loader){  //Строка name отвечает за название файла, а также указывает какой файл из папки resources будет скопирован
        settingsLoader = loader;
        settingsLoader.setConfiguration(this);
        String name = loader.getFileName();
        configFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), name);
        config = new YamlConfiguration();

        try {
            setDefaultIfNotExists(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadFromFile();
    }

    private void setDefaultIfNotExists(String name) throws Exception {
        if(!configFile.exists()){                        // checks if the yaml does not exists
            configFile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
            copy(AdditionalMechanics.getPlugin().getResource(name), configFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
    }

    public void loadFromFile() {
        try {
            config.load(configFile); //loads the contents of the File to its FileConfiguration
            loadSettings();          //загрузка настроек из файла конфигурации в Enum который доступен из любой части программы
                                     //возможно также использовать FileConfiguration но не желательно для настроек
                                     //использование FileConfiguration полезно для данных структура которых не статична
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        try {
            config.save(configFile); //saves the FileConfiguration to its File
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings(){
        settingsLoader.loadAndSetDefaultIfNotExists(config);
    }

    @Override
    public void reload() {
        loadFromFile();
    }


}
