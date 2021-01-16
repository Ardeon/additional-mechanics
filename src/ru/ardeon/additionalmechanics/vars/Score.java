package ru.ardeon.additionalmechanics.vars;

public class Score {
	public int[] var = new int[5];
	public String uuid;
	
	public Score(String uuid) {
		this.uuid = uuid;
	}
	
	public int getVar(int varID) {
		if(varID<1 || varID>6) return 0;
		return var[varID-1];
	}
	
	public void setVar(int varID, int value) {
		if(varID<1 || varID>6) return;
		var[varID-1] = value;
	}
	
	public void addToVar(int varID, int value) {
		if(varID<1 || varID>6) return;
		var[varID-1] += value;
	}
	
	public Score(String uuid, int var1, int var2, int var3, int var4, int var5) {
		this.uuid = uuid;
		this.var[0] = var1;
		this.var[1] = var2;
		this.var[2] = var3;
		this.var[3] = var4;
		this.var[4] = var5;
	}
}
