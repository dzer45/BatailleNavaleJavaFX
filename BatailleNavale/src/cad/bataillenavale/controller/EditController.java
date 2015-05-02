package cad.bataillenavale.controller;

import javafx.stage.Stage;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.map.Boat;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.view.AddEpoqueView;
import cad.bataillenavale.view.AddMaritimeView;
import cad.bataillenavale.view.EditView;

public class EditController {

	private EpoqueManager em = EpoqueManager.getInstance();
	private BatailleNavale model;
	
	public EditController(BatailleNavale model) {
		// TODO Auto-generated constructor stub
		this.model = model;
	}

	public void addEpoque(String epoqueName) {
		//em.addEpoque(new Epoque(epoqueName));
		model.addEpoque(new Epoque(epoqueName));
	}

	public void removeEpoque(String epoqueName) {
		em.removeEpoque(epoqueName);
	}

	public void showAddEpoquePopUp(Stage stage) {
		new AddEpoqueView(model,stage);
	}

	public void addMaritime(String epoqueName, String maritimeName, String length, String width, String power) {
		Epoque e = em.getEpoque(epoqueName);
		Maritime m = new Boat(maritimeName, Integer.parseInt(length), Integer.parseInt(width), Integer.parseInt(power));
		e.addMaritime(m);
	}

	public void removeMaritime(String epoqueName, String maritimeName) {
		Epoque e = em.getEpoque(epoqueName);
		e.removeMaritime(maritimeName);
	}

	public void showAddMaritimePopUp(Stage stage, String epoqueName) {
		new AddMaritimeView(model,stage, epoqueName);
	}
	
	public void returnToEditView(Stage stage){
		new EditView(model,stage);
	}
}
