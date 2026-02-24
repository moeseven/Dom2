package moesgames.dom2.models;

import moesgames.dom2.controller.ControlMessage;
import moesgames.dom2.controller.Game;
import moesgames.dom2.controller.Player;

public class DomGame implements Game {

	@Override
	public void process(ControlMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidMessage(ControlMessage message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPlayerId(Player player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Player getPlayerById(int sender_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

}
