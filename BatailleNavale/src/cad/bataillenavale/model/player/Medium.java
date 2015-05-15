package cad.bataillenavale.model.player;

import java.awt.Point;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;

/**
 * Tir en croix
 * @author aziz
 *
 */
public class Medium extends Difficult {
	
	public Medium(BatailleNavale model){
		super(model);
	}

	@Override
	public void shoot(Player p) {
		if (model.isGameFinished())
			return;

		Point whereToShoot = getRandomShoot();

		try {
			
			boolean touched = p.shoot(whereToShoot.x, whereToShoot.y);
			
			if(touched) // IA touched a CaseMaritime, so it shoots again
			{  
				shootCross(p, whereToShoot);
				return;
			}
			

		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.setCurrentPlayer(model.getIA().getOpponent()); // if missed lets the opponent to play
	}

	public void shootCross(Player p, Point whereToCross) {
		
		Map myMap = model.getMapIA();
		Map playerMap = model.getMapPlayer();

		boolean reacheable = false;
		boolean alreadyPlayed = true;

		Point pnt = null;
		
		do {

			pnt = getCossShoot(whereToCross);

			try {

				reacheable = myMap.isReacheable(pnt.x, pnt.y);
				alreadyPlayed = playerMap.isPlayed(pnt.x, pnt.y);

			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while ((!reacheable) || (alreadyPlayed)); // we search for a place
													// wich we can reach it and
													// which is not already
													// played
		
		try {
			
			boolean touched = p.shoot(pnt.x, pnt.y);
			if(touched) // IA touched a CaseMaritime, so it shoots again
			{  
				shootCross(p, pnt);
				return;
			}
			
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Point getCossShoot(Point whereToCross){
		Point p = null;
		
		int rand = random.nextInt(3);
		
		switch(rand){
		case 0: p = new Point(whereToCross.x, whereToCross.y+1);
		case 1: p = new Point(whereToCross.x, whereToCross.y-1);
		case 2: p = new Point(whereToCross.x+1, whereToCross.y);
		case 3: p = new Point(whereToCross.x-1, whereToCross.y);
		}
		
		return p;
	}
}
