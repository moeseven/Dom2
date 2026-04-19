package moesgames.dom2.models;

public class PretenderMagicPaths extends MagicPaths{
	
	
	
	public PretenderMagicPaths() {
		super();
		getMagic_paths().put(MagicPath.Air, 0);
		getMagic_paths().put(MagicPath.Astral, 0);
		getMagic_paths().put(MagicPath.Blood, 0);
		getMagic_paths().put(MagicPath.Death, 0);
		getMagic_paths().put(MagicPath.Earth, 0);
		getMagic_paths().put(MagicPath.Fire, 0);
		getMagic_paths().put(MagicPath.Glamour, 0);
		getMagic_paths().put(MagicPath.Nature, 0);
		getMagic_paths().put(MagicPath.Water, 0);
	}


	
	public boolean increase(PretenderConfiguration pc, MagicPath path) {
		if (pc.payPoints(calcLevelupCost(path))) {
			levelUp(path);
			return true;
		}
		return false;
	}
	
	public boolean decrease(PretenderConfiguration pc, MagicPath path) {
		int current_level = getMagic_paths().get(path)-path.getBase_value();
		if (current_level > 0) {
			getMagic_paths().put(path, current_level - 1);
			pc.gainPoints(calcLevelupCost(path));
			return true;
		}
		return false;
	}


	private void levelUp(MagicPath path) {
		int current_level = getMagic_paths().get(path);
		getMagic_paths().put(path, current_level + 1);
	}

	private int calcLevelupCost(MagicPath path) {
		int current_level = getMagic_paths().get(path);
		int cost = PretenderConfiguration.calcScalingCost(current_level);
		return cost;
	}
	

}
