package cad.bataillenavale.model.player;

import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;

public abstract class Player {

	protected String name;
	private Player opponent;
	private Map map;

	/**
	 * 
	 * @return le nom du joueur
	 */
	public String getName() {
		return name;
	}

	/**
	 * Modifier le nom du joueur
	 * @param name le nom du joueur
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return l'adveraire du joueur
	 */
	public Player getOpponent() {
		return opponent;
	}

	/**
	 * Modifier l'adveraire du joueur
	 * @param opponent l'adveraire du joueur
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
	/**
	 * Tirer sur la grille de l'adversaire
	 * @param x 
	 * @param y
	 * @throws MapException si la case tirée sort de la map
	 * @return vrai si une case à été détruite
	 */
	public boolean shoot(int x, int y) throws MapException {
		if (getMap().isReacheable(x, y)) {
			if (!this.getOpponent().getMap().isPlayed(x, y)) {
				return this.getOpponent().getMap().shoot(x, y);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return la grille du joueur
	 */
	public Map getMap() {
		return map;
	}

	
	/**
	 * Modifier la grille du joueur
	 * @param map la grille du joueur
	 */
	public void setMap(Map map) {
		this.map = map;
	}
	
	/**
	 * Initialiser la map du joueur
	 * @param length taille de la grille
	 * @return la grille
	 */
	public Map initMap(int length){
		setMap(new Map(length));
		return map;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
