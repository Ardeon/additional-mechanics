package ru.ardeon.tost.guild.adventurers.portals;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class PortalBook {
	public ItemStack i;
	
	PortalBook(){
		i = new ItemStack(Material.WRITTEN_BOOK);
		
		BookMeta bookMeta = (BookMeta) i.getItemMeta();
		ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/broadcast 22");
		//List<BaseComponent[]> pages = bookMeta.spigot().getPages();
		BaseComponent[] page = new ComponentBuilder("Click me").strikethrough(true)
		        .event(clickEvent)
		        .create();

		//add the page to the meta
		bookMeta.spigot().addPage(page);
		bookMeta.setTitle("Interactive Book");
		bookMeta.setAuthor("gigosaurus");
		
		//BaseComponent[] tc = new ComponentBuilder("Hello ").color(ChatColor.BLUE).create();
		i.setItemMeta(bookMeta);
		//book.spigot().getPage(0).toString();
		//book.spigot()
		//book.
		//p.openBook(arg0);
	}
	
	public void open(Player p) {
		p.openBook(i);
	}
}
