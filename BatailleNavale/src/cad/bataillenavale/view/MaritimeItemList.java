package cad.bataillenavale.view;

import java.util.ArrayList;

import javafx.collections.ObservableListBase;
import cad.bataillenavale.model.epoque.Epoque;

public class MaritimeItemList extends ObservableListBase<String> {
	
	private ArrayList<String> alm = new ArrayList<>();
	
	public MaritimeItemList(Epoque e) {
		for(String maritime : e.getMaritimesNames())
			alm.add(maritime);
	}
	
	@Override
	public String get(int index) {
		return alm.get(index);
	}

	@Override
	public int size() {
		return alm.size();
	}

}
