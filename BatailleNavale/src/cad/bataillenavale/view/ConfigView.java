package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import cad.bataillenavale.controller.ConfigController;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Case;
import cad.bataillenavale.model.map.EmptyCase;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.map.MaritimeCase;

public class ConfigView implements Observer {

	private BatailleNavale model;
	private Scene scene;
	
	private Button[][] btnsPlayer = new Button[10][10]; // Player's map (where we place maritimes and see IA's shoots)
	
	private ConfigController configController;
	
	private ListView<String> list = new ListView<String>();
	
	private Button finish;
	
	public ConfigView(BatailleNavale modelBataille){
		this.model = modelBataille;
		model.addObserver(this);
		configController = new ConfigController(model);
		
		GridPane gpRoot = new GridPane(); 
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setPadding(new Insets(25, 25, 25, 25));
		
		GridPane gpPlayer = new GridPane(); 
		
		for(int i = 0; i < model.getLength(); i++){
			for(int j = 0; j < model.getWidth(); j++){
				Button b = new Button();
				b.setOnAction(new BtnAddEventHandler(i, j));
				gpPlayer.add(b, i, j);
				btnsPlayer[i][j] = b;
			}
		}
		
		gpRoot.add(gpPlayer, 0, 0);
		
		ObservableList<String> items =FXCollections.observableArrayList (
			    "Galion");
		list.setItems(items);
		
		gpRoot.add(list, 1, 0);
		
		finish = new Button();
		finish.setOnAction(new BtnFinishEventHandler());
		finish.setText("Terminer");
		
		gpRoot.add(finish, 1, 1);
		
		scene = new Scene(gpRoot,600,400);	
		
	}

	@Override
	public void update(Observable o, Object arg) {
		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				Case c = model.getMapPlayer().getCase(i, j);
				if(c != null)
				{

					if(c instanceof MaritimeCase)
						btnsPlayer[i][j].setText("B");
					else if(c instanceof EmptyCase){
						if(c.isReachable())
							btnsPlayer[i][j].setText("R");
						else
							btnsPlayer[i][j].setText("_");
					}
				}
			}
		}
	}
	
	public void show(Stage stage){
		stage.setTitle("TOOTOTOTO");
		stage.setScene(scene);
		stage.show();
	}
	

	class BtnAddEventHandler implements EventHandler<ActionEvent>{

		private int x, y;
		
		public BtnAddEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void handle(ActionEvent event) {
			try {
				configController.notifyAdd(x, y, (Maritime)EpoqueManager.getInstance().getEpoque(model.getCurrentEpoque().getName()).getMaritime(list.getSelectionModel().getSelectedItem()));
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	class BtnFinishEventHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			configController.notifyFinish();
		}
		
	}
	
}
