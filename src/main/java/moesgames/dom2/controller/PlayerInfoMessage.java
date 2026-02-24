package moesgames.dom2.controller;

import java.awt.Color;

public class PlayerInfoMessage extends Message {
	
	

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	private Color color;
	private String name;
	public PlayerInfoMessage(Color color, String name) {
		super();
		this.color = color;
		this.name = name;
	}
	
	public int getGameVersion() {
		return 1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("name:");
		sb.append(name);
		sb.append(" color:");
		sb.append(color.toString());
		sb.append("game version:");
		sb.append(getGameVersion());
		return sb.toString();
	}
	
	
}
