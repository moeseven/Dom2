package moesgames.dom2.controller;

public abstract class NetworkingEntity extends Thread {
	protected boolean end = false;
	public abstract void shutDown();

}
