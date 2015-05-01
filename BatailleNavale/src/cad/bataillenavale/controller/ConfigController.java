package cad.bataillenavale.controller;

import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.view.PlayView;

public class ConfigController {


	private BatailleNavale model;
	
	public ConfigController(BatailleNavale model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}

	public void notifyAdd(int x, int y, String maritimeSelected) throws MapException {
		model.addMaritime(x, y, maritimeSelected);
	}

	public void notifyFinish(Stage stage) {
		model.addEmptyCases(model.getPlayer());
		model.addEmptyCases(model.getIA());
		new PlayView(model,stage);
	}
}
