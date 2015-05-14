package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;

public class PlayController extends BatailleNavaleController {
	
	public PlayController(BatailleNavale model) {
		super(model);
	}

	public void notifyShoot(int x, int y){
		try {
			
			model.shoot(x, y);
			
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
