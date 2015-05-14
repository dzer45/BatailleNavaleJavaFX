package cad.bataillenavale.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.StartController;
import cad.bataillenavale.model.BatailleNavale;

public class StartView extends BatailleNavaleView {

	public StartView(BatailleNavale model, Stage stage) {
		super(stage, model, new StartController(model));
		
		buildFrame();
	}

	private void buildFrame() {
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
				model = model.restore();
				new PlayView(model,stage);
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
		root.setCenter(gridPane);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
