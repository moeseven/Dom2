package moesgames.dom2.models;

public enum PretenderAttribute {
	Wealth(4,10),
	Strength(3,9),
	Magic(2,8),
	Dominion(0,4),
	Delay(-4,0)
	;
	
	private int base_value;
	private int max_value;
	
	PretenderAttribute(int base, int max) {
		base_value = base;
		max_value = max;
	}

	public int getBase_value() {
		return base_value;
	}

	public int getMax_value() {
		return max_value;
	}
	
	
	
}
