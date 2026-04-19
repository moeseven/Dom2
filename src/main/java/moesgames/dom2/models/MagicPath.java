package moesgames.dom2.models;

public enum MagicPath {
	Air,
	Astral,
	Blood,
	Death,
	Earth,
	Fire,
	Glamour,
	Nature,
	Water
	;
	
	private int base_value;
	
	MagicPath() {
		base_value = 0;
	}

	public int getBase_value() {
		return base_value;
	}
	
	
}
