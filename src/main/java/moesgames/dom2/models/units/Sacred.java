package moesgames.dom2.models.units;

import java.util.Map.Entry;

import moesgames.dom2.localisation.LocalisationUnits;
import moesgames.dom2.models.MagicPath;
import moesgames.dom2.models.PretenderConfiguration;
import moesgames.dom2.models.unit.RecruitableDomUnit;
import moesgames.dom2.models.unit.UnitTag;

public class Sacred extends RecruitableDomUnit {

	protected Sacred(PretenderConfiguration pc) {
		super(LocalisationUnits.sacred, 3, 3);
		getTags().add(UnitTag.living);
		for (Entry<MagicPath, Integer> entry : pc.getPaths().getMagic_paths().entrySet()) {
			MagicPath key = entry.getKey();
    	    Integer value = entry.getValue();
    	    if (value > 1) {
				getMagic_paths().add(key, value - 1);
			}			
		}
		
	}


}
