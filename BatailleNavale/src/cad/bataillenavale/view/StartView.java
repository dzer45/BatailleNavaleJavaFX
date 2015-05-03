package cad.bataillenavale.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import cad.bataillenavale.controller.StartController;
import cad.bataillenavale.model.BatailleNavale;

public class StartView extends BatailleNavaleView {

	public StartView(BatailleNavale model, Stage stage) {
		super(stage, model, new StartController(model));
		
		buildFrame();
	}

	private void buildFrame() {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		double screenWidth = bounds.getWidth() ;
		double screenHeight = bounds.getHeight() ;
		BorderPane borderPane = new BorderPane();
		final Menu menu1 = new Menu("Fichier");
		final Menu menu2 = new Menu("Options");
		final Menu menu3 = new Menu("Aide");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2, menu3);
		Button button = new Button();
		button.setText("Nouvelle partie");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StartController startController = (StartController) controller;
				startController.showPopUpGetParams(stage);
			}
		});
		Button button2 = new Button();
		button2.setText("Reprendre une partie");
		button2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		Button button3 = new Button();
		button3.setText("Edition");
		button3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				StartController startController = (StartController) controller;
				startController.showEdition(stage);
			}
		});
		Button button4 = new Button();
		button4.setText("Aide");
		button4.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		GridPane gridPane = new GridPane();

		VBox vbox = new VBox(10);
		vbox.getChildren().add(button);
		vbox.getChildren().add(button2);
		vbox.getChildren().add(button3);
		vbox.getChildren().add(button4);
		gridPane.getChildren().add(vbox);
		gridPane.setAlignment(Pos.CENTER);
		borderPane.setMinSize(screenWidth, screenHeight);
		borderPane.setTop(menuBar);
		borderPane.setCenter(gridPane);
		Image img = new Image("file:resources/images/menu.jpg",screenWidth+20, screenHeight+20, false, true);
		Background bgImg = new Background(new BackgroundImage(img, null, null, BackgroundPosition.CENTER, null));
		borderPane.setBackground(bgImg);
		scene = new Scene(borderPane);
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.setHeight(screenHeight);
		stage.setWidth(screenWidth);
		stage.show();
	}
}
