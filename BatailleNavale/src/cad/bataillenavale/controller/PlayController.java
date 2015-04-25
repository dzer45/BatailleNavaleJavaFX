package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;

public class PlayController {

	private BatailleNavale model;
	
	public PlayController(BatailleNavale model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}

	public void notifyShoot(int x, int y){
		model.shoot(model.getPlayer(), x, y);
	}
}
