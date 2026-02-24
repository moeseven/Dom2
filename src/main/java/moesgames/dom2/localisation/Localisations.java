package moesgames.dom2.localisation;

import javax.swing.Icon;

public class Localisations {
	
	public static final LocalisationNameDescription water = new LocalisationNameDescription()
			.addLanguage(Language.english, "water", "the most important resource in the desert");
	
	public static final LocalisationNameDescription troop = new LocalisationNameDescription()
			.addLanguage(Language.english, "troop", "used to claim territory and resources");

	public static final LocalisationNameDescription harvest = new LocalisationNameDescription(water)
			.addLanguage(Language.english, "harvest", "gain 1 §, and harvest § from the desert");
	
	public static final LocalisationNameDescription deploy = new LocalisationNameDescription(troop)
			.addLanguage(Language.english, "deploy", "deploy 1 §, pay 1 water to repeat");
	
	public static final LocalisationNameDescription move = new LocalisationNameDescription(troop)
			.addLanguage(Language.english, "move", "move §s, pay 1 water to repeat");

	public static final LocalisationNameDescription done = new LocalisationNameDescription()
			.addLanguage(Language.english, "done", "ends current action or selection");

	public static final LocalisationNameDescription TheServerIsNotReachable = new LocalisationNameDescription()
			.addLanguage(Language.english, "the server is not reachable");
}
