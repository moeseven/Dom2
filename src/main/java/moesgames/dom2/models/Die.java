package moesgames.dom2.models;

import java.util.Random;

public class Die {
	
	private Random random;
	
	
	
	public Die(Random random) {
		super();
		this.random = random;
	}



	public int roll() {
		int roll = random.nextInt(1,7);
		if (roll == 1) {
			roll = 0;
		}
		if (roll == 6) {
			roll = 100;
		}
		return roll;
	}

}
