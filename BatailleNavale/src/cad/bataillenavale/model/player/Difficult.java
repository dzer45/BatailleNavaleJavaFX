package cad.bataillenavale.model.player;

import java.awt.Point;
import java.util.Random;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;

public abstract class Difficult {

	public static final String EASY = "Facile", MEDIUM = "Moyen", HARD = "Difficile";
	
	protected BatailleNavale model;
	
	protected Random random;
	
	public Difficult(BatailleNavale model){
		this.model = model;
		random = new Random();
	}
	
	public void addMaritime(String maritimeName) {
		
		Map myMap = model.getMapIA();

		Maritime m = (Maritime) model.getEpoque(
				model.getCurrentEpoque().getName()).cloneMaritime(maritimeName); // clone

		boolean added = false;

		while (!added) {

			int x = random.nextInt(model.getLength() - m.getLength());
			int y = random.nextInt(model.getLength()- m.getLength());

			try {

				myMap.addMaritime(x, y, m);
				added = true;
				//System.out.println("IA added a maritime : " + x + " " + y);

			} catch (MapException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}
	
	public abstract void shoot(Player p);
	
	protected Point getRandomShoot(){
		
		Map myMap = model.getMapIA();
		Map playerMap = model.getMapPlayer();

		int x = 0, y = 0;
		boolean reacheable = false;
		boolean alreadyPlayed = true;

		do {

			x = random.nextInt(model.getLength());
			y = random.nextInt(model.getLength());

			try {

				reacheable = myMap.isReacheable(x, y);
				alreadyPlayed = playerMap.isPlayed(x, y);

			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while ((!reacheable) || (alreadyPlayed)); // we search for a place
													// wich we can reach it and
													// which is not already
													// played
		
		return new Point(x, y);
	}	
}
