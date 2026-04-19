package moesgames.dom2.models;

import java.util.LinkedList;
import java.util.Random;

import moesgames.dom2.controller.ControlMessage;
import moesgames.dom2.controller.Game;
import moesgames.dom2.controller.Player;
import moesgames.dom2.models.unit.DomUnit;

public class DomGame implements Game {
	
	
	
	private LinkedList<Pretender> pretenders;	
	private Random randomizer;
	private Die gameDie;
	
	private Province[] provinces = new Province[3];

	public DomGame(LinkedList<Pretender> pretenders, int seed) {
		super();
		this.pretenders = pretenders;
		randomizer = new Random(seed);
		gameDie = new Die(randomizer);	
	}

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

	public boolean shareProvince(DomUnit caster, DomUnit target) {
		// TODO Auto-generated method stub
		return false;
	}

	public Die getDie() {
		return gameDie;
	}

	public void kill(DomUnit unit) {
		unit.onDeath();
		//TODO 
	}

}
