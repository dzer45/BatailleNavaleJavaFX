package cad.bataillenavale.persistance;

public class XMLDAOFactory extends DAOFactory {

	@Override
	public PlayerDAO getPlayerDAO() {
		// TODO Auto-generated method stub
		 return new XMLPlayerDAO();
	}

	@Override
	public EpoqueDAO getEpoqueDAO() {
		// TODO Auto-generated method stub
		return XMLDAOEpoque.getInstance();
	}

}
