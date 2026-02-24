package moesgames.dom2.controller;

import moesgames.dom2.tools.MyOperation;

public class GameController {
	
	
	
	public Game getGame() {
		return game;
	}
	
	private Player player;
	private Game game;

	private MyOperation<ControlMessage> sendOutOperation;

	public GameController(Player player, Game game, MyOperation<ControlMessage> sendOutOperation) {
		super();
		this.player = player;
		this.game = game;
		this.sendOutOperation = sendOutOperation;
	}

	

	private void resolve(ControlMessage message) {
		game.process(message);
	}
	
	public boolean sendControlMessage(ControlMessage message) {
		if (game.isValidMessage(message)) {
			if (isFromMyPlayer(message)) {
				sendOutOperation.operate(message);
			}
			resolve(message);
			return true;
		}
		return false;
	}



	private boolean isFromMyPlayer(ControlMessage message) {
		return game.getPlayerById(message.getSender_id()) == player;
	}

	public boolean sendControlMessage(ControlmessageType messageType, int index1, int index2) {
		return sendControlMessage(new ControlMessage(messageType, index1,index2, game.getPlayerId(player)));
	}


}
