package ru.ardeon.additionalmechanics.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

public class Base64converter {
	
	public static String itemToString(ItemStack item) throws IOException {
		Map<String, Object> data = item.serialize();
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(data);
		byte[] bytes = byteOut.toByteArray();
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	@SuppressWarnings("unchecked")
	public static ItemStack stringToItem(String string) throws IOException, ClassNotFoundException {
		byte[] bytes = Base64.getDecoder().decode(string);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
	    ObjectInputStream in = new ObjectInputStream(byteIn);
	    Map<String, Object> data = (Map<String, Object>) in.readObject();
	    return ItemStack.deserialize(data);
	}

}
