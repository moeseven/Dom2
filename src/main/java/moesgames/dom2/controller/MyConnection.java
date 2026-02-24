package moesgames.dom2.controller;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;

public class MyConnection implements Runnable {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ConnectionPair connectionPair;
	private ServerWithConnections server;
	private boolean closed;
	
	

	public boolean isClosed() {
		return closed;
	}

	public void setConnectionPair(ConnectionPair connectionPair) {
		this.connectionPair = connectionPair;
	}

	public MyConnection(Socket socket, ServerWithConnections server) {
		super();
		this.socket = socket;
		this.server = server;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} 
		catch (StreamCorruptedException e) {
			close();
			server.anotherPreventedAttack(socket.getInetAddress(), e);
		}
		catch (EOFException e) {
			close();
			server.anotherPreventedAttack(socket.getInetAddress(), e);
		}
		catch (SocketException e) {
			close();
			server.anotherPreventedAttack(socket.getInetAddress(), e);
		}
		catch (IOException e) {
			close();
			e.printStackTrace();
		}
		if (!closed) {
			new Thread(this).start();
		}		
	}

	@Override
	public void run() {
		while (socket.isConnected() && !closed) {
			try {
				Object data = in.readObject();	
				handleInput(data);			
			} catch (Exception e) {				
				tryCloseConnectionPair();
				break;
			}
		}
		close();
	}

	private void handleInput(Object data) {
		if (closed) {
			return;
		}
		if (data instanceof ProbingMessage) {
			server.log("probing echo received");
			return;
		}
		if (connectionPair == null) {
			close();
			return;
		}

		if (data instanceof ControlMessage) {
			connectionPair.getPartner(this).sendObject(data);
			System.out.println("message forwarded");
			return;
		}
		if (data instanceof PlayerInfoMessage) {
			connectionPair.loginPlayerInfo((PlayerInfoMessage) data, this);
			return;
		}

	}

	private void tryCloseConnectionPair() {
		if (connectionPair != null) {
			connectionPair.close();
		}
		close();
	}

	public void close() {
		if (!closed) {
			try {
				closed = true;
				socket.close();
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				server.log("connection from " + socket.getInetAddress().getHostAddress() + " was closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean sendObject(Object packet) {
		if (closed) {
			return false;
		}
		try {
			out.writeObject(packet);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean areYouStillThere() {
		if (closed) {
			return false;
		}
		if (sendObject(new ProbingMessage())) {
			return true;
		}
		return false;
	}

}
