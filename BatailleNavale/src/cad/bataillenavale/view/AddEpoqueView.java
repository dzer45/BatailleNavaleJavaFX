package cad.bataillenavale.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.EditController;

public class AddEpoqueView {

	private Stage stage;
	private Scene scene;
	private EditController editController;
	
	public AddEpoqueView(Stage stage){
		this.stage = stage ;
		editController = new EditController();
		
		buildFrame();
	}

	private void buildFrame() {
		BorderPane borderPane = new BorderPane();
		
		VBox vb = new VBox();
		
		HBox  hbText = new HBox();
		
		Label text = new Label("Nom de l'époque : ");
		TextField ta = new TextField();
		Button btnOK = new Button("OK");
		btnOK.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				editController.addEpoque(ta.getText());
				editController.returnToEditView(stage);
			}
		});
		
		HBox hbBtns = new HBox();
		
		Button btnAnnuler = new Button("Annuler");
		btnAnnuler.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				editController.returnToEditView(stage);
			}
		});
		
		hbText.getChildren().add(text);
		hbText.getChildren().add(ta);
		
		hbBtns.getChildren().add(btnOK);
		hbBtns.getChildren().add(btnAnnuler);
		
		vb.getChildren().add(hbText);
		vb.getChildren().add(hbBtns);
		
		borderPane.setCenter(vb);
		
		scene = new Scene(borderPane);
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.show();
	}
}
