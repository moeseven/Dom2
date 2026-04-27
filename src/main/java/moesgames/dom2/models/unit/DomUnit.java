package moesgames.dom2.models.unit;

import java.util.HashMap;
import java.util.LinkedList;

import moesgames.dom2.localisation.LocalisationNameDescription;
import moesgames.dom2.models.Damage;
import moesgames.dom2.models.DamageSource;
import moesgames.dom2.models.Die;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.MagicPaths;
import moesgames.dom2.models.Resistances;
import moesgames.dom2.models.TargetableDomEntity;

public class DomUnit extends TargetableDomEntity implements DamageSource{
	
	
	
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

	public Resistances getResistances() {
		return resistances;
	}

	public LinkedList<UnitTag> getTags() {
		return tags;
	}

	private LocalisationNameDescription name;
	private int strength;
	private int stored_damage = 0;
	private int gold_cost;
	private DamageType damage_type;
	
	private int preach;
	
	private Resistances resistances;
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
	
	public void startTurn() {
		stored_damage = 0;
	}

	


	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	public void takeDamage(Damage damage) {
		stored_damage += damage.resist(resistances);
		checkDeath();
	}


	private void checkDeath() {
		// TODO Auto-generated method stub
		
	}

}
