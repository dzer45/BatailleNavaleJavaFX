package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import cad.bataillenavale.controller.StartController;
import cad.bataillenavale.model.BatailleNavale;

public class StartView implements Observer {

	private BatailleNavale model;
	private Stage stage;
	private Scene scene;

	private StartController startController;

	public StartView(BatailleNavale modelBataille, Stage stage) {
		// TODO Auto-generated constructor stub
		this.model = modelBataille;
		this.stage = stage;
		buildFrame();
		model.addObserver(this);
	}

	private void buildFrame() {
		startController = new StartController(model);
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		double screenWidth = bounds.getWidth() ;
		double screenHeight = bounds.getHeight() ;
		BorderPane borderPane = new BorderPane();
		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Options");
		final Menu menu3 = new Menu("Help");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2, menu3);
		Button button = new Button();
		button.setText("START");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				startController.showPopUpGetParams(stage);
			}
		});
		Button button2 = new Button();
		button2.setText("LOAD");
		button2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				startController.showPopUpGetParams(stage);
			}
		});
		Button button3 = new Button();
		button3.setText("HELP");
		button3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				startController.showPopUpGetParams(stage);
			}
		});
		GridPane gridPane = new GridPane();

		VBox vbox = new VBox(10);
		vbox.getChildren().add(button);
		vbox.getChildren().add(button2);
		vbox.getChildren().add(button3);
		gridPane.getChildren().add(vbox);
		gridPane.setAlignment(Pos.CENTER);
		borderPane.setMinSize(screenWidth, screenHeight);
		borderPane.setTop(menuBar);
		borderPane.setCenter(gridPane);
		Image img = new Image("file:resources/images/menu.jpg",screenWidth+20, screenHeight+20, false, true);
		Background bgImg = new Background(new BackgroundImage(img, null, null, BackgroundPosition.CENTER, null));
		borderPane.setBackground(bgImg);
		scene = new Scene(borderPane);
		stage.setTitle("TOOTOTOTO");
		stage.setScene(scene);
		stage.setHeight(screenHeight);
		stage.setWidth(screenWidth);
		stage.show();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
