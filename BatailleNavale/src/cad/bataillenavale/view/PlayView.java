package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import cad.bataillenavale.controller.PlayController;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.map.Case;
import cad.bataillenavale.model.map.Case.State;
import cad.bataillenavale.model.map.EmptyCase;
import cad.bataillenavale.model.map.MaritimeCase;

public class PlayView implements Observer {

	private BatailleNavale model;
	private Scene scene;
	
	private PlayController gameController;
	private Stage stage;
	
	private Button[][] btnsPlayer; // my Map (where i see my maritimes and IA's shoots)
	private Button[][] btnsIA;  // IA's map (Where i see my reachable map and my shoots)
	
	public PlayView(BatailleNavale modelBataille, Stage stage) {
		this.model = modelBataille;
		this.stage = stage ;
		model.addObserver(this);
		gameController = new PlayController(model);
		
		btnsPlayer = new Button[model.getLength()][model.getWidth()];
		btnsIA = new Button[model.getLength()][model.getWidth()];
		
		GridPane gpRoot = new GridPane(); 
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setPadding(new Insets(30, 30, 30, 30));
		
		GridPane gpIA = new GridPane(); 
		GridPane gpPlayer = new GridPane(); 
		
		for(int i = 0; i < model.getLength(); i++){
			for(int j = 0; j < model.getWidth(); j++){
				Button b = new Button();
				gpPlayer.add(b, i, j);
				btnsPlayer[i][j] = b;
				
				b = new Button();
				b.setOnAction(new BtnShootEventHandler(i, j));
				gpIA.add(b, i, j);
				btnsIA[i][j] = b;
			}
		}
		gpRoot.add(gpPlayer, 0, 0);
		gpRoot.add(gpIA, 1, 0);
		gpRoot.setAlignment(Pos.CENTER);
		scene = new Scene(gpRoot);	
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.show();
		update(null, null); 
	}
	
	@Override
	public void update(Observable o, Object arg) {
		drawMaps();
	}
	
	private void drawMaps(){
		
		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				Case c = model.getMapPlayer().getCase(i, j);
				if(c != null)
				{
					
					//  draw IA's shoots
					if(c.getState().equals(State.TOUCHED))
						btnsPlayer[i][j].setText("X");
					else if(c.getState().equals(State.MISSED))
						btnsPlayer[i][j].setText("O");
					
					// draw maritimes and reachable map
					else if(c.getState().equals(State.NOTPLAYED))
					{
						if(c instanceof MaritimeCase)
						{
							btnsPlayer[i][j].setText("B"); // draw maritimes 
							btnsIA[i][j].setText("R"); // draw reachable map
						}
						
						// update reachable map
						else if(c instanceof EmptyCase)
						{ 
							if(c.isReachable()) 
								btnsIA[i][j].setText("R"); 
							else
							{
								btnsIA[i][j].setText("_");
								btnsIA[i][j].setDisable(true);
							}
						}
					}
				}
			}
		}
		
		
		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
				Case c = model.getMapIA().getCase(i, j);
				if(c != null)
				{
					
					if(c.getState().equals(State.TOUCHED))
					{
						btnsIA[i][j].setText("X");
						btnsIA[i][j].setDisable(true);
					}
					else if(c.getState().equals(State.MISSED))
					{
						btnsIA[i][j].setText("O");
						btnsIA[i][j].setDisable(true);
					}
					
				}
			}
		}
	}

	class BtnShootEventHandler implements EventHandler<ActionEvent>{

		private int x, y;
		
		public BtnShootEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public void handle(ActionEvent event) {
			gameController.notifyShoot(x, y);
			
			if(model.isGameFinished())
			{
				/*Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bataille Navale");
				alert.setHeaderText("La partie est terminnée !");
				alert.setContentText("Bien joué !");

				alert.showAndWait();*/
			}
		}
		
	}
	
}
