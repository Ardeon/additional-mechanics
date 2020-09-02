package ru.ardeon.tost.guild.adventurers.portals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import ru.ardeon.tost.Tost;
import ru.ardeon.tost.util.ItemSerializer;

public class Portals {
	LinkedHashMap<UUID,PortalMenu> map = new LinkedHashMap<UUID,PortalMenu>();
	ItemSerializer is = new ItemSerializer();
	Gson gson;
	
	public Portals() {
		Tost.getPlugin().getServer().getPluginManager().registerEvents(new Portalslistener(this), Tost.getPlugin());
		Tost.getPlugin().getServer().getPluginCommand("enderbook").setExecutor(new EnderBookCommand(this));
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(ItemStack.class, new ItemSerializer());
		gson = builder.create();
	}
	
	public PortalMenu get(UUID id) {
		PortalMenu menu = map.get(id);
		if (menu==null) {
			menu = read(id);
		}
		return menu;
	}
	
	public void saveAll() {
		for (UUID id : map.keySet()) {
			save(id);
		}
	}
    public void save(UUID id) {
    	File f = new File(Tost.getPlugin().getDataFolder()+File.separator+"PortalBooks", id.toString()+".json");
    	PortalMenu menu = map.get(id);
		f.getParentFile().mkdirs();
		if (menu==null) {
			Tost.getPlugin().getLogger().info("save error");
			return;
		}
		try(FileWriter writer = new FileWriter(f, false)) {
			ItemStack[] items = menu.getInventory().getContents();
			//List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
			//Type itemsListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
			//for (ItemStack i : items) {
			//	l.add(i.serialize());
			//}
			
			String json = is.serialize(items, null, null).toString();//gson.toJson(items, ItemStack[].class);
			writer.write(json);
			writer.close();
        }
        catch(IOException ex) {
        	Tost.getPlugin().getLogger().info(ex.toString());
        } 
    }
	
	
    public void loadAll() {
    	File f = new File(Tost.getPlugin().getDataFolder()+File.separator+"PortalBooks");
    	for (File file : f.listFiles()) {
    		String uuid = file.getName().split("\\.")[0];
    		PortalMenu menu = getFromFile(file);
    		map.put(UUID.fromString(uuid), menu);
    	}
    } 
    public PortalMenu read(UUID id) {
    	File f = new File(Tost.getPlugin().getDataFolder()+File.separator+"PortalBooks", id.toString()+".json");
		return getFromFile(f);
    }
    public PortalMenu getFromFile(File f) {
    	f.getParentFile().mkdirs();
    	PortalMenu menu;
    	if (f.exists()) {
			FileReader fr;
			String json = "";
			try {
				fr = new FileReader(f);
				Scanner scan = new Scanner(fr);
		        while (scan.hasNextLine()) {
		            json = json.concat(scan.nextLine());
		            //log.info(json);
		        }
		        scan.close();
		        fr.close();
			} catch (FileNotFoundException e1) {
				Tost.getPlugin().getLogger().info("open error");
			} catch (IOException e1) {
				Tost.getPlugin().getLogger().info("error");
			}
			//Map<String, Object>[] itemsMap;
			//Type itemsMapType = new TypeToken<Map<String, Object>[]>() {}.getType();
			//itemsMap = gson.fromJson(json, itemsMapType);
			//List<ItemStack> l = new ArrayList<ItemStack>();
			//for (Map<String, Object> m : itemsMap) {
			//	l.add(ItemStack.deserialize(m));
			//}
			
			JsonParser parser = new JsonParser();
			
			ItemStack[] items = is.deserialize(parser.parse(json), null, null);//l.toArray(new ItemStack[l.size()]);
			menu = new PortalMenu("Эндер книга игрока "+f.getName().split("\\.")[0], items);
		}
		else {
			menu = new PortalMenu("Эндер книга игрока "+f.getName().split("\\.")[0]);
		}
    	return menu;
    }
}
