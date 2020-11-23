package ru.ardeon.additionalmechanics.mechanics.builds;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.ardeon.additionalmechanics.AdditionalMechanics;

public class Build {
	Material[][][] blocks;//[x] [y] [z] = new Material[1][1][1]
	
	
	public static Build newBuild(Block block, int x, int y, int z, String name) {
		Location loc = block.getLocation();
		ArrayList<String> aliesLiters = new ArrayList<String>();
		ArrayList<Material> aliesMaterials = new ArrayList<Material>();
		//HashMap<String, Material> map = new HashMap<String, Material>();
		Material[][][] bl = new Material[x][y][z];
		for (int i = 0; i < y; i++) {
	    	for (int j = 0; j < z; j++) {
	    		for (int k = 0; k < x; k++) {
	    			bl[k][i][j] = loc.clone().add(k, i, j).getBlock().getType();
	    			//AdditionalMechanics.getPlugin().getLogger().info(bl[k][i][j].toString());
		    	}
	    	}
	    }
		/*
		int xx = bl.length;
		int yx = bl[0].length;
		int zx = bl[0][0].length;
		AdditionalMechanics.getPlugin().getLogger().info(" "+ xx + " " + yx + " " + zx);
		*/
		Character key = 'a';
		int n = key;
		key = (char) n;
		for (int i = 0; i < y; i++) {
	    	for (int j = 0; j < z; j++) {
	    		for (int k = 0; k < x; k++) {
	    			if (!aliesMaterials.contains(bl[k][i][j])) {
	    				//map.put(key.toString(), bl[k][j][i]);
	    				aliesLiters.add(key.toString());
	    				aliesMaterials.add(bl[k][i][j]);
	    				n++;
	    				key = (char) n;
	    				//!map.containsValue(bl[k][j][i])
	    			}
		    	}
	    	}
	    }
		File configFile;
		FileConfiguration config;
		File folder = new File(AdditionalMechanics.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "Builds");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		configFile = new File(folder, name + ".yml");
		config = new YamlConfiguration();
		config.set("x", x);
		config.set("y", y);
		config.set("z", z);
		ConfigurationSection mapsecsion = config.createSection("map");
		for (int i = 0; i < aliesLiters.size(); i++) {
			mapsecsion.set(aliesLiters.get(i), aliesMaterials.get(i).toString());
		}
		for (int i = 0; i < y; i++) {
			ConfigurationSection layer = config.createSection(""+i);
	    	for (int j = 0; j < z; j++) {
	    		String line = "";
	    		for (int k = 0; k < x; k++) {
	    			int index = aliesMaterials.indexOf(bl[k][i][j]);
	    			line = line + aliesLiters.get(index);
		    	}
	    		layer.set(""+j, line);
	    	}
	    }
		try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Build(bl);
	}

	public Build(Material[][][] bl) {
		blocks = bl;
	}
	
	public Build(String name) {
		File folder = new File(AdditionalMechanics.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "Builds");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File configFile;
	    FileConfiguration config;
	    configFile = new File(folder, name + ".yml");
	    config = new YamlConfiguration();
	    try {
            config.load(configFile); //loads the contents of the File to its FileConfiguration
        } catch (Exception e) {
            e.printStackTrace();
            blocks = null;
            return;
        }
	    int x, y, z;
	    x = config.getInt("x");
	    y = config.getInt("y");
	    z = config.getInt("z");
	    blocks = new Material[x][y][z];
	    HashMap<String, Material> map = new HashMap<String, Material>();
	    ConfigurationSection materials = config.getConfigurationSection("map");
	    for (String s : materials.getKeys(false)) {
	    	String m = materials.getString(s,"AIR");
	    	Material mat = Material.getMaterial(m);
	    	if (mat==null) {
	    		mat = Material.AIR;
	    	}
	    	map.put(s, mat);
	    }
	    for (int i = 0; i < y; i++) {
	    	ConfigurationSection layer = config.getConfigurationSection("" + i);
	    	for (int j = 0; j < z; j++) {
	    		String line = layer.getString(""+j,"");
	    		for (int k = 0; k < x; k++) {
	    			if (k < line.length()) {
	    				blocks[k][i][j]=map.getOrDefault(line.substring(k, k+1), null);//Material.AIR);
	    			}
		    	}
	    	}
	    }
	    
	}
	
	public boolean test(Block block) {
		int xx = blocks.length;
		int yx = blocks[0].length;
		int zx = blocks[0][0].length;
		/*AdditionalMechanics.getPlugin().getLogger().info(" "+ xx + " " + yx + " " + zx);*/
		Location loc = block.getLocation();
		for (int i = 0; i < yx; i++) {
	    	for (int j = 0; j < zx; j++) {
	    		for (int k = 0; k < xx; k++) {
	    			if (blocks[k][i][j] != loc.clone().add(k, i, j).getBlock().getType())
	    				return false;
		    	}
	    	}
	    }
		return true;
	}
	
	public static HashMap<String, Build> loadAll(){
		HashMap<String, Build> map = new HashMap<String, Build>();
		File folder = new File(AdditionalMechanics.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "Builds");
		if (folder.exists()) {
			for (File f : folder.listFiles()) {
				String name =  f.getName();
				if (name.indexOf(".yml")!=-1) {
					name = name.substring(0, name.length() - 4);
				}
				map.put(name,new Build(name));
			}
		}
		return map;
	}
}
