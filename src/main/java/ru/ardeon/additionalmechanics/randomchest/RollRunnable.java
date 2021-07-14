package ru.ardeon.additionalmechanics.randomchest;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;

public class RollRunnable extends BukkitRunnable {
	private ProtocolManager protocolManager;
	AdditionalMechanics plugin;
	Player player;
	ItemStack reward;
	int i;
	
	public RollRunnable(AdditionalMechanics plugin, Player player, ItemStack item){
		this.plugin = plugin;
		this.player = player;
		reward = item;
		i = 8;
		protocolManager = ProtocolLibrary.getProtocolManager();
	}

	@Override
	public void run() {
		if (i>0) {
			roll(player, reward, 36+i);
			i--;
		}
		else {
			if (i==0) roll(player, reward, 36);
			else {
				player.updateInventory();
				this.cancel();
			}
			i--;
		}
	}
	
	public void roll(Player player, ItemStack item, int slot) {
	    PacketContainer pc = protocolManager.createPacket(PacketType.Play.Server.WINDOW_ITEMS);
	    //pc.getBytes().write(0, (byte) 0);
	    //pc.getShorts().write(0, (short) 36);
	    pc.getIntegers().write(0, 0);
	    int inventorySize = 46;

	    ItemStack[] blankInventory = new ItemStack[inventorySize];
	    Arrays.fill(blankInventory, new ItemStack(Material.AIR));
	    blankInventory[slot] = item;
	    //pc.getItemSlots().write(0, item);
	    //old minecraft versions
	    StructureModifier<ItemStack[]> itemArrayModifier = pc.getItemArrayModifier();
	    if (itemArrayModifier.size() > 0) {
	        itemArrayModifier.write(0, blankInventory);
	    } else {
	        //minecraft versions above 1.11
	        StructureModifier<List<ItemStack>> itemListModifier = pc.getItemListModifier();
	        itemListModifier.write(0, Arrays.asList(blankInventory));
	    }
	    
	    try {
	        protocolManager.sendServerPacket(player, pc, false);
	    } catch (InvocationTargetException invocationExc) {
	    	plugin.getLogger().info("Error during sending blank inventory");
	    }
	    //player.updateInventory();
	}
}
