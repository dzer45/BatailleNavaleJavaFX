package cad.bataillenavale.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.EditController;
import cad.bataillenavale.model.BatailleNavale;

public class AddMaritimeView {

	private Stage stage;
	private Scene scene;
	private EditController editController;
	private BatailleNavale model;
	
	private String epoque;
	
	public AddMaritimeView(BatailleNavale model,Stage stage, String epoque ){
		this.stage = stage ;
		this.epoque = epoque;
		editController = new EditController(model);
		
		buildFrame();
	}


	private void buildFrame() {
		BorderPane borderPane = new BorderPane();
		
		VBox vb = new VBox();
		
		HBox  nameHBox = new HBox();
		Label nameLabel = new Label("Nom : ");
		TextField nameTextField = new TextField();
		
		String[] values = new String[20];
		for (int i = 0; i < values.length; i++) {
			values[i] = (i+1)+"";
		}
		
		HBox  longueurHBox = new HBox();
		Label longueurLabel = new Label("Longueur : ");
	    ComboBox longueurComboBox = new ComboBox();
	    longueurComboBox.getItems().addAll(values);   
		
	    HBox  hauteurHBox = new HBox();
		Label hauteurLabel = new Label("Hauteur: ");
	    ComboBox hauteurComboBox = new ComboBox();
        hauteurComboBox.getItems().addAll(values);   
		
        HBox  puissanceHBox = new HBox();
		Label puissanceLabel = new Label("Puissance : ");
	    ComboBox puissanceComboBox = new ComboBox();
        puissanceComboBox.getItems().addAll(values);   
		
		
		Button btnOK = new Button("OK");
		btnOK.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String name = nameTextField.getText();
				String longueur = longueurComboBox.getValue().toString();
				String hauteur = hauteurComboBox.getValue().toString();
				String puissance = puissanceComboBox.getValue().toString();
				editController.addMaritime(epoque, name, longueur, hauteur, puissance);
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
		
		nameHBox.getChildren().add(nameLabel);
		nameHBox.getChildren().add(nameTextField);
		
		longueurHBox.getChildren().add(longueurLabel);
		longueurHBox.getChildren().add(longueurComboBox);
		
		hauteurHBox.getChildren().add(hauteurLabel);
		hauteurHBox.getChildren().add(hauteurComboBox);
		
		puissanceHBox.getChildren().add(puissanceLabel);
		puissanceHBox.getChildren().add(puissanceComboBox);
		
		hbBtns.getChildren().add(btnOK);
		hbBtns.getChildren().add(btnAnnuler);
		
		vb.getChildren().add(nameHBox);
		vb.getChildren().add(longueurHBox);
		vb.getChildren().add(hauteurHBox);
		vb.getChildren().add(puissanceHBox);
		vb.getChildren().add(hbBtns);
		
		borderPane.setCenter(vb);
		
		scene = new Scene(borderPane);
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.show();
	}
}
