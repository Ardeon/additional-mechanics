package ru.ardeon.additionalmechanics.vars;

public class Score {
	public int var1 = 0;
	public int var2 = 0;
	public int var3 = 0;
	public int var4 = 0;
	public int var5 = 0;
	public String uuid;
	
	public Score(String uuid) {
		this.uuid = uuid;
	}
	
	public int getVar(int varID) {
		int value = 0;
		switch (varID) {
		case 1: {
			value = var1;
			break;
		}
		case 2: {
			value = var2;
			break;
		}
		case 3: {
			value = var3;
			break;
		}
		case 4: {
			value = var4;
			break;
		}
		case 5: {
			value = var5;
			break;
		}
		}
		return value;
	}
	
	public void setVar(int varID, int value) {
		switch (varID) {
		case 1: {
			var1 = value;
			break;
		}
		case 2: {
			var2 = value;
			break;
		}
		case 3: {
			var3 = value;
			break;
		}
		case 4: {
			var4 = value;
			break;
		}
		case 5: {
			var5 = value;
			break;
		}
		}
	}
	
	public void addToVar(int varID, int value) {
		switch (varID) {
		case 1: {
			var1 += value;
			break;
		}
		case 2: {
			var2 += value;
			break;
		}
		case 3: {
			var3 += value;
			break;
		}
		case 4: {
			var4 += value;
			break;
		}
		case 5: {
			var5 += value;
			break;
		}
		}
	}
	
	public Score(String uuid, int var1, int var2, int var3, int var4, int var5) {
		this.uuid = uuid;
		this.var1 = var1;
		this.var2 = var2;
		this.var3 = var3;
		this.var4 = var4;
		this.var5 = var5;
	}
}
