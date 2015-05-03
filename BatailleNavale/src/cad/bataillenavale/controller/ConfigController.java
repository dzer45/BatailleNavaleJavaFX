package cad.bataillenavale.controller;

import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.view.PlayView;

public class ConfigController extends BatailleNavaleController {
	
	public ConfigController(BatailleNavale model) {
		super(model);
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
