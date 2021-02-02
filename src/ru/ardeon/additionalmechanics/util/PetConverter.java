package ru.ardeon.additionalmechanics.util;
@Deprecated
public class PetConverter {
	public static int PetNameToID(String name) {
		int id=0;
		//AdditionalMechanics.getPlugin().getLogger().info(name+"dddd");
		//AdditionalMechanics.getPlugin().getLogger().info(name+"§7  §aВсевидящее Око");
		switch (name){
		case "§7 §aВсевидящее Око":{
			id=2;
			break;
		}
		case "§7 §aРипер":{
			id=3;
			break;
		}
		case "§7 §aМалыш Гаст":{
			id=4;
			break;
		}
		case "§7 §aМимик":{
			id=5;
			break;
		}
		case "§7 §aЛягуш":{
			id=6;
			break;
		}
		case "§7 §aМохнатый Шмель":{
			id=7;
			break;
		}
		case "§7 §aМалыш Йода":{
			id=8;
			break;
		}
		case "§7 §aРыжая Панда":{
			id=9;
			break;
		}
		case "§7 §aМалыш Магикарп":{
			id=10;
			break;
		}
		case "§7 §aГолубая Девятка":{
			id=11;
			break;
		}
		case "§7 §aДворф":{
			id=12;
			break;
		}
		}
		//AdditionalMechanics.getPlugin().getLogger().info(""+id);
		return id;
	}
}
