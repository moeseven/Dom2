package moesgames.dom2.models.units;

import moesgames.dom2.localisation.LocalisationUnits;
import moesgames.dom2.models.unit.DamageType;
import moesgames.dom2.models.unit.RecruitableDomUnit;
import moesgames.dom2.models.unit.UnitTag;

public class Soldier extends RecruitableDomUnit {

	protected Soldier() {
		super(LocalisationUnits.soldier, 2, 2);
		getTags().add(UnitTag.living);
	}


}
