package moesgames.dom2.models.unit;

import java.util.HashMap;
import java.util.LinkedList;

import moesgames.dom2.localisation.LocalisationNameDescription;
import moesgames.dom2.models.Die;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.MagicPaths;
import moesgames.dom2.models.TargetableDomEntity;

public class DomUnit extends TargetableDomEntity{
	
	
	
	public void setDamage_type(DamageType damage_type) {
		this.damage_type = damage_type;
	}

	public DamageType getDamage_type() {
		return damage_type;
	}

	public MagicPaths getMagic_paths() {
		return magic_paths;
	}

	public LocalisationNameDescription getName() {
		return name;
	}

	public int getStrength() {
		return strength;
	}

	public int getGold_cost() {
		return gold_cost;
	}

	public int getPreach() {
		return preach;
	}

	public HashMap<DamageType, Integer> getResistances() {
		return resistances;
	}

	public LinkedList<UnitTag> getTags() {
		return tags;
	}

	private LocalisationNameDescription name;
	private int strength;
	private int gold_cost;
	private DamageType damage_type;
	
	private int preach;
	
	private HashMap<DamageType, Integer> resistances = new HashMap<DamageType, Integer>();
	private MagicPaths magic_paths = new MagicPaths();
	
	private LinkedList<UnitTag> tags;

	protected DomUnit(LocalisationNameDescription name, int strength, int gold_cost) {
		super();
		this.name = name;
		this.strength = strength;
		this.gold_cost = gold_cost;
		damage_type = DamageType.combat;
	}
	
	public DomUnit(LocalisationNameDescription name, int strength) {
		this(name,strength,0);
	}
	

	public void addResistance(DamageType resistance_type, int r) {
		if (resistances.containsKey(resistance_type)) {
			resistances.put(resistance_type,resistances.get(resistance_type)+r);
		}else {
			resistances.put(resistance_type, r);
		}
	}
	
	public int roll_against(DamageType damageType, Die die) {
		int roll = die.roll();
		if (resistances.containsKey(damageType)) {
			roll += resistances.get(damageType);
		}
		return roll + getStrength();
	}

	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

}
