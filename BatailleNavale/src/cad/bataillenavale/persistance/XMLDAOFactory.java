package cad.bataillenavale.persistance;

public class XMLDAOFactory extends DAOFactory {

	@Override
	public GameDAO getGameDAO() {
		// TODO Auto-generated method stub
		 return XMLDAOGame.getInstance();
	}

	@Override
	public EpoqueDAO getEpoqueDAO() {
		// TODO Auto-generated method stub
		return XMLDAOEpoque.getInstance();
	}

}
