package cad.bataillenavale.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import cad.bataillenavale.controller.BatailleNavaleController;
import cad.bataillenavale.model.BatailleNavale;

public abstract class BatailleNavaleView {

	protected Stage stage;
	protected Scene scene;
	protected BatailleNavale model;
	protected BatailleNavaleController controller;
	protected BorderPane root = new BorderPane();
	
	public BatailleNavaleView(Stage stage, BatailleNavale model, BatailleNavaleController controller){
		this.stage = stage;
		this.model = model;
		this.controller = controller;
		
		stage.setTitle("Bataille Navale");
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		double screenWidth = bounds.getWidth();
		double screenHeight = bounds.getHeight();
		stage.setHeight(screenHeight);
		stage.setWidth(screenWidth);
		root.setMinSize(screenWidth, screenHeight);
		root.setTop(new BatailleNavaleMenu(model, stage));
		Image img = new Image("file:resources/images/menu.jpg", screenWidth+30, screenHeight+30, false, true);
		Background bgImg = new Background(new BackgroundImage(img, null, null,BackgroundPosition.CENTER, null));
		root.setBackground(bgImg);
	}
	
	//abstract void buildFrame();
}
