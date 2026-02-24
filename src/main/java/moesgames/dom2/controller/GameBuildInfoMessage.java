package moesgames.dom2.controller;

import java.util.Iterator;
import java.util.LinkedList;

public class GameBuildInfoMessage extends Message {
	
	
	
	public LinkedList<PlayerInfoMessage> getPlayerInfo() {
		return playerInfo;
	}
	public int getSeed() {
		return seed;
	}
	LinkedList<PlayerInfoMessage> playerInfo;
	int seed;
	public GameBuildInfoMessage(LinkedList<PlayerInfoMessage> playerInfo, int seed) {
		super();
		this.playerInfo = playerInfo;
		this.seed = seed;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("seed: ");
		sb.append(seed);
		for (Iterator iterator = playerInfo.iterator(); iterator.hasNext();) {
			PlayerInfoMessage playerInfoMessage = (PlayerInfoMessage) iterator.next();
			sb.append(playerInfoMessage.toString());
		}
		return sb.toString();
	}
	
	
	

}
