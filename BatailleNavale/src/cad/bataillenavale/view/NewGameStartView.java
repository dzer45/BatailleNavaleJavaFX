package cad.bataillenavale.view;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
		// TODO Auto-generated method stub
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
		GridPane gridPane = addGridPane();
		borderPane.setCenter(gridPane);
		Image img = new Image("file:resources/images/menu.jpg", screenWidth+30, screenHeight+30, false, true);
		Background bgImg = new Background(new BackgroundImage(img, null, null,BackgroundPosition.CENTER, null));
		borderPane.setBackground(bgImg);
		scene = new Scene(borderPane);
		stage.setTitle("Bataille Navale");
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
	    
	    ComboBox epoqueComboBox = new ComboBox();
        epoqueComboBox.getItems().addAll(model.getEpoques());   

        epoqueComboBox.setValue("XVI");
        grid.add(epoqueComboBox, 1, 1);
        
	    ComboBox sizeComboBox = new ComboBox();
        sizeComboBox.getItems().addAll(
            "10",
            "20",
            "30"
        );   

        sizeComboBox.setValue("10");
        grid.add(sizeComboBox, 1, 2);

	    // ButtonOK  in column 1-2, row 3
	    Button buttonOk = new Button();
	    buttonOk.setText("Valider");		
	    buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				NewGameStartController newGameStartController = (NewGameStartController) controller;
				newGameStartController.notifyStart(sizeComboBox.getValue().toString(),epoqueComboBox.getValue().toString());
				ConfigView cView = new ConfigView(model, stage);
			}
		});
	    grid.add(buttonOk, 1, 3, 2, 1);

	    grid.setAlignment(Pos.CENTER);
	    return grid;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
