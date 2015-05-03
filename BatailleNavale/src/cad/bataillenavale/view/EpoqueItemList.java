package cad.bataillenavale.view;

import java.util.ArrayList;

import javafx.collections.ObservableListBase;
import cad.bataillenavale.model.BatailleNavale;

public class EpoqueItemList extends ObservableListBase<String> {
	
	private ArrayList<String> alm = new ArrayList<>();
	
	public EpoqueItemList(BatailleNavale model) {
		for(String maritime : model.getEpoques())
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
