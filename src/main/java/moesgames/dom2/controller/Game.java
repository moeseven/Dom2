package moesgames.dom2.controller;

public interface Game {

	void process(ControlMessage message);

	boolean isValidMessage(ControlMessage message);

	int getPlayerId(Player player);

	Player getPlayerById(int sender_id);

	void start();

	static Game generate(GameBuildInfoMessage gameInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
