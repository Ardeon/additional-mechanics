package ru.ardeon.additionalmechanics.vars.playerdata;

public class ArenaProgress {
	int[] boots = new int[12];
	int[] legs = new int[12];
	int[] chest = new int[12];
	int[][] power = new int[12][5];
	
	public int getBoots(int classid) {
		if(classid < 0 || classid > boots.length) {
			return 0;
		}
		return boots[classid];
	}
	public int getLegs(int classid) {
		if(classid < 0 || classid > boots.length) {
			return 0;
		}
		return legs[classid];
	}
	public int getChest(int classid) {
		if(classid < 0 || classid > boots.length) {
			return 0;
		}
		return chest[classid];
	}
	public int getPower(int classid, int powerid) {
		if(classid < 1 || classid > boots.length || powerid < 0 || powerid > 5) {
			return 0;
		}
		return power[classid][powerid];
	}
	public void setBoots(int classid, int value) {
		if(classid < 0 || classid > boots.length) {
			return;
		}
		boots[classid] = value;
	}
	public void setLegs(int classid, int value) {
		if(classid < 0 || classid > boots.length) {
			return;
		}
		legs[classid] = value;
	}
	public void setChest(int classid, int value) {
		if(classid < 0 || classid > boots.length) {
			return;
		}
		chest[classid] = value;
	}
	public void setPower(int classid, int powerid, int value) {
		if(classid < 1 || classid > boots.length || powerid < 0 || powerid > 5) {
			return;
		}
		power[classid][powerid] = value;
	}
	public void upgradeBoots(int classid, int value) {
		if(classid < 0 || classid > boots.length) {
			return;
		}
		boots[classid] += value;
	}
	public void upgradeLegs(int classid, int value) {
		if(classid < 0 || classid > boots.length) {
			return;
		}
		legs[classid] += value;
	}
	public void upgradeChest(int classid, int value) {
		if(classid < 0 || classid > boots.length) {
			return;
		}
		chest[classid] += value;
	}
	public void upgradePower(int classid, int powerid, int value) {
		if(classid < 1 || classid > boots.length || powerid < 0 || powerid > 5) {
			return;
		}
		power[classid][powerid] += value;
	}
}
