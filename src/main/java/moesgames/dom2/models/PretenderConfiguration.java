package moesgames.dom2.models;

import java.io.Serializable;

public class PretenderConfiguration implements Serializable{
	
	
	
	public PretenderAttributes getAttributes() {
		return attributes;
	}

	public PretenderMagicPaths getPaths() {
		return paths;
	}

	public void gainPoints(int p) {
		points+= p;
	}
	
	public boolean payPoints(int cost) {
		if (points >= cost) {
			points-=cost;
			return true;
		}
		return false;
	}
	
	private int points = 30;

	
	private PretenderMagicPaths paths = new PretenderMagicPaths();
	private PretenderAttributes attributes = new PretenderAttributes();
	
	private String name;
	
	public static int calcScalingCost(int level) {
		return level +1;
//		if (level > 0) {
//			return calcScalingCost(level-1) + level + 1;
//		}else {
//			return 1;
//		}		
	}

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
