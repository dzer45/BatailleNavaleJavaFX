package cad.bataillenavale.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.EditController;
import cad.bataillenavale.model.BatailleNavale;

public class AddEpoqueView extends BatailleNavaleView {
	
	public AddEpoqueView(BatailleNavale model,Stage stage){
		super(stage, model, new EditController(model));
		
		buildFrame();
	}

	private void buildFrame() {
		
		VBox vb = new VBox();
		
		HBox  hbText = new HBox();
		
		Label text = new Label("Nom de l'époque : ");
		TextField ta = new TextField();
		Button btnOK = new Button("OK");
		btnOK.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EditController editController = (EditController)controller;
				editController.addEpoque(ta.getText());
				editController.returnToEditView(stage);
			}
		});
		
		HBox hbBtns = new HBox();
		
		Button btnAnnuler = new Button("Annuler");
		btnAnnuler.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				EditController editController = (EditController)controller;
				editController.returnToEditView(stage);
			}
		});
		
		hbText.getChildren().add(text);
		hbText.getChildren().add(ta);
		
		hbBtns.getChildren().add(btnOK);
		hbBtns.getChildren().add(btnAnnuler);
		
		vb.getChildren().add(hbText);
		vb.getChildren().add(hbBtns);
		
		GridPane gp = new GridPane();
		gp.add(vb, 1, 1);
		gp.setAlignment(Pos.CENTER);
		root.setCenter(gp);
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
