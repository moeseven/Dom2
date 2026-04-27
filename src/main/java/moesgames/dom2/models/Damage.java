package moesgames.dom2.models;

import moesgames.dom2.models.unit.DamageType;

public class Damage {
	DamageType type;
	int value;
	DamageSource source;
	public Damage(DamageType type, int number, DamageSource source) {
		super();
		this.type = type;
		this.value = number;
		this.source = source;
	}
	public DamageType getType() {
		return type;
	}
	public int getValue() {
		return value;
	}
	public DamageSource getSource() {
		return source;
	}
	
	public int resist(Resistances resistances) {
		int resisted_damage_value = value;
		if (resistances.getResistances().containsKey(type)) {
			resisted_damage_value += resistances.getResistances().get(type);
		}
		return resisted_damage_value;
	}

	
	
}
