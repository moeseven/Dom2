package moesgames.dom2.models;

import java.util.LinkedList;

import moesgames.dom2.models.unit.DomUnit;

public class Province {

	private int dominion = 0;
	
	private LinkedList<DomUnit> unit_position_player_1 = new LinkedList<DomUnit>();
	private LinkedList<DomUnit> unit_position_player_2 = new LinkedList<DomUnit>();
}
