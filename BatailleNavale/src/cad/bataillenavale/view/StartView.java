package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import cad.bataillenavale.controller.StartController;
import cad.bataillenavale.model.BatailleNavale;

public class StartView implements Observer{

	private BatailleNavale model ;
	private Scene scene ;
	
	private StartController startController;
	public StartView(BatailleNavale modelBataille) {
		// TODO Auto-generated constructor stub
		this.model = modelBataille;
		startController = new StartController(model);
		
		
		BorderPane borderPane = new BorderPane();
		AnchorPane anchorPane = new AnchorPane();
		borderPane.getChildren().add(anchorPane);
		Button button = new Button();
		anchorPane.getChildren().add(button);
		button.setText("START");
		button.setOnAction(new StartBtnEventHandler());
		scene = new Scene(borderPane,600,400);	
		model.addObserver(this);
	}
	
	public void show(Stage stage){
		stage.setTitle("TOOTOTOTO");
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	class StartBtnEventHandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			startController.showPopUpGetParams();
		}
		
	}

}
