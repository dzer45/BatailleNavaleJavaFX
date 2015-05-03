package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;

public class NewGameStartController extends BatailleNavaleController {

	public NewGameStartController(BatailleNavale model) {
		super(model);
	}
	public void notifyStart(String length, String epoque) {
		model.start(Integer.parseInt(length), Integer.parseInt(length), epoque);
	}

}
