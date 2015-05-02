package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;

public class PlayController {

	private BatailleNavale model;
	
	public PlayController(BatailleNavale model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}

	public void notifyShoot(int x, int y){
		try {
			
			model.shoot(model.getPlayer(), x, y);
			
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
