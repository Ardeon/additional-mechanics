package ru.ardeon.additionalmechanics.vars.playerdata;

public class AbilityAndStatsData extends AbstractData{
	//Map<String,Integer> achievement = new HashMap<String,Integer>();
	
	int horsespeed = 0;
	int horsejump = 0;
	int horsehp = 0;
	int hookLength = 0;
	int adventurerRadarSmall = 0;
	int adventurerRadarMedium = 0;
	int adventurerRadarBig = 0;
	
	int minerExtradrop = 0;
	int minerSize = 0;
	int minerRadarSmall = 0;
	int minerRadarMedium = 0;
	int minerRadarBig = 0;
	
	
	
	
	
	
	
	
	/*
	public String getAchievement() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(achievement);
			oos.close();
			byte[] bytes = compress(baos.toByteArray());
			if (bytes==null) {
				return null;
			}
			return new String(Base64.getEncoder().encode(bytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Integer> readAchievement(String string){
		byte[] decoded = Base64.getDecoder().decode(string.getBytes());
		decoded = decompress(decoded);
		if (decoded==null)
			return null;
		ByteArrayInputStream bais = new ByteArrayInputStream(decoded);
		ObjectInputStream ois;
		Map<String,Integer> map = new HashMap<String,Integer>();
		try {
			ois = new ObjectInputStream(bais);
			map = (Map<String,Integer>) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private static byte[] compress(byte[] in) {
	    try {
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        DeflaterOutputStream defl = new DeflaterOutputStream(out);
	        defl.write(in);
	        defl.flush();
	        defl.close();

	        return out.toByteArray();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	private static byte[] decompress(byte[] in) {
	    try {
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        InflaterOutputStream infl = new InflaterOutputStream(out);
	        infl.write(in);
	        infl.flush();
	        infl.close();

	        return out.toByteArray();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	*/
}
