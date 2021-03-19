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
	private File configFile;
	private FileConfiguration config;
	private File varsFile;
	private FileConfiguration vars;
	private File configFileBlocks;
	private FileConfiguration configBlocks;
	private File configFilePermkit;
	private FileConfiguration configPermkit;
	private File altarFile;
	private YamlConfiguration altar;
	private File portalsFile;
	private FileConfiguration portals;
	
    public ConfigLoader() {
    	init();
    }

    public FileConfiguration getConfigBlocks() {
    	return configBlocks;
    }
    public FileConfiguration getConfig() {
    	return config;
    }
    public FileConfiguration getVars() {
    	return vars;
    }
    public FileConfiguration getConfigPermkit() {
    	return configPermkit;
    }
    public FileConfiguration getConfigAltar() {
    	return altar;
    }
    public FileConfiguration getConfigPortals() {
    	return portals;
    }
    
	public void init() {
		configFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), "config.yml");
		varsFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), "vars.yml");
		configFileBlocks = new File(AdditionalMechanics.getPlugin().getDataFolder(), "configBlocks.yml");
		configFilePermkit = new File(AdditionalMechanics.getPlugin().getDataFolder(), "configPermkit.yml");
		altarFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), "altar.yml");
		portalsFile = new File(AdditionalMechanics.getPlugin().getDataFolder(), "portals.yml");
		vars = new YamlConfiguration();
	    config = new YamlConfiguration();
	    configBlocks = new YamlConfiguration();
	    configPermkit = new YamlConfiguration();
	    altar = new YamlConfiguration();
	    portals = new YamlConfiguration();
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
        if(!varsFile.exists()){
        	varsFile.getParentFile().mkdirs();
            copy(AdditionalMechanics.getPlugin().getResource("vars.yml"), varsFile);
        }
        if(!configFileBlocks.exists()) {
        	configFileBlocks.getParentFile().mkdirs();
        	copy(AdditionalMechanics.getPlugin().getResource("configBlocks.yml"), configFileBlocks);
        }
        if(!configFilePermkit.exists()) {
        	configFilePermkit.getParentFile().mkdirs();
        	copy(AdditionalMechanics.getPlugin().getResource("configPermkit.yml"), configFilePermkit);
        }
        if(!altarFile.exists()) {
        	altarFile.getParentFile().mkdirs();
        	copy(AdditionalMechanics.getPlugin().getResource("altar.yml"), altarFile);
        }
        if(!portalsFile.exists()) {
        	portalsFile.getParentFile().mkdirs();
        	copy(AdditionalMechanics.getPlugin().getResource("portals.yml"), portalsFile);
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
            vars.load(varsFile);
            configBlocks.load(configFileBlocks);
            configPermkit.load(configFilePermkit);
            altar.load(altarFile);
            portals.load(portalsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveYamls() {
        try {
            config.save(configFile); //saves the FileConfiguration to its File
            vars.save(varsFile);
            configBlocks.save(configFileBlocks);
            configPermkit.save(configFilePermkit);
            altar.save(altarFile);
            portals.save(portalsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
