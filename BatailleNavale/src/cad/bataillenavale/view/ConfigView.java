package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.ConfigController;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Case;
import cad.bataillenavale.model.map.EmptyCase;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.map.MaritimeCase;

public class ConfigView extends BatailleNavaleView implements Observer {

	private Button[][] btnsPlayer; // Player's map (where we place maritimes and see IA's shoots)
	
	private ListView<String> lvMaritimes = new ListView<String>(); // list of maritimes names
	
	private Button finishButton;
	
	private GridPane gpPlayer; // référence pour drag n drop
	private GridPane gpRoot = new GridPane(); 
	
	public ConfigView(BatailleNavale model,Stage stage){
		super(stage, model, new ConfigController(model));
		model.addObserver(this);
		
		btnsPlayer = new Button[model.getLength()][model.getLength()];
		
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setPadding(new Insets(25, 25, 25, 25));
		
		gpPlayer = new GridPane(); 
		
		for(int i = 0; i < model.getLength(); i++){
			for(int j = 0; j < model.getLength(); j++){
				Button b = new Button();
				b.setOnAction(new BtnAddEventHandler(i, j));
				gpPlayer.add(b, i, j);
				btnsPlayer[i][j] = b;
			}
		}
		
		gpRoot.add(gpPlayer, 0, 0);
		
		MaritimeItemList maritimeItemList = new MaritimeItemList(model.getCurrentEpoque());
		lvMaritimes.setItems(maritimeItemList);
		lvMaritimes.getSelectionModel().selectedItemProperty().addListener(new MaritimeListener());
		
		// drag n drop 
		lvMaritimes.setOnDragDetected(new OnDragDetected()); 
		lvMaritimes.setOnDragDone(new OnDragDone()); 
		gpPlayer.setOnDragDropped(new OnDragDropped()); 
		gpPlayer.setOnDragOver(new OnDragOver());
		
		gpRoot.add(lvMaritimes, 1, 0);
		
		finishButton = new Button("Terminer");
		finishButton.setOnAction(new BtnFinishEventHandler());
		finishButton.setDisable(true);
		
		gpRoot.add(finishButton, 1, 1);
		gpRoot.setAlignment(Pos.CENTER);
		root.setCenter(gpRoot);
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getLength(); j++) {
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
		
		finishButton.setDisable(!model.canFinishGame());	
	}
	
	public void show(Stage stage){
		stage.setTitle("Bataille Navalle");
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
				
				String maritimeSelected = lvMaritimes.getSelectionModel().getSelectedItem();
				ConfigController configController = (ConfigController) controller;
				configController.notifyAdd(x, y, maritimeSelected);
				
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	class BtnFinishEventHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			ConfigController configController = (ConfigController) controller;
			configController.notifyFinish(stage);
		}
		
	}
	
	// drag started
	class OnDragDetected implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			/* drag was detected, start drag-and-drop gesture*/
			System.out.println("Bateau sélectionné");
			
			 /* allow any transfer mode */
			Dragboard db = lvMaritimes.startDragAndDrop(TransferMode.ANY);
			
			/* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(lvMaritimes.getSelectionModel().getSelectedItem());
            db.setContent(content);
            
            event.consume();
		}		
	}
	
	// draging over the map
	class OnDragOver implements EventHandler<DragEvent> {

		@Override
		public void handle(DragEvent event) {
			/* data is dragged over the target */
            System.out.println("onDragOver");
            
            /* accept it only if it is  not dragged from the same node 
             * and if it has a string data */
            if (event.getGestureSource() != gpPlayer &&
                    event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                
                // draw the boat on the view    
                EventTarget et = event.getTarget();
                if(et instanceof Button)
                {
                	int x = GridPane.getColumnIndex((Button)et);
                	int y = GridPane.getRowIndex((Button)et);
                	Dragboard db = event.getDragboard();
                	//drawBoat(x, y, db.getString());
                }
            }
            
            event.consume();
		}
		
	}

	private void drawBoat(int x, int y, String maritime){
		Maritime m = (Maritime)model.getEpoque(model.getCurrentEpoque().getName()).cloneMaritime(maritime);
		
		for (int i = x; i < m.getLength(); i++) {
			for (int j = y; j < m.getWidth(); j++) {
				if(i > 0 && j > 0 && i <=  btnsPlayer.length && j <= btnsPlayer[0].length)
				{
					btnsPlayer[i][j].setText("B");
				}
			}
		}
	
		for (int i = x - m.getPower(); i < m.getLength()+m.getPower(); i++) {
			for (int j = y - m.getPower(); j < m.getWidth()+m.getPower(); j++) {
				if(i > 0 && j > 0 && i <=  btnsPlayer.length && j <= btnsPlayer[0].length)
				{
					if(!btnsPlayer[i][j].getText().equals("B")){
						btnsPlayer[i][j].setText("R");
					}
				}
			}
		} 
	}
	
	// draging finished
	class OnDragDropped implements EventHandler<DragEvent> {

		@Override
		public void handle(DragEvent event) {
			 /* data dropped */
            System.out.println("onDragDropped");
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
            	
            	try {
            		// add the boat to the model
            		EventTarget et = event.getTarget();
            		if(et instanceof Button)
                    { 	
                    	int x = GridPane.getColumnIndex((Button)et);
                    	int y = GridPane.getRowIndex((Button)et);
                    	ConfigController configController = (ConfigController) controller;
                    	configController.notifyAdd(x, y, db.getString());
                    }
				} catch (MapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                success = true;
            }
            /* let the source know whether the string was successfully 
             * transferred and used */
            event.setDropCompleted(success);
            
            event.consume();
		}
		
	}
	
	class OnDragDone implements EventHandler<DragEvent> {

		@Override
		public void handle(DragEvent event) {
			 /* the drag-and-drop gesture ended */
			System.out.println("Bateau déposé");
			
            /* if the data was successfully moved, clear it */
			if (event.getTransferMode() == TransferMode.MOVE) {
                
            }
            
            event.consume();
		}
		
	}
	
	// draw properties of a maritime
	class MaritimeListener implements ChangeListener<String>{

		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {

			String epoqueName =  model.getCurrentEpoque().getName();
			String maritimeName =  lvMaritimes.getSelectionModel().getSelectedItem();
			Maritime m = model.getEpoque(epoqueName).getMaritime(maritimeName);
			
			VBox vBox = new VBox();
			Label longueurLabel = new Label("Longueur : "+m.getLength());
			Label hauteurLabel = new Label("Hauteur : "+m.getWidth());
			Label puissanceLabel = new Label("Puissance : "+m.getPower());
			vBox.getChildren().add(longueurLabel);
			vBox.getChildren().add(hauteurLabel);
			vBox.getChildren().add(puissanceLabel);
			
			gpRoot.add(vBox, 2, 0);
		}
		
	}
}
