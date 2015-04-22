package cad.bataillenavale.model.epoque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cad.bataillenavale.model.map.Maritime;

public class Epoque {
	
	private String name;
	private Map<String, Maritime> maritimes = new HashMap<String, Maritime>();
	
	public Epoque(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMaritime(Maritime m) {
		this.maritimes.put(m.getName(), m);
	}
	
	public Prototype getMaritime(String name){
		return maritimes.get(name).doClone();
	}
	
	public Set<String> getMaritimes(){
		return maritimes.keySet();
	}
}