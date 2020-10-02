package ru.ardeon.additionalmechanics.configs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class ConfigLoader {
    File configFile;
    FileConfiguration config;
    File configFileBlocks;
    FileConfiguration configBlocks;
    File configFilePermkit;
    FileConfiguration configPermkit;
    

    public ConfigLoader() {
    	init();
    }

    public FileConfiguration getConfigBlocks() {
    	return configBlocks;
    }
    public FileConfiguration getConfig() {
    	return config;
    }
    public FileConfiguration getConfigPermkit() {
    	return configPermkit;
    }
    
	public void init() {
		configFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), "config.yml");
		configFileBlocks = new File(AdditionalMechanics.getPlugin().getDataFolder(), "configBlocks.yml");
		configFilePermkit = new File(AdditionalMechanics.getPlugin().getDataFolder(), "configPermkit.yml");
	    config = new YamlConfiguration();
	    configBlocks = new YamlConfiguration();
	    configPermkit = new YamlConfiguration();
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
            copy(AdditionalMechanics.getPlugin().getResource("config.yml"), configFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
        if(!configFileBlocks.exists()) {
        	configFileBlocks.getParentFile().mkdirs();
        	copy(AdditionalMechanics.getPlugin().getResource("configBlocks.yml"), configFileBlocks);
        }
        if(!configFilePermkit.exists()) {
        	configFilePermkit.getParentFile().mkdirs();
        	copy(AdditionalMechanics.getPlugin().getResource("configPermkit.yml"), configFilePermkit);
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
            configPermkit.load(configFilePermkit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveYamls() {
        try {
            config.save(configFile); //saves the FileConfiguration to its File
            configBlocks.save(configFileBlocks);
            configPermkit.save(configFilePermkit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
