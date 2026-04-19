package moesgames.dom2.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class MagicPaths implements Serializable{
	
	
	
	public MagicPaths() {
		super();
	}

	public HashMap<MagicPath, Integer> getMagic_paths() {
		return magic_paths;
	}

	private HashMap<MagicPath, Integer> magic_paths = new HashMap<MagicPath, Integer>();

	public MagicPaths add(MagicPath key, int m) {
		if (magic_paths.containsKey(key)) {
			m += magic_paths.get(key);
		}
		magic_paths.put(key, m);
		return this;
	}
	
	public int bonusOnSpell(MagicPaths spell_paths) {
		int bonus = 0;
		for (Entry<MagicPath, Integer> entry : spell_paths.getMagic_paths().entrySet()) {
			MagicPath path = entry.getKey();
    	    Integer level = entry.getValue();
    	    if (level < getMagic_paths().get(path)) {
    	    	bonus = -1;
				break;
			}
    	    bonus += getMagic_paths().get(path)-level;
		}
		return bonus;
	}

}
