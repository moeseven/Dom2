package moesgames.dom2.controller;

import moesgames.dom2.localisation.Language;

public interface GameSettings {

	Language getLanguage();

	PlayerInfoMessage generateInfoMessage();

}
