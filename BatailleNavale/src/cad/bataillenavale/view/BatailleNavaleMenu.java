package cad.bataillenavale.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;

public class BatailleNavaleMenu extends MenuBar {

	public BatailleNavaleMenu(BatailleNavale model,Stage stage){
		
		final Menu menu1 = new Menu("Fichier");
		MenuItem mi1 = new MenuItem("Retourner à la page de démarrage");
		mi1.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		       new StartView(model, stage);
		    }
		});
		MenuItem mi2 = new MenuItem("Quitter");
		mi2.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);
		    }
		});
		menu1.getItems().addAll(mi1, mi2);
		final Menu menu2 = new Menu("Aide");
		
		getMenus().addAll(menu1, menu2);
		
	}
}
