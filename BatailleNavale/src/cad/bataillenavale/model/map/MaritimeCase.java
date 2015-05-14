package cad.bataillenavale.model.map;

import cad.bataillenavale.model.exception.MapException;

public class MaritimeCase extends Case {

	private Maritime maritime;
	
	/**
	 * Constructeur
	 * @param map la grille de jeu
	 * @param m le maritime auquel la case appartient
	 */
	public MaritimeCase(Map map, Maritime m){
		super(map);
		this.maritime = m;
	}

	/**
	 * Le maritime auquel la case appartient
	 * @return le maritime
	 */
	public Maritime getMaritime() {
		return maritime;
	}

	@Override
	protected void shoot() {
		this.s = State.TOUCHED;
		this.map.touched++;
		this.maritime.descRemaningCase();
		if(this.maritime.isDestroyed())
		{
			try {
				this.map.updateReachableMap(maritime);
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}