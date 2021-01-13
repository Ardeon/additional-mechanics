package ru.ardeon.additionalmechanics.util;

public class PetConverter {
	public static int PetNameToID(String name) {
		int id=0;
		switch (name){
		case "§7  §aВсевидящее Око":{
			id=2;
		}
		case "§7  §aРипер":{
			id=3;
		}
		case "§7  §aМалыш Гаст":{
			id=4;
		}
		case "§7  §aМимик":{
			id=5;
		}
		case "§7  §aЛягуш":{
			id=6;
		}
		case "§7  §aМохнатый Шмель":{
			id=7;
		}
		case "§7  §aМалыш Йода":{
			id=8;
		}
		case "§7  §aРыжая Панда":{
			id=9;
		}
		case "§7  §aМалыш Магикарп":{
			id=10;
		}
		case "§7  §aГолубая Девятка":{
			id=11;
		}
		case "§7  §aДворф":{
			id=12;
		}
		}
		return id;
	}
}
