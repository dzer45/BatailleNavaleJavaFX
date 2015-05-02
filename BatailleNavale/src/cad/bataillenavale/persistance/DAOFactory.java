package cad.bataillenavale.persistance;


public class DAOFactory {

	private static DAOFactory daof = new DAOFactory();
	
	private DAOFactory(){
		
	}
	
	public static DAOFactory getInstance(){
		return daof;
	}
	
	public DAOPlayer getDAOPlayer(){
		return null;
	}
	
	public DAOEpoque getDAOEpoque(){
		return null;
	}
}
