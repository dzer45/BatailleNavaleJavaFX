package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import cad.bataillenavale.controller.ConfigController;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Maritime;

public class ConfigView extends BatailleNavaleView implements Observer {

	private ListView<String> lvMaritimes = new ListView<String>(); // list of maritimes names
	private Button finishButton;
	private GridPane gpPlayer;
	private Rectangle[][] btnsPlayer;
	private GridPane gpRoot = new GridPane(); 
	
	public ConfigView(BatailleNavale model,Stage stage){
		super(stage, model, new ConfigController(model));
		model.addObserver(this);
		
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.setPadding(new Insets(25, 25, 25, 25));
		
		btnsPlayer = new Rectangle[model.getLength()][model.getLength()];

		gpPlayer = new GridPane(); 
		
		for(int i = 0; i < model.getLength(); i++){
			for(int j = 0; j < model.getLength(); j++){
				Rectangle rectangle = new Rectangle(40, 40);
				rectangle.setOnMouseClicked(new BtnAddEventHandler(i, j));
				rectangle.setStroke(Paint.valueOf("orange"));
                rectangle.setFill(Paint.valueOf("steelblue"));
                gpPlayer.add(rectangle, i, j);
                btnsPlayer[i][j] = rectangle;
			}
		}
		
		gpRoot.add(gpPlayer, 0, 0);
		
		MaritimeItemList maritimeItemList = new MaritimeItemList(model.getCurrentEpoque());
		lvMaritimes.setItems(maritimeItemList);
		lvMaritimes.getSelectionModel().selectedItemProperty().addListener(new MaritimeListener());

		
		gpRoot.add(lvMaritimes, 1, 0);
		
		finishButton = new Button("Terminer");
		finishButton.setOnAction(new BtnFinishEventHandler());
		
		gpRoot.add(finishButton, 1, 1);
		gpRoot.setAlignment(Pos.CENTER);
		root.setCenter(gpRoot);
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void update(Observable o, Object arg) { 
		Maritime m = (Maritime)arg;
		if(m!=null){
			ImageView iv1 = new ImageView(new Image("file:resources/images/bateaux/"+m.getName()+".png",m.getLength()*40,m.getWidth()*40, false, false));
			gpPlayer.add(iv1,m.getPoint().x , m.getPoint().y, m.getLength(), m.getWidth());
			for(int i =m.getPoint().x; i< m.getPoint().x+m.getLength();i++) {
				for (int j = m.getPoint().y; j < m.getPoint().y+ m.getWidth(); j++) {
					btnsPlayer[i][j].setFill(Paint.valueOf("gray"));
					btnsPlayer[i][j].setDisable(true);
				}
			}
		}
	}
	
	public void show(Stage stage){
		stage.setTitle("Bataille Navalle");
		stage.setScene(scene);
		stage.show();
	}
	

	class BtnAddEventHandler implements  EventHandler<MouseEvent>{

		private int x, y;
		
		public BtnAddEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}
	
		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			try{
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
