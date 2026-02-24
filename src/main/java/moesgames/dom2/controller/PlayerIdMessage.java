package moesgames.dom2.controller;

public class PlayerIdMessage extends Message {
	

	public int getPlayerId() {
		return playerId;
	}

	int playerId;
	
	public PlayerIdMessage(int playerId) {
		super();
		this.playerId = playerId;
	}

	
}
