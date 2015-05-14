package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import cad.bataillenavale.controller.NewGameStartController;
import cad.bataillenavale.model.BatailleNavale;

public class NewGameStartView extends BatailleNavaleView implements Observer{

	public NewGameStartView(BatailleNavale model, Stage stage) {
		super(stage, model, new NewGameStartController(model));
		buildFrame();
		model.addObserver(this);
	}

	private void buildFrame() {
		GridPane gridPane = addGridPane();
		root.setCenter(gridPane);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public GridPane addGridPane() {
	    GridPane grid = new GridPane();
	    grid.setHgap(10);
	    grid.setVgap(10);
	  //  grid.setPadding(new Insets(0, 10, 0, 10));

	    // Epoque in column 1-2, row 1
	    Text title = new Text("Configuration partie ! ");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
	    grid.add(title, 0, 0, 2, 1); 
	    
	    // Epoque in column 1-2, row 1
	    Text epoque = new Text("Epoque : ");
	    epoque.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    grid.add(epoque, 0, 1); 

	    // Taille in column 1-2, row 1
	    Text taille = new Text("Taille de grille : ");
	    taille.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    grid.add(taille, 0, 2);
	    
	    // Taille in column 1-2, row 1
	    Text difficult = new Text("Difficult : ");
	    difficult.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    grid.add(difficult, 0, 3);
	    
	    ComboBox<String> epoqueComboBox = new ComboBox<String>();
        epoqueComboBox.getItems().addAll(model.getEpoques());   

        epoqueComboBox.setValue((String)model.getEpoques().toArray()[0]);
        grid.add(epoqueComboBox, 1, 1);
        
	    ComboBox<String> sizeComboBox = new ComboBox<String>();
        sizeComboBox.getItems().addAll(
            "10",
            "20",
            "30"
        );   
        
        sizeComboBox.setValue("10");
        grid.add(sizeComboBox, 1, 2);
        
	    ComboBox<String> difficultComboBox = new ComboBox<String>();
        difficultComboBox.getItems().addAll(
            "Easy",
            "Medium",
            "Hard"
        );   
        
        difficultComboBox.setValue("Easy");
        grid.add(difficultComboBox, 1, 3);

	    // ButtonOK  in column 1-2, row 3
	    Button buttonOk = new Button();
	    buttonOk.setText("Valider");		
	    buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				NewGameStartController newGameStartController = (NewGameStartController) controller;
				newGameStartController.notifyStart(sizeComboBox.getValue().toString(),epoqueComboBox.getValue().toString(),difficultComboBox.getValue().toString());
				new ConfigView(model, stage);
			}
		});
	    grid.add(buttonOk, 1, 4, 2, 1);

	    grid.setAlignment(Pos.CENTER);
	    return grid;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
