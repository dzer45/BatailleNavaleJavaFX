package cad.bataillenavale.controller;

import cad.bataillenavale.model.BatailleNavale;

public abstract class BatailleNavaleController {

	protected BatailleNavale model;
	
	public BatailleNavaleController(BatailleNavale model){
		this.model = model;
	}
}
