package cad.bataillenavale.model.epoque;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cad.bataillenavale.model.map.Boat;
import cad.bataillenavale.model.map.Maritime;

public class EpoqueManager {

	private static EpoqueManager em = new EpoqueManager();
	
	private Map<String, Epoque> epoques = new HashMap<String, Epoque>();
	
	private EpoqueManager(){
		Epoque e = new Epoque("XX");
		Maritime m = new Boat(2, 2, 4);
		m.setName("Croiseur");
		e.addMaritime(m);
		addEpoque(e);
		
		e = new Epoque("XVI");
		m = new Boat(2, 2, 1);
		m.setName("Galion");
		e.addMaritime(m);
		addEpoque(e);
	}
	
	public static EpoqueManager getInstance(){
		return em;
	}
	
	public Set<String> getEpoques(){
		return this.epoques.keySet();
	}
	
	public Epoque getEpoque(String name){
		return this.epoques.get(name);
	}
	
	public void addEpoque(Epoque e){
		this.epoques.put(e.getName(), e);
	}
}
