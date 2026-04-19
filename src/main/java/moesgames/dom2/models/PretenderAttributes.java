package moesgames.dom2.models;

import java.io.Serializable;
import java.util.HashMap;

public class PretenderAttributes implements Serializable{
	
	
	
	public HashMap<PretenderAttribute, Integer> getAttributes() {
		return attributes;
	}

	private HashMap<PretenderAttribute, Integer> attributes = new HashMap<PretenderAttribute, Integer>();

	public PretenderAttributes() {
		super();
		addAtribute(PretenderAttribute.Wealth);
		addAtribute(PretenderAttribute.Magic);
		addAtribute(PretenderAttribute.Strength);
		addAtribute(PretenderAttribute.Dominion);
		addAtribute(PretenderAttribute.Delay);
	}
	
	private void addAtribute(PretenderAttribute a) {
		attributes.put(a, a.getBase_value());
	}
	
	public boolean increase(PretenderConfiguration pc, PretenderAttribute attribute) {
		if (pc.payPoints(calcLevelupCost(attribute))) {
			levelUp(attribute);
			return true;
		}
		return false;
	}
	
	public boolean decrease(PretenderConfiguration pc, PretenderAttribute attribute) {
		int attribute_value = attributes.get(attribute);
		if (attribute_value > attribute.getBase_value()) {
			attributes.put(attribute, attribute_value - 1);
			pc.gainPoints(calcLevelupCost(attribute));
			return true;
		}
		return false;
	}

	public int getLevelups(PretenderAttribute attribute) {
		return attributes.get(attribute)-attribute.getBase_value();
	}


	private void levelUp(PretenderAttribute attribute) {
		int current_level = attributes.get(attribute);
		attributes.put(attribute, current_level + 1);
	}

	private int calcLevelupCost(PretenderAttribute attribute) {
		int current_level = getLevelups(attribute);
		int cost = PretenderConfiguration.calcScalingCost(current_level);
		return cost;
	}
}
