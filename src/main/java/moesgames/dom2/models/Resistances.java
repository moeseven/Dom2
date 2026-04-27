package moesgames.dom2.models;

import java.util.HashMap;

import moesgames.dom2.models.unit.DamageType;

public class Resistances {
	private HashMap<DamageType, Integer> resistances = new HashMap<DamageType, Integer>();

	public HashMap<DamageType, Integer> getResistances() {
		return resistances;
	}
	
	public void addResistance(DamageType resistance_type, int r) {
		if (resistances.containsKey(resistance_type)) {
			resistances.put(resistance_type,resistances.get(resistance_type)+r);
		}else {
			resistances.put(resistance_type, r);
		}
	}
}
