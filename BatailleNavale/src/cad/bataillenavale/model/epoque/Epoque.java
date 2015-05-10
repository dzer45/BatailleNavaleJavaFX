package cad.bataillenavale.model.epoque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.map.Prototype;

public class Epoque implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6201229032979142491L;
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

	
	public Map<String, Maritime> getMaritimes() {
		return maritimes;
	}

	public void setMaritimes(Map<String, Maritime> maritimes) {
		this.maritimes = maritimes;
	}

	public void addMaritime(Maritime m) {
		this.maritimes.put(m.getName(), m);
	}
	
	public void removeMaritime(String m) {
		this.maritimes.remove(m);
	}
	
	public Prototype cloneMaritime(String name){
		return maritimes.get(name).doClone();
	}
	
	public Maritime getMaritime(String name){
		return maritimes.get(name);
	}
	
	public Set<String> getMaritimesNames(){
		return maritimes.keySet();
	}
}
