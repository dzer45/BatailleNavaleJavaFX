package cad.bataillenavale.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.EditController;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.map.Maritime;

public class EditView extends BatailleNavaleView {
	
	private ListView<String> lvEpoques, lvMaritimes ;
	GridPane gp = new GridPane();
	
	public EditView(BatailleNavale model,Stage stage){
		super(stage, model, new EditController(model));
		
		buildFrame();
	}
	
	private void buildFrame() {
		
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setPadding(new Insets(25, 25, 25, 25));
		
		lvEpoques = new ListView<>();
		lvEpoques.setItems(new EpoqueItemList(model));
		
		lvEpoques.getSelectionModel().selectedItemProperty().addListener(new EpoqueListener());
		
		HBox hbBtnEpoque = new HBox();
		Button addEpoqueBtn = new Button("Ajouter");
		addEpoqueBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				EditController editController = (EditController) controller;
				editController.showAddEpoquePopUp(stage);
			}
		});
		hbBtnEpoque.getChildren().add(addEpoqueBtn);
		Button removeEpoqueBtn = new Button("Supprimer");
		removeEpoqueBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				String epoqueName =  lvEpoques.getSelectionModel().getSelectedItem();
				EditController editController = (EditController) controller;
				editController.removeEpoque(epoqueName);
				lvEpoques.setItems(new EpoqueItemList(model)); // refresh list TODO
			}
		});
		hbBtnEpoque.getChildren().add(removeEpoqueBtn);
		
		VBox vbEpoques = new VBox();
		vbEpoques.getChildren().add(lvEpoques);
		vbEpoques.getChildren().add(hbBtnEpoque);
		
		root.setCenter(gp);
		gp.add(vbEpoques, 1, 1);
		gp.setAlignment(Pos.CENTER);
		root.setCenter(gp);
		
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	class EpoqueListener implements ChangeListener<String>{

		// when an epoque is selected, it shows all the maritimes of that epoque on a listView 
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			lvMaritimes = new ListView<>();
			lvMaritimes.getSelectionModel().selectedItemProperty().addListener(new MaritimeListener());
			
			Epoque e = model.getEpoque(newValue);
			lvMaritimes.setItems(new MaritimeItemList(e));
			
			HBox hbBtnMaritime = new HBox();
			Button addMaritimeBtn = new Button("Ajouter");
			addMaritimeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					EditController editController = (EditController) controller;
					editController.showAddMaritimePopUp(stage, newValue);
				}
			});
			hbBtnMaritime.getChildren().add(addMaritimeBtn);
			Button removeMaritimeBtn = new Button("Supprimer");
			removeMaritimeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String epoqueName =  lvEpoques.getSelectionModel().getSelectedItem();
					String maritimeName =  lvMaritimes.getSelectionModel().getSelectedItem();
					EditController editController = (EditController) controller;
					editController.removeMaritime(epoqueName, maritimeName);
					lvMaritimes.setItems(new MaritimeItemList(e));  // refresh list TODO
				}
			});
			hbBtnMaritime.getChildren().add(removeMaritimeBtn);
			
			VBox vbMaritimes = new VBox();
			vbMaritimes.getChildren().add(lvMaritimes);
			vbMaritimes.getChildren().add(hbBtnMaritime);
			
			gp.add(vbMaritimes, 2, 1);
		}
		
	}
	
	// draw properties of a maritime
	class MaritimeListener implements ChangeListener<String>{

		@Override
		public void changed(ObservableValue<? extends String> observable,
				String oldValue, String newValue) {

			String epoqueName =  lvEpoques.getSelectionModel().getSelectedItem();
			String maritimeName =  lvMaritimes.getSelectionModel().getSelectedItem();
			Maritime m = model.getEpoque(epoqueName).getMaritime(maritimeName);
			
			ListView<String> lv = new ListView<String>();
			ObservableList<String> items = FXCollections.observableArrayList (
					"Longueur : "+m.getLength(), "Hauteur : "+m.getWidth(), "Puissance : "+m.getPower());
			lv.setItems(items);
			
			gp.add(lv, 3, 1);
		}
		
	}
}
