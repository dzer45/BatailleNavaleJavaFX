package cad.bataillenavale.controller;

import java.util.Random;

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

	public void notifyAdd(int x, int y, Maritime m) throws MapException {
		model.addMaritime(model.getPlayer(), x, y, m);
	}

	public void notifyFinish(Stage stage) {
		model.addEmptyCases(model.getPlayer());
		model.addEmptyCases(model.getIA());
		PlayView gameView = new PlayView(model,stage);
	}

	public void configIA() throws MapException {
		// TODO Auto-generated method stub
		Random random = new Random();
        int x = random.nextInt(model.getLength());
        int y = random.nextInt(model.getWidth());
        model.addMaritime(model.getIA(), x, y, (Maritime) EpoqueManager
				.getInstance()
				.getEpoque(model.getCurrentEpoque().getName())
				.getMaritime("Galion"));
	}

}
