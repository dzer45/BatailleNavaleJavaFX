package cad.bataillenavale.model.epoque;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import cad.bataillenavale.model.map.Boat;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.persistance.DAOFactory;
import cad.bataillenavale.persistance.EpoqueDAO;

public class EpoqueManager {

	private static EpoqueManager em = new EpoqueManager();
	// create the required DAO Factory
	private DAOFactory xmlFactory ; 
	private EpoqueDAO epoqueDAO ;
	private Map<String, Epoque> epoques = new HashMap<String, Epoque>();
	
	/**
	 * Constructeur
	 */
	private EpoqueManager(){
		xmlFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
		epoqueDAO = xmlFactory.getEpoqueDAO();
		
		List<Epoque> listEpoques = epoqueDAO.getAllEpoque();
		
		for (Epoque epoque : listEpoques) {
			this.epoques.put(epoque.getName(), epoque);
		}
		
	}
	
	/**
	 * Point d'acces au singleton
	 * @return l'instance unique de la classe
	 */
	public static EpoqueManager getInstance(){
		return em;
	}
	
	/**
	 * Liste de epoques
	 * @return la liste des noms des epoques
	 */
	public Set<String> getEpoques(){
		return this.epoques.keySet();
	}
	
	/**
	 * Retrouver une époque par son nom
	 * @param name le nom de l'epoque
	 * @return l'epoque correspondante
	 */
	public Epoque getEpoque(String name){
		return this.epoques.get(name);
	}
	
	/**
	 * Ajouter une epoque
	 * @param e l'époque à ajouter
	 */
	public void addEpoque(Epoque e){
		// create a new epoque
	    epoqueDAO.insertEpoque(e);
		this.epoques.put(e.getName(), e);
	}
	
	/**
	 * Supprimer une epoque
	 * @param e l'époque à supprimer
	 */
	public void removeEpoque(String epoqueName){
		this.epoques.remove(epoqueName);
	}

	/**
	 * Ajouter un maritime à l'époque
	 * @param epoqueName le nom de l'époque
	 * @param maritimeName du bateau
	 * @param length longeur du bateau
	 * @param width hauteur du bateau
	 * @param power puissance du bateau
	 */
	public void addMaritime(String epoqueName, String maritimeName,
			String length, String width, String power) {
		// TODO Auto-generated method stub
		Epoque e = this.epoques.get( epoqueName);
		Maritime m = new Boat(maritimeName, Integer.parseInt(length), Integer.parseInt(width), Integer.parseInt(power));
		epoqueDAO.insertMaritime(epoqueName,m);
		e.addMaritime(m);
	}


}
