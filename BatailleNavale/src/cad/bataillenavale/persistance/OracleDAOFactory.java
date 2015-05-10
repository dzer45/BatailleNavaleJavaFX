package cad.bataillenavale.persistance;

public class OracleDAOFactory extends DAOFactory {

	@Override
	public GameDAO getGameDAO() {
		// TODO Auto-generated method stub
		return new OracleGameDAO();
	}

	@Override
	public EpoqueDAO getEpoqueDAO() {
		// TODO Auto-generated method stub
		return new OracleDAOEpoque();
	}

}
