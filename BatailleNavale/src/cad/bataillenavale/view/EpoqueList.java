package cad.bataillenavale.view;

import java.util.ArrayList;

import javafx.collections.ObservableListBase;
import cad.bataillenavale.model.epoque.EpoqueManager;

public class EpoqueList extends ObservableListBase<String> {
	
	private ArrayList<String> alm = new ArrayList<>();
	
	public EpoqueList() {
		EpoqueManager em = EpoqueManager.getInstance();
		for(String maritime : em.getEpoques())
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
