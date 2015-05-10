package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;

public class NewGameStartController extends BatailleNavaleController {

	public NewGameStartController(BatailleNavale model) {
		super(model);
	}
	public void notifyStart(String length, String epoque,String difficult) {
		model.start(Integer.parseInt(length), epoque,difficult);
	}

}
