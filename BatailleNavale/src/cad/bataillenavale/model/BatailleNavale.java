package cad.bataillenavale.model;

import java.util.HashMap;
import java.util.Observable;

import javafx.stage.Stage;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.player.Human;
import cad.bataillenavale.model.player.IA;
import cad.bataillenavale.model.player.Player;

public class BatailleNavale extends Observable {

	private Player player = new Human(), iA = new IA(), currentPlayer = null;
	private java.util.Map<Player, Map> maps = new HashMap<Player, Map>();
	private java.util.Map<Player, Player> opponents = new HashMap<Player, Player>();
	private Epoque currentEpoque;
	private Stage primaryStage;
	
	private int length, width;

	public void start(int length, int width, String epoque) {
		this.length = length;
		this.width = width;
		
		opponents.put(player, iA);
		opponents.put(iA, player);
		currentPlayer = player;

		Map grillePlayer = new Map(length, width);
		maps.put(player, grillePlayer);
		Map grilleIA = new Map(length, width);
		maps.put(iA, grilleIA);

		this.currentEpoque = EpoqueManager.getInstance().getEpoque(epoque);
	}

	public void addMaritime(Player p, int x, int y, Maritime m)
			throws MapException {
		maps.get(p).addMaritime(x, y, m);
		setChanged();
		notifyObservers();
	}

	public void addEpoque(Epoque e) {
		EpoqueManager.getInstance().addEpoque(e);
	}

	public void shoot(Player whoShooted, int x, int y) {
		if (isMyTurn(whoShooted)) 
		{
		//	if (maps.get(whoShooted).reacheableShoot(x, y))
			{
				if (!maps.get(opponents.get(whoShooted)).isPlayed(x, y))
				{
					maps.get(opponents.get(whoShooted)).shoot(x, y);
					//currentPlayer = getOpponent(currentPlayer);
				}
		//		else
			//		System.out.println("case deja jouee");
			}
		//	else
			//	System.out.println("pas atteingnable");
		}
	//	else
	//		System.out.println("pas ton tour");
			
		setChanged();
		notifyObservers();
	}

	public Player getOpponent(Player p) {
		return opponents.get(p);
	}

	public boolean isMyTurn(Player p) {
		return currentPlayer.equals(p);
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

	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getWidth(){
		return width;
	}
}