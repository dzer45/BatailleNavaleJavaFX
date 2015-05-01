package cad.bataillenavale.controller;

import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.view.EditView;
import cad.bataillenavale.view.NewGameStartView;

public class StartController {

	private BatailleNavale model;
	
	public StartController(BatailleNavale model) {
		// TODO Auto-generated constructor stu
		this.model = model;
	}

	public void showPopUpGetParams(Stage stage){
		new NewGameStartView(model, stage);
	}
	
	public void showEdition(Stage stage){
		new EditView(stage);
	}
}
