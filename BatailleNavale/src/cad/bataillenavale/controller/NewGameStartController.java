package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;

public class NewGameStartController {

	private BatailleNavale model;
	public NewGameStartController(BatailleNavale bn) {
		// TODO Auto-generated constructor stub
		this.model = bn;
	}
	public void notifyStart(String length, String epoque) {
		// TODO Auto-generated method stub
		model.start(Integer.parseInt(length), Integer.parseInt(length), epoque);
	}

}
