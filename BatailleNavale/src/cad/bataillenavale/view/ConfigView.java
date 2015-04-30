package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
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
	private MaritimeList ml;
	
	private Button finish;
	private Stage stage;
	
	private GridPane gpPlayer; // drag n drop
	
	public ConfigView(BatailleNavale modelBataille,Stage stage){
		this.model = modelBataille;
		this.stage = stage;
		model.addObserver(this);
		configController = new ConfigController(model);
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		double screenWidth = bounds.getWidth();
		double screenHeight = bounds.getHeight();
		BorderPane borderPane = new BorderPane();
		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Options");
		final Menu menu3 = new Menu("Help");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2, menu3);
		borderPane.setMinSize(screenWidth, screenHeight);
		borderPane.setTop(menuBar);
		
		GridPane gpRoot = new GridPane(); 
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setPadding(new Insets(25, 25, 25, 25));
		
		gpPlayer = new GridPane(); 
		
		for(int i = 0; i < model.getLength(); i++){
			for(int j = 0; j < model.getWidth(); j++){
				Button b = new Button();
				b.setOnAction(new BtnAddEventHandler(i, j));
				gpPlayer.add(b, i, j);
				btnsPlayer[i][j] = b;
			}
		}
		
		gpRoot.add(gpPlayer, 0, 0);
		
		ml = new MaritimeList(modelBataille);
		list.setItems(ml);
		
		// drag n drop 
		list.setOnDragDetected(new OnDragDetected()); 
		list.setOnDragDone(new OnDragDone()); 
		gpPlayer.setOnDragDropped(new OnDragDropped()); 
		gpPlayer.setOnDragOver(new OnDragOver());
		
		gpRoot.add(list, 1, 0);
		
		finish = new Button();
		finish.setOnAction(new BtnFinishEventHandler());
		finish.setText("Terminer");
		
		gpRoot.add(finish, 1, 1);
		gpRoot.setAlignment(Pos.CENTER);
		borderPane.setCenter(gpRoot);
		Image img = new Image("file:resources/images/menu.jpg", screenWidth+30, screenHeight+30, false, true);
		Background bgImg = new Background(new BackgroundImage(img, null, null,BackgroundPosition.CENTER, null));
		borderPane.setBackground(bgImg);
		
		scene = new Scene(borderPane);
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.show();
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
			Dragboard db = list.startDragAndDrop(TransferMode.ANY);
			
			/* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(list.getSelectionModel().getSelectedItem());
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
		Maritime m = (Maritime)EpoqueManager.getInstance().getEpoque(model.getCurrentEpoque().getName()).getMaritime(maritime);
		
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
                    	configController.notifyAdd(x, y, (Maritime)EpoqueManager.getInstance().getEpoque(model.getCurrentEpoque().getName()).getMaritime(db.getString()));
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
	
	
}
