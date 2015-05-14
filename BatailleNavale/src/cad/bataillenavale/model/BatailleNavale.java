package cad.bataillenavale.model;

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
	private Epoque currentEpoque = null;
	private Human player = new Human();
	private IA iA = new IA();
	private int length;

	/**
	 * Commencer une nouvelle partie
	 * @param length taille de la grille
	 * @param epoque epoque à laquelle se déroule la partie
	 * @param difficult niveau de difficulté de la partie
	 */
	public void start(int length, String epoque, String difficult) {
		this.length = length;

		player.setOpponent(iA);
		iA.setOpponent(player);
		currentPlayer = player;

		player.initMap(length);
		iA.initMap(length);
		
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

	/**
	 * Ajouter un maritime a la grille
	 * @param x coordonnée en x où il faut ajouter le maritime
	 * @param y coordonnée en y où il faut ajouter le maritime
	 * @param maritimeName nom du maritime à ajouter
	 * @throws MapException si le maritime empiete sur un autre ou s'il sort de la grille 
	 */
	public void addMaritime(int x, int y, String maritimeName)
			throws MapException {

		Maritime m = (Maritime) EpoqueManager.getInstance()
				.getEpoque(currentEpoque.getName()).cloneMaritime(maritimeName); // clone

		// player adds the maritime
		player.getMap().addMaritime(x, y, m);

		// IA adds the same maritime
		iA.addMaritime(maritimeName);

		setChanged();
		notifyObservers(m);
	}

	/**
	 * Ajouter une époque
	 * @param e l'epoque à ajouter
	 */
	public void addEpoque(Epoque e) {
		EpoqueManager.getInstance().addEpoque(e);
	}

	/**
	 * Supprimer une époque
	 * @param e l'epoque à supprimer
	 */
	public void removeEpoque(String epoqueName) {
		// TODO DAO
		EpoqueManager.getInstance().removeEpoque(epoqueName);
	}

	/**
	 * Tirer sur la grille de l'adversaire (l'IA)
	 * @param x coordonnée en x ou il faut tirer
	 * @param y coordonnée en y ou il faut tirer
	 * @throws MapException si le tir sort de la grille
	 */
	public void shoot(int x, int y) throws MapException {
		boolean touched = currentPlayer.shoot(x, y);

		if(!touched)// if we don't touch a CaseMaritime we give the hand
			currentPlayer = currentPlayer.getOpponent();
		
		if(currentPlayer == iA)
			iA.shoot();
		
		setChanged();
		notifyObservers();
	}

	/**
	 * Permet à un joueur de savoir si c'est son tour de jouer
	 * @param p le joueur qui veut tirer
	 * @return vrai si c'est le cas
	 */
	public boolean isMyTurn(Player p) {
		return currentPlayer.equals(p);
	}

	/**
	 * Récupérer le joueur courant
	 * @return le joueur courant
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Modifier le joueur courant
	 * @param currentPlayer le nouveau joueur courant
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Récupérer le joueur
	 * @return le joueur
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Récupérer l'IA
	 * @return l'IA
	 */
	public Player getIA() {
		return iA;
	}

	/**
	 * Récupérer la grille du joueur
	 * @return la grille du joueur
	 */
	public Map getMapPlayer() {
		return player.getMap();
	}

	/**
	 * Récupérer la grille de l'IA
	 * @return la grille de l'IA
	 */
	public Map getMapIA() {
		return iA.getMap();
	}

	/**
	 * Récupérer l'époque courante
	 * @return l'epoque courante
	 */
	public Epoque getCurrentEpoque() {
		return currentEpoque;
	}

	/**
	 * Ajouter les EmptyCases une fois la configuration des maritimes effectuée
	 * 
	 */
	public void addEmptyCases() {
		player.getMap().addEmptyCases();
		iA.getMap().addEmptyCases();
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Récupérer la taille de la grille
	 * @return la taille de la grille
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Si le partie est finie
	 * @return vrai si c'est le cas
	 */
	public boolean isGameFinished() {
		return player.getMap().getMaritimeRemaining() == 0 || iA.getMap().getMaritimeRemaining() == 0;
	}

	/**
	 * Récupéré une epoque par son nom
	 * @param epoqueName le nom de l'epoque
	 * @return l'epoque en question
	 */
	public Epoque getEpoque(String epoqueName) {
		return EpoqueManager.getInstance().getEpoque(epoqueName);
	}

	/**
	 * Récupérer la liste des noms des époques
	 * @return la liste des noms des époques
	 */
	public Set<String> getEpoques() {
		return EpoqueManager.getInstance().getEpoques();
	}

	/**
	 * Ajouter un maritime à l'époque
	 * @param epoqueName le nom de l'époque
	 * @param maritimeName le nom du bateau
	 * @param length longueur du bateau
	 * @param width hauteur du bateau
	 * @param power puissance du bateau
	 */
	public void addMaritime(String epoqueName, String maritimeName,
			String length, String width, String power) {
		// TODO Auto-generated method stub
		EpoqueManager.getInstance().addMaritime(epoqueName, maritimeName,
				length, width, power);
	}
	
	/**
	 * Si les coordonnées sont à la portée des matitimes
	 * @param x coordonnée en x 
	 * @param y coordonnée en y
	 * @return vrai si c'est le cas
	 */
	public boolean isReachableShoot(int x, int y){
		boolean b = false;
		try {
			b = player.getMap().isReacheable(x, y);
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
	
	/**
	 * Si les coordonnées sont déjà jouées
	 * @param x coordonnée en x 
	 * @param y coordonnée en y
	 * @return vrai si c'est le cas
	 */
	public boolean isPlayedShoot(int x, int y){
		boolean b = false;
		try {
			b = iA.getMap().isPlayed(x, y);
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
}
