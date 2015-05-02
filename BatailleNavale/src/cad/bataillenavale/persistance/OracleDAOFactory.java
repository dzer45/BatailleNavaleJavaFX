package cad.bataillenavale.persistance;

public class OracleDAOFactory extends DAOFactory {

	@Override
	public PlayerDAO getPlayerDAO() {
		// TODO Auto-generated method stub
		return new OraclePlayerDAO();
	}

	@Override
	public EpoqueDAO getEpoqueDAO() {
		// TODO Auto-generated method stub
		return new OracleDAOEpoque();
	}

}
