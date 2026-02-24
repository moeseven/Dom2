package moesgames.dom2.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;

import moesgames.dom2.tools.MyConcurrentCollectionOperator;


public class ServerWithConnections implements Runnable {

	private int port;
	private ServerSocket serverSocket;
	private MyConnection waitingConnection;
	private boolean someOneWaiting;
	private LinkedList<ConnectionPair> linkedConnections = new LinkedList<>();
	private String output = "";
	private boolean running = false;
	private int preventedAttacks = 0;
	
	public static String badIpListPath = "./WDServerlog/badIps.log"; 

	private int socketRepairAttempts = 0;

	public void setGui(HostServerGui gui) {
		this.gui = gui;
	}

	private HostServerGui gui;

	public void log(String log) {
		output = log + "\n" + output;
		System.out.println(log);
		updateGui();
	}

	private void updateGui() {
		if (gui != null) {
			gui.refresh();
		}
	}

	public String getOutput() {
		return output;
	}

	public static void main(String[] args) {
		new ServerWithConnections(2003).start();
	}

	public ServerWithConnections(int port) {
		super();
		this.port = port;
		createServerSocket();
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			log("host server started from " + InetAddress.getLocalHost().getHostAddress().trim());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		running = true;
		while (running) {
			try {
				Socket socket = serverSocket.accept();
				log("New client connected from " + socket.getInetAddress().getHostAddress());
				initSocket(socket);
			} catch (SocketException e) {
				// try recreate the socket				
				createServerSocket();
				System.out.println("socket repaired");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		shutdown();
	}

	private void createServerSocket() {
		if (socketRepairAttempts < 100) {
			try {
				serverSocket = new ServerSocket(port);
				socketRepairAttempts = 0;
			} catch (IOException e) {
				socketRepairAttempts++;
				e.printStackTrace();
			}
		}

	}

	private void initSocket(Socket socket) {
		if (someOneWaiting) {
			if (waitingConnection.areYouStillThere()) {
				someOneWaiting = false;
				MyConnection connection = new MyConnection(socket, this);
				if (!connection.isClosed()) {
					linkedConnections.add(new ConnectionPair(waitingConnection, connection, this));
				}				
				cleanLinkedConnections();
				log("running games: " + linkedConnections.size());
			} else {
				waitForAMatch(socket);
			}

		} else {
			waitForAMatch(socket);
		}
	}

	private void cleanLinkedConnections() {
		MyConcurrentCollectionOperator<LinkedList<ConnectionPair>, ConnectionPair> mco = new MyConcurrentCollectionOperator<LinkedList<ConnectionPair>, ConnectionPair>() {

			@Override
			public void operate(ConnectionPair operand) {
				if (!operand.isAlive()) {
					operand.close();
				}
			}
		};
		mco.operateCollection(linkedConnections);
	}

	public void waitForAMatch(Socket socket) {
		waitingConnection = new MyConnection(socket, this);
		if (!waitingConnection.isClosed()) {
			someOneWaiting = true;
		}		
	}

	public void shutdown() {
		running = false;
		for (Iterator iterator = linkedConnections.iterator(); iterator.hasNext();) {
			ConnectionPair connectionPair = (ConnectionPair) iterator.next();
			connectionPair.setAlive(false);
		}
		cleanLinkedConnections();
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class InitSocketThread extends Thread {

		Socket socket1;

		public InitSocketThread(Socket socket) {
			super();
			this.socket1 = socket;
		}

		@Override
		public void run() {
			initSocket(socket1);
		}

	}
	
	public static void logBadIP(String ip) {
        File file = new File(badIpListPath);

        try {
            // 1. Ensure the parent directories exist
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean dirsCreated = parentDir.mkdirs();
                if (dirsCreated) {
                    System.out.println("Created directories: " + parentDir.getAbsolutePath());
                }
            }

            // 2. Ensure the file exists
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    System.out.println("Created file: " + file.getAbsolutePath());
                }
            }

            // 3. Append text with newline
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.newLine();
                writer.write(ip);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public void dropConnection(ConnectionPair connectionPair) {
		linkedConnections.remove(connectionPair);

	}

	public void anotherPreventedAttack(InetAddress ip, Exception e) {
		preventedAttacks++;
		logBadIP(ip.toString());
		System.out.println("shut down (malicious) connection attempt from "+ip+" -"+e.toString()+" ("+preventedAttacks+" attacks since the server is live)");
	}
}
