package cad.bataillenavale.model.map;

public abstract class Case {
	
	// mis a jour sur la grille de l'adversaire  (si on peut tirer sur la case)
	public enum State { NOTPLAYED, TOUCHED, MISSED }
	
	protected State s;
	// nombre de bateaux qui peuvent atteindre la case 
	private int nbMaritimeReach = 0;
	
	protected Map map;
	
	protected Case(Map map){
		this.s = State.NOTPLAYED;
		
		this.map = map;
	}
	
	public State getState(){
		return s;
	}
	
	public void incReacheable(){
		nbMaritimeReach++;
	}
	
	public void descReacheable(){
		nbMaritimeReach--;
	}
	
	public int getReachable(){
		return nbMaritimeReach;
	}
	
	public boolean isReachable(){
		return nbMaritimeReach > 0;
	}
		
	protected abstract void shoot();
}