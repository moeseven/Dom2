package moesgames.dom2.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import moesgames.dom2.localisation.Localisations;
import moesgames.dom2.tools.MyOperation;

public class GameClient extends NetworkingEntity {

	protected GameController controller;
	protected int port;
	protected ObjectOutputStream out;
	protected ObjectInputStream in;
	private Socket socket;
	private String host;

	private GuiMaster guiMaster;
	private GameSettings settings;

	public GameClient(String host, GameSettings settings) {
		this.port = 2003;
		this.host = host;
		this.settings = settings;
	}

	private int playerId;

	public void connect() {
		System.out.println("Connecting to host " + host + " on port " + port + ".");
		try {
			socket = new Socket(host, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + host);
			JOptionPane.showMessageDialog(null, Localisations.TheServerIsNotReachable.getName(settings.getLanguage()),
					"Warning", JOptionPane.WARNING_MESSAGE);
			return;
		} catch (IOException e) {
			System.err.println("Unable to get streams from server");
			JOptionPane.showMessageDialog(null, Localisations.TheServerIsNotReachable.getName(settings.getLanguage()),
					"Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		new Thread(this).start();
	}

	public void run() {
		active = true;
		try {
			Message message = null;
			Object receivedObject = null;
			// controller.getMatchHandler().connectToServerSuccess();
			while (!((message = (Message) in.readObject()) instanceof PlayerIdMessage)) {
				out.writeObject(new ProbingMessage());
				System.out.println("connection tested");
			}

			// receive control
			playerId = ((PlayerIdMessage) message).getPlayerId();
			sendInfo();
			// receive game
			Game game = null;
			try {
				GameBuildInfoMessage gameInfo = (GameBuildInfoMessage) in.readObject();
				if (true) {// gameInfo.isCompatible()
					game = Game.generate(gameInfo);
					game.start();// controller.getMatchHandler().getOnGameOver()
					controller = new GameController(game.getPlayerById(playerId), game, new MyOperation<ControlMessage>() {
						
						@Override
						public void operate(ControlMessage message) {
							send(message);
							
						}
					});
					guiMaster = GuiMaster.generate(controller,settings);
					System.out.println("Client: <- " + gameInfo.toString());
					//System.out.println("Client: <- " + game.toString());
				} else {
					System.out.println("game version mismatch");
					shutDown();
					guiMaster.oldVersionAlert();
				}

			} catch (SocketException e) {
				// wrong version
				guiMaster.oldVersionAlert();
				shutDown();
				return;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				shutDown();
			}
			if (!socket.isClosed()) {
				guiMaster.activateGui();
			}

			while (!socket.isClosed()) {
				try {
					receivedObject = in.readObject();
				} catch (ClassNotFoundException e) {
					break;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
				if (receivedObject instanceof EndConnectionMessage) {
					break;
				}
				if (receivedObject instanceof ControlMessage) {
					ControlMessage control_message = (ControlMessage) receivedObject;
					controller.sendControlMessage(control_message);
					System.out.println("Client <- control message: " + message);
				}
			}
			shutDown();
		} catch (SocketException e) {
			// System.out.println("client terminated with exception");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		active = false;
	}

	protected void sendInfo() throws IOException {
		PlayerInfoMessage message = settings.generateInfoMessage();
		out.writeObject(message);
		System.out.println("sending info");
		System.out.println(message.toString());
	}

	public void send(ControlMessage msg) {
		try {
			out.writeObject(msg);
			System.out.println("Client: -> " + msg.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isActive() {
		return active;
	}

	private boolean active = false;

	@Override
	public void shutDown() {
		System.out.println("shutting down game client");
		try {
			out.close();
			in.close();
			socket.close();
			active = false;
			System.out.println("client terminated");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
