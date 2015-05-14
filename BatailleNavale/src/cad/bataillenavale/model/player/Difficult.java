package cad.bataillenavale.model.player;

public interface Difficult {

	public static final String EASY = "Facile", MEDIUM = "Moyen", HARD = "Difficile";
	
	public void addMaritime(String maritimeName);
	public void shoot(Player p);
	
}
