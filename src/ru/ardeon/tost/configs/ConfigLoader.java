package ru.ardeon.tost.configs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.ardeon.tost.Tost;

public class ConfigLoader {
    File configFile;
    FileConfiguration config;
    File configFileBlocks;
    FileConfiguration configBlocks;

    public ConfigLoader() {
    	init();
    }

    public FileConfiguration getConfigBlocks() {
    	return configBlocks;
    }
    public FileConfiguration getConfig() {
    	return config;
    }
    
	public void init() {
		configFile = new File(Tost.getPlugin().getDataFolder(), "config.yml");
		configFileBlocks = new File(Tost.getPlugin().getDataFolder(), "configBlocks.yml");
	    config = new YamlConfiguration();
	    configBlocks = new YamlConfiguration();
	    try {
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
	    loadYamls();
	}
	
	private void firstRun() throws Exception {
        if(!configFile.exists()){                        // checks if the yaml does not exists
        	configFile.getParentFile().mkdirs();         // creates the /plugins/<pluginName>/ directory if not found
            copy(Tost.getPlugin().getResource("config.yml"), configFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
        if(!configFileBlocks.exists()) {
        	configFileBlocks.getParentFile().mkdirs();
        	copy(Tost.getPlugin().getResource("configBlocks.yml"), configFileBlocks);
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
    public void loadYamls() {
        try {
            config.load(configFile); //loads the contents of the File to its FileConfiguration
            configBlocks.load(configFileBlocks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveYamls() {
        try {
            config.save(configFile); //saves the FileConfiguration to its File
            configBlocks.save(configFileBlocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
