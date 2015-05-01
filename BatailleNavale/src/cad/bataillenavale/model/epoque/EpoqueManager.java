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
		Maritime m = new Boat("Croiseur", 2, 2, 2);
		e.addMaritime(m);
		addEpoque(e);
		
		e = new Epoque("XVI");
		m = new Boat("Galion", 2, 2, 1);
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
	
	public void removeEpoque(String epoqueName){
		this.epoques.remove(epoqueName);
	}
}
