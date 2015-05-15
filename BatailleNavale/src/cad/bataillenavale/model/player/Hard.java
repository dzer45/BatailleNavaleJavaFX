package cad.bataillenavale.model.player;

import java.awt.Point;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;

public class Hard extends Difficult{
	
	public Hard(BatailleNavale model){
		super(model);
	}
	
	@Override
	public void shoot(Player p) {
		if (model.isGameFinished())
			return;
		
		try {
			
			Point whereToShoot = null;
			
			int touch = random.nextInt(1);
			
			if(touch == 1)
			{
				whereToShoot = getMaritimeShoot();
			}
			else
			{
				whereToShoot = getRandomShoot();
			}
			
			boolean touched = p.shoot(whereToShoot.x, whereToShoot.y);
			
			if(touched){ // IA touched a CaseMaritime, so it shoots again 
				shoot(p);
				return;
			}
			
			model.setCurrentPlayer(model.getIA().getOpponent()); // if missed lets the opponent to play

		
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Point getMaritimeShoot() throws MapException{
		Map myMap = model.getMapIA();
		Map playerMap = model.getMapPlayer();
		
		for(Maritime m : playerMap.getMaritimes())
		{
			for (int i = m.getPoint().x; i < m.getLength(); i++) {
				for (int j = m.getPoint().y; j < m.getLength(); j++) {
					if(myMap.isReacheable(i, j) && (!playerMap.isPlayed(i, j)))
						return new Point(i, j);
				}
			}
		}
		
		return null;
	}
}
