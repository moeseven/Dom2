package moesgames.dom2.models.spell;

import moesgames.dom2.localisation.LocalisationNameDescription;
import moesgames.dom2.models.DamageSource;
import moesgames.dom2.models.DomGame;
import moesgames.dom2.models.MagicPaths;
import moesgames.dom2.models.TargetableDomEntity;
import moesgames.dom2.models.target.GameTarget;
import moesgames.dom2.models.unit.DamageType;
import moesgames.dom2.models.unit.DomUnit;

public abstract class Spell implements DamageSource{
	
	
	public LocalisationNameDescription getName() {
		return name;
	}

	public MagicPaths getMagic_paths() {
		return magic_paths;
	}

	public int getScalar() {
		return scalar;
	}

	public int getMagic_cost() {
		return magic_cost;
	}


	public boolean isScales_with_path_level() {
		return scales_with_path_level;
	}

	public DamageType getType() {
		return type;
	}

	private LocalisationNameDescription name;
	
	private MagicPaths magic_paths = new MagicPaths();
	private int scalar;
	private int magic_cost;

	private boolean scales_with_path_level;
	
	private DamageType type;

	public Spell(LocalisationNameDescription name, int scalar, int magic_cost, int point_cost,
			boolean scales_with_path_level, DamageType type) {
		super();
		this.name = name;
		this.scalar = scalar;
		this.magic_cost = magic_cost;
		this.scales_with_path_level = scales_with_path_level;
		this.type = type;
	}
	
	public int getScalar(DomUnit caster) {
		if (scales_with_path_level) {
			return scalar + caster.getMagic_paths().bonusOnSpell(magic_paths);
		}
		return 0;
	}
	
	public abstract boolean needsTarget();
	
	public abstract void apply(DomUnit caster, GameTarget target, DomGame game);
	
	public abstract boolean isValidTarget(DomUnit caster, TargetableDomEntity targetable, DomGame game);
	

}
