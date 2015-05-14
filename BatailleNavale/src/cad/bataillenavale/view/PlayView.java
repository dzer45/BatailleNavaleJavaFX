package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import cad.bataillenavale.controller.PlayController;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.map.Case;
import cad.bataillenavale.model.map.Case.State;
import cad.bataillenavale.model.map.Maritime;

public class PlayView extends BatailleNavaleView implements Observer {

	private Rectangle[][] btnsPlayer, btnsIA; // IA's map (Where i see my reachable map and my shoots)
	private GridPane gpIA, gpPlayer;

	public PlayView(BatailleNavale model, Stage stage) {
		super(stage, model, new PlayController(model));
		model.addObserver(this);

		btnsPlayer = new Rectangle[model.getLength()][model.getLength()];
		btnsIA = new Rectangle[model.getLength()][model.getLength()];

		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(30, 30, 30, 30));

		gpIA = new GridPane();
		gpPlayer = new GridPane();

		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getLength(); j++) {

				Rectangle rectangle = new Rectangle(40, 40);
				rectangle.setStroke(Paint.valueOf("orange"));
				rectangle.setFill(Paint.valueOf("steelblue"));
				gpPlayer.add(rectangle, i, j);
				btnsPlayer[i][j] = rectangle;

				rectangle = new Rectangle(40, 40);
				rectangle.setOnMouseClicked(new BtnShootEventHandler(i, j));
				rectangle.setStroke(Paint.valueOf("orange"));
				rectangle.setFill(Paint.valueOf("steelblue"));
				gpIA.add(rectangle, i, j);
				btnsIA[i][j] = rectangle;
			}
		}
		gp.add(gpPlayer, 0, 0);
		gp.add(gpIA, 1, 0);
		gp.setAlignment(Pos.CENTER);

		root.setCenter(gp);
		scene = new Scene(root);
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.show();
		
		drawMaps();
	}

	@Override
	public void update(Observable o, Object arg) {
		drawMaps();
	}

	private void drawMaps() {

		for (Maritime m : model.getMapPlayer().getMaritimes()) {
			
			ImageView iv1 = new ImageView(new Image(
					"file:resources/images/bateaux/" + m.getName() + ".png",
					m.getLength() * 40, m.getWidth() * 40, false, false));
			
			gpPlayer.add(iv1, m.getPoint().x, m.getPoint().y, m.getLength(),
					m.getWidth());
			
			for(int i = m.getPoint().x; i< m.getPoint().x+m.getLength();i++) {
				for (int j = m.getPoint().y; j < m.getPoint().y+ m.getWidth(); j++) {
					btnsPlayer[i][j].setFill(Paint.valueOf("gray"));
					btnsPlayer[i][j].setDisable(true);
					
					for (int k = -m.getPower(); k < m.getPower()+1; k++) {
						for (int l = -m.getPower(); l < m.getPower()+1; l++) {
							
							if(i+k >= model.getLength() || j+l >= model.getLength() || i+k < 0 || j+l < 0)
								continue;
							else
								btnsIA[i+k][j+l].setFill(Paint.valueOf("gray"));
						}
					}

				}
			}
		}

		for (Maritime m : model.getMapIA().getMaritimes()) {
			ImageView iv1 = new ImageView(new Image(
					"file:resources/images/bateaux/" + m.getName() + ".png",
					m.getLength() * 40, m.getWidth() * 40, false, false));
			if(!m.isDestroyed())
				iv1.setVisible(false);
			gpIA.add(iv1, m.getPoint().x, m.getPoint().y, m.getLength(),
					m.getWidth());
		}
		

		for (int i = 0; i < model.getLength(); i++) {
			for (int j = 0; j < model.getLength(); j++) {
				Case c = model.getMapIA().getCase(i, j);
				Case c1 = model.getMapPlayer().getCase(i, j);
				if (c != null) {
					if (c.getState().equals(State.TOUCHED)) {
						btnsIA[i][j].setFill(Paint.valueOf("red"));
					} else if (c.getState().equals(State.MISSED)) {
						btnsIA[i][j].setFill(Paint.valueOf("black"));
					}

				}
				if (c1 != null) {
					if (c1.getState().equals(State.TOUCHED)) {
						btnsPlayer[i][j].setFill(Paint.valueOf("red"));
					} else if (c1.getState().equals(State.MISSED)) {
						btnsPlayer[i][j].setFill(Paint.valueOf("black"));
					}

				}
			}
		}
	}

	class BtnShootEventHandler implements EventHandler<MouseEvent> {

		private int x, y;

		public BtnShootEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void handle(MouseEvent event) {
			
			if(model.isReachableShoot(x, y))
			{
				if(!model.isPlayedShoot(x, y))
				{
					PlayController playController = (PlayController) controller;
					playController.notifyShoot(x, y);
				}
				else
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Bataille Navale");
					alert.setHeaderText("La case à déjà été jouée !");
					alert.setContentText("Tirez sur une autre case !");
					alert.showAndWait();
				}
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bataille Navale");
				alert.setHeaderText("Cette case ne se trouve pas à la portée de vos maritimes !");
				alert.setContentText("Tirez sur une case à la portée de vos maritimes !");
				alert.showAndWait();
			}

			if (model.isGameFinished()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bataille Navale");
				alert.setHeaderText("La partie est terminnée !");
				alert.setContentText("Bien joué !");
				alert.showAndWait();
				
				new StartView(model, stage);
			}
		}

	}

}
