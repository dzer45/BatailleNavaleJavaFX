package cad.bataillenavale.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cad.bataillenavale.controller.EditController;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.epoque.EpoqueManager;

public class EditView {

	private Stage stage;
	private Scene scene;
	private EditController editController;

	private BorderPane borderPane;
	
	private ListView<String> lvEpoques;
	
	public EditView(Stage stage){
		this.stage = stage ;
		editController = new EditController();
		
		buildFrame();
	}
	
	private void buildFrame() {
		borderPane = new BorderPane();
		
		lvEpoques = new ListView<>();
		lvEpoques.setItems(new EpoqueList());
		
		lvEpoques.getSelectionModel().selectedItemProperty().addListener(new EpoqueListener());
		
		HBox hbBtnEpoque = new HBox();
		Button addEpoqueBtn = new Button("Ajouter");
		addEpoqueBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				editController.showAddEpoquePopUp(stage);
			}
		});
		hbBtnEpoque.getChildren().add(addEpoqueBtn);
		Button removeEpoqueBtn = new Button("Supprimer");
		removeEpoqueBtn.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				String epoqueName =  lvEpoques.getSelectionModel().getSelectedItem();
				editController.removeEpoque(epoqueName);
				lvEpoques.setItems(new EpoqueList()); // refresh list TODO
			}
		});
		hbBtnEpoque.getChildren().add(removeEpoqueBtn);
		
		VBox vbEpoques = new VBox();
		vbEpoques.getChildren().add(lvEpoques);
		vbEpoques.getChildren().add(hbBtnEpoque);
		
		borderPane.setLeft(vbEpoques);
		
		scene = new Scene(borderPane);
		stage.setTitle("Bataille Navale");
		stage.setScene(scene);
		stage.show();
	}
	
	class EpoqueListener implements ChangeListener<String>{

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			ListView<String> lvMaritimes = new ListView<>();
			EpoqueManager em = EpoqueManager.getInstance();
			Epoque e = em.getEpoque(newValue);
			lvMaritimes.setItems(new MaritimeList(e));
			
			HBox hbBtnMaritime = new HBox();
			Button addMaritimeBtn = new Button("Ajouter");
			addMaritimeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
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
					editController.removeMaritime(epoqueName, maritimeName);
					lvMaritimes.setItems(new MaritimeList(e));  // refresh list TODO
				}
			});
			hbBtnMaritime.getChildren().add(removeMaritimeBtn);
			
			VBox vbMaritimes = new VBox();
			vbMaritimes.getChildren().add(lvMaritimes);
			vbMaritimes.getChildren().add(hbBtnMaritime);
			
			borderPane.setCenter(vbMaritimes);
		}
		
	}
}
