package cad.bataillenavale.persistance;


public abstract class DAOFactory {
	// List of DAO types supported by the factory
	public static final int XML = 1;
	public static final int ORACLE = 2;
	
	public abstract GameDAO getGameDAO();
	
	public abstract EpoqueDAO getEpoqueDAO();
	
	public static DAOFactory getDAOFactory(
		      int whichFactory) {
		  
		    switch (whichFactory) {
		      case XML: 
		          return new XMLDAOFactory();
		      case ORACLE    : 
		          return new OracleDAOFactory();      
		      default           : 
		          return null;
		    }
		  }


}
