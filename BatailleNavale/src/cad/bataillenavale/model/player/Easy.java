package cad.bataillenavale.model.player;

import java.awt.Point;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;

/**
 * Tir aléatoire uniquement
 * @author aziz
 *
 */
public class Easy extends Difficult {

	public Easy(BatailleNavale model) {
		super(model);
	}

	@Override
	public void shoot(Player p) {
		if (model.isGameFinished())
			return;

		Point whereToShoot = getRandomShoot();

		try {
			
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

}
