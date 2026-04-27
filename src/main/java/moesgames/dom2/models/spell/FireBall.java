package moesgames.dom2.models.spell;

import moesgames.dom2.localisation.LocalisationSpells;
import moesgames.dom2.models.Damage;
import moesgames.dom2.models.DomGame;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.TargetableDomEntity;
import moesgames.dom2.models.target.GameTarget;
import moesgames.dom2.models.unit.DamageType;
import moesgames.dom2.models.unit.DomUnit;

public class FireBall extends Spell {

	public FireBall() {
		super(LocalisationSpells.fireball, 2, 1, 1, true, DamageType.fire);
		getMagic_paths().add(MagicPath.Fire, 1);
	}

	@Override
	public boolean needsTarget() {
		return true;
	}

	@Override
	public void apply(DomUnit caster, GameTarget target, DomGame game) {
		int damage_number = getScalar(caster);
		Damage damage = new Damage(DamageType.fire, damage_number, this);
		DomUnit unit = target.extractFirstUnit();
		unit.takeDamage(damage);
	}


	@Override
	public boolean isValidTarget(DomUnit caster, TargetableDomEntity targetable, DomGame game) {
		return unitInProvince(caster, targetable, game);
	}

	public boolean unitInProvince(DomUnit caster, TargetableDomEntity targetable, DomGame game) {
		if (targetable instanceof DomUnit) {
			DomUnit target = (DomUnit) targetable;
			if (game.shareProvince(caster,target)) {
				return true;
			}
		}
		return false;
	}





}
