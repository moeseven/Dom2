package moesgames.dom2.app;

import moesgames.dom2.models.PretenderConfiguration;

public class TesterA {

	public static void main(String[] args) {
		for (int i = 0; i < 6; i++) {
			System.out.println("level "+i+" -> "+(1+i)+" costs: "+PretenderConfiguration.calcScalingCost(i));
		}
		
	}

}
