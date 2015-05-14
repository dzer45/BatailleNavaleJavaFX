package cad.bataillenavale.model.map;

public abstract class Case {
	
	// mis a jour sur la grille de l'adversaire  (si on peut tirer sur la case)
	public enum State { NOTPLAYED, TOUCHED, MISSED }
	
	protected State s;
	// nombre de bateaux qui peuvent atteindre la case 
	private int nbMaritimeReach = 0;
	
	protected Map map;
	
	/**
	 * Constructeur
	 * @param map Grille ou la case à été ajoutée
	 */
	protected Case(Map map){
		this.s = State.NOTPLAYED;
		
		this.map = map;
	}
	
	/**
	 * L'état de la case
	 * @return cette état
	 */
	public State getState(){
		return s;
	}
	
	/**
	 * Incrémenter le nombre de maritimes qui ont la case à leurs portée
	 */
	public void incReacheable(){
		nbMaritimeReach++;
	}
	
	/**
	 * Decrémenter le nombre de maritimes qui ont la case à leurs portée
	 */
	public void descReacheable(){
		nbMaritimeReach--;
	}
	
	/**
	 * 
	 * @return le nombre de maritimes qui ont la case à leurs portée
	 */
	public int getReachable(){
		return nbMaritimeReach;
	}
	
	/**
	 * Si au moins un maritime à la case dans sa portée
	 * @return
	 */
	public boolean isReachable(){
		return nbMaritimeReach > 0;
	}
	
	/**
	 * Lorsqu'un tir atteint la case
	 */
	protected abstract void shoot();
}