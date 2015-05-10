package cad.bataillenavale;

import javafx.application.Application;
import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.view.StartView;

/**
 * 
 * @author Bachir Arif
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		BatailleNavale modelBataille = new BatailleNavale();
		modelBataille.setStage(primaryStage);
		new StartView(modelBataille, primaryStage);	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
