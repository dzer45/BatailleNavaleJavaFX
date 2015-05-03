package cad.bataillenavale.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import cad.bataillenavale.controller.BatailleNavaleController;
import cad.bataillenavale.model.BatailleNavale;

public abstract class BatailleNavaleView {

	protected Stage stage;
	protected Scene scene;
	protected BatailleNavale model;
	protected BatailleNavaleController controller;
	
	public BatailleNavaleView(Stage stage, BatailleNavale model, BatailleNavaleController controller){
		this.stage = stage;
		this.model = model;
		this.controller = controller;
	}
	
	//abstract void buildFrame();
}
