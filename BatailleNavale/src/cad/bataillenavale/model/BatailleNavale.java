package cad.bataillenavale.model;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javafx.stage.Stage;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.player.Human;
import cad.bataillenavale.model.player.IA;
import cad.bataillenavale.model.player.Player;
import cad.bataillenavale.persistance.DAOFactory;
import cad.bataillenavale.persistance.EpoqueDAO;

public class BatailleNavale extends Observable {

	// create the required DAO Factory
	private DAOFactory xmlFactory =   DAOFactory.getDAOFactory(DAOFactory.XML);
	
	private Player currentPlayer = null;
	private Human player = new Human();
	private IA iA = new IA(this);
	
	private java.util.Map<Player, Map> maps = new HashMap<Player, Map>();
	private java.util.Map<Player, Player> opponents = new HashMap<Player, Player>();
	
	private Epoque currentEpoque;
	private Stage primaryStage;

	private int length, width;
	
	private EpoqueManager epoqueManager = EpoqueManager.getInstance();

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

		this.currentEpoque = epoqueManager.getEpoque(epoque);
	}

	public void addMaritime(int x, int y, String maritimeName) throws MapException {
		
		Maritime m = (Maritime)epoqueManager.getEpoque(currentEpoque.getName()).cloneMaritime(maritimeName); // clone
		
		// player adds the maritime
		maps.get(player).addMaritime(x, y, m); 

		// IA adds the same maritime
		iA.addMaritime(maritimeName);

		setChanged();
		notifyObservers();
	}

	public void addEpoque(Epoque e) {
		// Create a DAO
		EpoqueDAO epoqueDAO = xmlFactory.getEpoqueDAO();
		// create a new epoque
	    epoqueDAO.insertEpoque(e);
		epoqueManager.addEpoque(e);
	}
	
	public void removeEpoque(String epoqueName) {
		// TODO DAO
		epoqueManager.removeEpoque(epoqueName);
	}
	
	

	public void shoot(Player whoShooted, int x, int y) throws MapException {
		if (isMyTurn(whoShooted)) {
			
			if (maps.get(whoShooted).reacheableShoot(x, y)) {
				
				if (!maps.get(opponents.get(whoShooted)).isPlayed(x, y)) {
					maps.get(opponents.get(whoShooted)).shoot(x, y);
				} else {
					System.out.println(whoShooted+" case deja jouee");
					return;
				}
				
			} else {
				
				System.out.println(whoShooted+" pas atteingnable");
				return;
			}
			
		} else {

			System.out.println(whoShooted+" pas ton tour");
			return;
		}

		currentPlayer = getOpponent(currentPlayer);
		
		if (currentPlayer instanceof IA) {
			// IA shoots
			iA.shoot();
		}

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

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}
	
	/**
	 * S'il est possible de terminer la partie compte tenu des positions des bateaux
	 * @return false si il existe une case bateau non reachable dans la map du joueur ou dans la map de l'ia
	 */
	public boolean canFinishGame(){
		
		int nbCasesNotReachByPlayer = 0;
		int nbCasesNotReachByIA = 0;
		
		Map mapPlayer = maps.get(player);
		Map mapIA = maps.get(iA);
		
		// pour tout les maritimes de player je verifie s'ils sont atteignables par l'IA
		List<Maritime> maritimesPlayer = mapPlayer.getMaritimes();
		for(Maritime m : maritimesPlayer)
		{
			for (int i = m.getPoint().x; i < m.getPoint().x + m.getLength(); i++) {
				for (int j = m.getPoint().y; j < m.getPoint().y + m.getWidth(); j++) {
					try {
						
						if(!mapIA.reacheableShoot(i, j))
						{
							nbCasesNotReachByIA++;
						}
						
					} catch (MapException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		// pour tout les maritimes de l'IA je verifie s'ils sont atteignables par player
		List<Maritime> maritimesIA = mapIA.getMaritimes();
		for(Maritime m : maritimesIA)
		{
			for (int i = m.getPoint().x; i < m.getPoint().x + m.getLength(); i++) {
				for (int j = m.getPoint().y; j < m.getPoint().y + m.getWidth(); j++) {
					try {
						
						if(!mapPlayer.reacheableShoot(i, j))
						{
							nbCasesNotReachByPlayer++;
						}
						
					} catch (MapException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return nbCasesNotReachByPlayer == 0 || nbCasesNotReachByIA == 0;
	}
	
	public boolean isGameFinished(){
		Map mapPlayer = maps.get(player);
		Map mapIA = maps.get(iA);
		return mapPlayer.getMaritimeRemaining() == 0 || mapIA.getMaritimeRemaining() == 0;
	}
	
	public Epoque getEpoque(String epoqueName){
		return epoqueManager.getEpoque(epoqueName);
	}
	
	public Set<String> getEpoques(){
		return epoqueManager.getEpoques();
	}
}
