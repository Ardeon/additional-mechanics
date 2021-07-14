package ru.ardeon.additionalmechanics.vars.playerdata;

public class ArenaData extends AbstractData{
	int[] boots = new int[12];
	int[] legs = new int[12];
	int[] chest = new int[12];
	int[][] power = new int[12][5];
	
	public int getBoots(int classid) {
		if(classid < 1 || classid > boots.length+1) {
			return 0;
		}
		return boots[classid-1];
	}
	public int getLegs(int classid) {
		if(classid < 1 || classid > boots.length+1) {
			return 0;
		}
		return legs[classid-1];
	}
	public int getChest(int classid) {
		if(classid < 1 || classid > boots.length+1) {
			return 0;
		}
		return chest[classid-1];
	}
	public int getPower(int classid, int powerid) {
		if(classid < 1 || classid > boots.length+1 || powerid < 1 || powerid > 6) {
			return 0;
		}
		return power[classid-1][powerid-1];
	}
	public void setBoots(int classid, int value) {
		if(classid < 1 || classid > boots.length+1) {
			return;
		}
		boots[classid-1] = value;
	}
	public void setLegs(int classid, int value) {
		if(classid < 1 || classid > boots.length+1) {
			return;
		}
		legs[classid-1] = value;
	}
	public void setChest(int classid, int value) {
		if(classid < 1 || classid > boots.length+1) {
			return;
		}
		chest[classid-1] = value;
	}
	public void setPower(int classid, int powerid, int value) {
		if(classid < 1 || classid > boots.length+1 || powerid < 1 || powerid > 6) {
			return;
		}
		power[classid-1][powerid-1] = value;
	}
	public void upgradeBoots(int classid, int value) {
		if(classid < 1 || classid > boots.length+1) {
			return;
		}
		boots[classid-1] += value;
	}
	public void upgradeLegs(int classid, int value) {
		if(classid < 1 || classid > boots.length+1) {
			return;
		}
		legs[classid-1] += value;
	}
	public void upgradeChest(int classid, int value) {
		if(classid < 1 || classid > boots.length+1) {
			return;
		}
		chest[classid-1] += value;
	}
	public void upgradePower(int classid, int powerid, int value) {
		if(classid < 1 || classid > boots.length+1 || powerid < 1 || powerid > 6) {
			return;
		}
		power[classid-1][powerid-1] += value;
	}
}
