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
	
	/**
	 * Constructeur
	 * @param name le nom de l'époque
	 */
	public Epoque(String name){
		this.name = name;
	}

	/**
	 * Récupérer le nom de l'époque
	 * @return le nom de l'époque
	 */
	public String getName() {
		return name;
	}

	/**
	 * Modifier le nom de l'époque
	 * @param name le nom de l'époque
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Récupérer l'ensemble des maritimes de l'époque
	 * @return l'ensemble des maritimes de l'époque
	 */
	public Map<String, Maritime> getMaritimes() {
		return maritimes;
	}
	
	/**
	 * Modifier l'ensemble des maritimes de l'époque
	 * @param maritimes l'ensemble des maritimes de l'époque
	 */
	public void setMaritimes(Map<String, Maritime> maritimes) {
		this.maritimes = maritimes;
	}

	/**
	 * Ajouter un maritime à l'époque
	 * @param m le maritime
	 */
	public void addMaritime(Maritime m) {
		this.maritimes.put(m.getName(), m);
	}
	
	/**
	 * Supprimer un maritime
	 * @param m le nom maritime
	 */
	public void removeMaritime(String m) {
		this.maritimes.remove(m);
	}
	
	/**
	 * Cloner un maritime
	 * @param name le nom du maritime à cloner
	 * @return un clone du maritime 
	 */
	public Prototype cloneMaritime(String name){
		return maritimes.get(name).doClone();
	}
	
	/**
	 * Récupérer un maritime par son nom
	 * @param name le nom du maritime
	 * @return le maritime associé
	 */
	public Maritime getMaritime(String name){
		return maritimes.get(name);
	}
	
	/**
	 * Récupérer la liste des nom des maritimes de l'époque
	 * @return la liste des nom des maritimes de l'époque
	 */
	public Set<String> getMaritimesNames(){
		return maritimes.keySet();
	}
}
