package cad.bataillenavale.model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Set;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.player.Difficult;
import cad.bataillenavale.model.player.Easy;
import cad.bataillenavale.model.player.Hard;
import cad.bataillenavale.model.player.Human;
import cad.bataillenavale.model.player.IA;
import cad.bataillenavale.model.player.Medium;
import cad.bataillenavale.model.player.Player;

public class BatailleNavale extends Observable {
	private Player currentPlayer = null;
	private Epoque currentEpoque;
	private Human player = new Human();
	private IA iA = new IA();
	private java.util.Map<Player, Map> maps = new HashMap<Player, Map>();
	private int length;

	public void start(int length, String epoque, String difficult) {
		this.length = length;

		player.setOpponent(iA);
		iA.setOpponent(player);
		currentPlayer = player;

		Map grillePlayer = player.initMap(length);
		maps.put(player, grillePlayer);
		
		Map grilleIA =  iA.initMap(length);
		maps.put(iA, grilleIA);
		
		Difficult dif = null;
		switch (difficult) {
		case "Easy":
			dif = new Easy(this);
			break;
		case "Medium":
			dif = new Medium(this);
			break;
		case "Hard":
			dif = new Hard(this);
			break;
		}
		iA.setDifficult(dif);
		
		this.currentEpoque = EpoqueManager.getInstance().getEpoque(epoque);
	}

	public void addMaritime(int x, int y, String maritimeName)
			throws MapException {

		Maritime m = (Maritime) EpoqueManager.getInstance()
				.getEpoque(currentEpoque.getName()).cloneMaritime(maritimeName); // clone

		// player adds the maritime
		maps.get(player).addMaritime(x, y, m);

		// IA adds the same maritime
		iA.addMaritime(maritimeName);

		setChanged();
		notifyObservers(m);
	}

	public void addEpoque(Epoque e) {
		EpoqueManager.getInstance().addEpoque(e);
	}

	public void removeEpoque(String epoqueName) {
		// TODO DAO
		EpoqueManager.getInstance().removeEpoque(epoqueName);
	}

	public void shoot(int x, int y) throws MapException {
		currentPlayer.shoot(x, y);
		currentPlayer = currentPlayer.getOpponent();
		if(currentPlayer == iA)
			iA.shoot();
		setChanged();
		notifyObservers();
	}

	public boolean isMyTurn(Player p) {
		return currentPlayer.equals(p);
	}

	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getPlayer() {
		return player;
	}

	public Player getIA() {
		return iA;
	}

	public Map getMapPlayer() {
		return maps.get(player);
	}

	public Map getMapIA() {
		return maps.get(iA);
	}

	public Epoque getCurrentEpoque() {
		return currentEpoque;
	}

	public void addEmptyCases(Player player) {
		maps.get(player).addEmptyCases();
		setChanged();
		notifyObservers();
	}
	
	public int getLength() {
		return length;
	}

	public boolean isGameFinished() {
		Map mapPlayer = maps.get(player);
		Map mapIA = maps.get(iA);
		return mapPlayer.getMaritimeRemaining() == 0
				|| mapIA.getMaritimeRemaining() == 0;
	}

	public Epoque getEpoque(String epoqueName) {
		return EpoqueManager.getInstance().getEpoque(epoqueName);
	}

	public Set<String> getEpoques() {
		return EpoqueManager.getInstance().getEpoques();
	}

	public void addMaritime(String epoqueName, String maritimeName,
			String length, String width, String power) {
		// TODO Auto-generated method stub
		EpoqueManager.getInstance().addMaritime(epoqueName, maritimeName,
				length, width, power);
	}
}
