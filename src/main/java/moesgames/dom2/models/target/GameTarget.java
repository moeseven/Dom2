package moesgames.dom2.models.target;

import java.util.ArrayList;

import moesgames.dom2.models.unit.DomUnit;

public class GameTarget {
	
	private ArrayList<DomUnit> unit_targets = new ArrayList<DomUnit>();
	
	public void addUnit(DomUnit unit) {
		unit_targets.add(unit);
	}
	
	public void addTile() {
		
	}

	public DomUnit extractFirstUnit() {
		return unit_targets.removeFirst();
	}

}
