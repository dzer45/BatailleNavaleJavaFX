package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.view.PlayView;

public class ConfigController {


	private BatailleNavale model;
	
	public ConfigController(BatailleNavale model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}

	public void notifyAdd(int x, int y, Maritime m) throws MapException {
		model.addMaritime(model.getPlayer(), x, y, m);
	}

	public void notifyFinish() {
		model.addEmptyCases(model.getPlayer());
		model.addEmptyCases(model.getIA()); // ----------
		PlayView gameView = new PlayView(model);
		gameView.show(model.getPrimaryStage()); 
	}

}
