package ru.ardeon.additionalmechanics.util;

import java.awt.Color;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TextUtilRGB {
	public static BaseComponent[] toSet(String string, String hex1, String hex2) {
		Color color1, color2;
		color1 = colorFromStr(hex1);
		color2 = colorFromStr(hex2);
		int r = color2.getRed() - color1.getRed();
		int g = color2.getGreen() - color1.getGreen();
		int b = color2.getBlue() - color1.getBlue();
		int r1 = color1.getRed();
		int g1 = color1.getGreen();
		int b1 = color1.getBlue();
		String[] chunks = string.split("");
		int l = chunks.length;
		ComponentBuilder cb = new ComponentBuilder();
		for (int i = 0; i < l; i++) {
			Color color = new Color(r1+r*i/l, g1+g*i/l, b1+b*i/l);
			cb.append(chunks[i]).color(ChatColor.of(color));
		}
		return cb.create();
	}
	
	public static BaseComponent[] toSet(String string, Color color1, Color color2) {
		int r = color2.getRed() - color1.getRed();
		int g = color2.getGreen() - color1.getGreen();
		int b = color2.getBlue() - color1.getBlue();
		int r1 = color1.getRed();
		int g1 = color1.getGreen();
		int b1 = color1.getBlue();
		String[] chunks = string.split("");
		int l = chunks.length;
		ComponentBuilder cb = new ComponentBuilder();
		for (int i = 0; i < l; i++) {
			Color color = new Color(r1+r*i/l, g1+g*i/l, b1+b*i/l);
			cb.append(chunks[i]).color(ChatColor.of(color));
		}
		return cb.create();
	}
	
	public static Color colorFromStr(String str) {
		if (str.length()==6) {
			try {
				int r = Integer.parseInt(str.substring(0, 2), 16);
				int g = Integer.parseInt(str.substring(2, 4), 16);
				int b = Integer.parseInt(str.substring(4, 6), 16);
				return new Color(r,g,b);
			} catch (Exception e) {
				return null;
			}
		}
		
		return null;
		
	}
}
