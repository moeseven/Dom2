package moesgames.dom2.models.spell;

import moesgames.dom2.localisation.LocalisationSpells;
import moesgames.dom2.models.DomGame;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.TargetableDomEntity;
import moesgames.dom2.models.target.GameTarget;
import moesgames.dom2.models.unit.DamageType;
import moesgames.dom2.models.unit.DomUnit;

public class FireBall extends Spell {

	public FireBall() {
		super(LocalisationSpells.fireball, 3, 1, 1, true, DamageType.fire);
		getMagic_paths().add(MagicPath.Fire, 1);
	}

	@Override
	public boolean needsTarget() {
		return true;
	}

	@Override
	public void apply(DomUnit caster, GameTarget target, DomGame game) {
		int damage = getScalar(caster);
		int roll = game.getDie().roll() + damage;
		DomUnit unit = target.extractFirstUnit();
		int target_roll = unit.roll_against(getType(), game.getDie());
		if (roll > target_roll) {
			game.kill(unit);
		}
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
