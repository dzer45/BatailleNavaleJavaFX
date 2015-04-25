package cad.bataillenavale.view;

import java.util.ArrayList;

import javafx.collections.ObservableListBase;
import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.Epoque;

public class MaritimeList extends ObservableListBase<String> {
	
	private ArrayList<String> alm = new ArrayList<>();
	
	public MaritimeList(BatailleNavale bn) {
		Epoque e = bn.getCurrentEpoque();
		for(String maritime : e.getMaritimes())
		{
			alm.add(maritime);
		}
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
