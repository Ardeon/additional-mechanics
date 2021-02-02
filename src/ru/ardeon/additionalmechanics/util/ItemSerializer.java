package ru.ardeon.additionalmechanics.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
@Deprecated
public class ItemSerializer implements JsonSerializer<ItemStack[]>, JsonDeserializer<ItemStack[]>  {
	@Override
	public JsonElement serialize(ItemStack[] src, Type type, 
			JsonSerializationContext context) {
		JsonArray arr = new JsonArray();
		for (ItemStack i : src) {
			JsonObject object = new JsonObject();
			//object.addProperty("Material", src.getType().name());
			if (i!=null) {
				object.addProperty("Amount", i.getAmount());
		        ItemMeta meta = i.getItemMeta();
		        JsonObject metaObj = new JsonObject();
		        if (meta!=null) {
		        	if (meta.hasDisplayName())
		        		metaObj.addProperty("DisplayName", i.getItemMeta().getDisplayName());
		        	if (meta.hasLore()) {
		        		List<String> lore = meta.getLore();
		        		JsonArray loreArr = new JsonArray();
		        		for(int j = 0;j<lore.size();j++) {
		        			loreArr.add(lore.get(j));
		        		}
		        		metaObj.add("Lore", loreArr);
		        	}
		        		
		        	object.add("Meta", metaObj);
		        }
			}
	        
	        arr.add(object);
		}
		JsonObject object = new JsonObject();
		object.add("Array", arr);
        
        
        return object;
    }
	
	@Override
    public ItemStack[] deserialize(JsonElement json, Type type, 
    		JsonDeserializationContext context) throws JsonParseException {
		
		JsonArray arr = json.getAsJsonObject().get("Array").getAsJsonArray();
		List<ItemStack> itemslist = new ArrayList<ItemStack>();
		for (int j = 0; j < arr.size();j++) {
			JsonObject object = arr.get(j).getAsJsonObject();
			ItemStack item = new ItemStack(Material.PAPER);
			
			if (object.get("Meta")!=null) {
				JsonObject metaObj = object.get("Meta").getAsJsonObject();
				if (metaObj!=null) {
					ItemMeta meta = item.getItemMeta();
					JsonElement nameEl = metaObj.get("DisplayName");
					if (nameEl!=null) {
						meta.setDisplayName(nameEl.getAsString());
					}
					if (metaObj.get("Lore")!=null) {
						JsonArray loreArr = metaObj.get("Lore").getAsJsonArray();
						if (loreArr!=null) {
							List<String> lore = new ArrayList<String>();
							loreArr.forEach(s->{
								lore.add(s.getAsString());
							});
							meta.setLore(lore);
						}
					}
					item.setItemMeta(meta);
				}
			}
			if (object.get("Amount")!=null) {
				item.setAmount(object.get("Amount").getAsInt());
			}
			else {
				item = null;
			}
			itemslist.add(item);
		}
		
		return itemslist.toArray(new ItemStack[itemslist.size()]);
    }
}