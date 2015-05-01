package cad.bataillenavale;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.view.ConfigView;
import cad.bataillenavale.view.StartView;

public class Main extends Application {
	private BatailleNavale modelBataille;
	private BorderPane rootLayout;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.modelBataille = new BatailleNavale();
		modelBataille.setStage(primaryStage);

		StartView startView = new StartView(modelBataille, primaryStage);
		
		// startView.show(this.primaryStage);
		// initRootLayout();
		// showStart();

		// TESTER LA CONFIGVIEW
/**
		 modelBataille.start(10, 10, "XVI");
		 ConfigView cView = new ConfigView(modelBataille, primaryStage);
		 cView.show(this.primaryStage);
		 **/
		// TESTER LA PLAYVIEW
		/**
		 * modelBataille.start(10, 10, "XVI");
		 * 
		 * try { Maritime m = (Maritime)
		 * EpoqueManager.getInstance().getEpoque("XVI").getMaritime("Galion");
		 * modelBataille.addMaritime(modelBataille.getPlayer(), 0, 0, m);
		 * modelBataille.addEmptyCases(modelBataille.getPlayer()); m =
		 * (Maritime)
		 * EpoqueManager.getInstance().getEpoque("XVI").getMaritime("Galion");
		 * modelBataille.addMaritime(modelBataille.getIA(), 4, 4, m);
		 * modelBataille.addEmptyCases(modelBataille.getIA());
		 * 
		 * } catch (MapException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } PlayView gameView = new
		 * PlayView(modelBataille); gameView.show(this.primaryStage);
		 **/

	}

	private void initRootLayout() {
		// TODO Auto-generated method stub
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showStart() {
		// TODO Auto-generated method stub
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootOverview.fxml"));
			AnchorPane rootOverview = (AnchorPane) loader.load();

			// Set image overview into the center of root layout.
			rootLayout.setCenter(rootOverview);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
