package cad.bataillenavale.model.player;

import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;

public abstract class Player {

	protected String name;
	private Player opponent;
	private Map map;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Player getOpponent() {
		return opponent;
	}

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
	
	public Map getMap() {
		return map;
	}

	public Map setMap(Map map) {
		this.map = map;
		return map;
	}
	
	public Map initMap(int length){
		return setMap(new Map(length));
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
