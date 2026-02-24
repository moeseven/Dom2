package moesgames.dom2.controller;

import java.util.LinkedList;

public class ConnectionPair {
	
	public ServerWithConnections getServer() {
		return server;
	}
	
	public MyConnection getConnection1() {
		return connection1;
	}

	public MyConnection getConnection2() {
		return connection2;
	}

	public boolean isAlive() {
		return alive;
	}


	public void setAlive(boolean alive) {
		this.alive = alive;
	}


	private ServerWithConnections server;
	private MyConnection connection1;
	private MyConnection connection2;
	private boolean alive = true;
	public ConnectionPair(MyConnection connection1, MyConnection connection2, ServerWithConnections server) {
		super();
		this.server = server;
		this.connection1 = connection1;
		this.connection2 = connection2;
		connection1.setConnectionPair(this);
		connection2.setConnectionPair(this);
		connection1.sendObject(new PlayerIdMessage(0));
		connection2.sendObject(new PlayerIdMessage(1));
		server.log("setting up game ...");
	}
	
	public MyConnection getPartner(MyConnection connection) {
		if (connection == connection1) {
			return connection2;
		}else {
			return connection1;
		}
	}
	
	
	PlayerInfoMessage playerInfo1,playerInfo2;
	
	public void loginPlayerInfo(PlayerInfoMessage info,MyConnection connection) {
		
		if (connection == connection1) {
			playerInfo1 = info;
		}else {
			playerInfo2 = info;
		}
		if (playerInfo1 !=null && playerInfo2 != null) {
			if (playerInfo1.getGameVersion() == playerInfo2.getGameVersion()) {
				int seed = (int) (Math.random() * Integer.MAX_VALUE);
				LinkedList<PlayerInfoMessage> players = new LinkedList<PlayerInfoMessage>();
				players.add(playerInfo1);
				players.add(playerInfo2);
				GameBuildInfoMessage gameInfo = new GameBuildInfoMessage(players, seed);
				connection1.sendObject(gameInfo);
				connection2.sendObject(gameInfo);
				server.log("game setup and sent out to clients");			
				server.log(gameInfo.toString());
			}else {
				GameBuildInfoMessage gameInfo = new GameBuildInfoMessage(new LinkedList<PlayerInfoMessage>(),0);
				connection1.sendObject(gameInfo);
				connection2.sendObject(gameInfo);		
				server.log("version mismatch: "+playerInfo1.getGameVersion() +" and "+ playerInfo2.getGameVersion());	
				close();
			}
			
		}
	}
	


	public void close() {
		if (connection1 != null) {
			connection1.close();
		}
		if (connection2 != null) {
			connection2.close();
		}
		server.dropConnection(this);
		alive = false;
	}
	
}
