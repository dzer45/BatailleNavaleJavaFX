package cad.bataillenavale.persistance;

import cad.bataillenavale.model.epoque.Epoque;

public interface EpoqueDAO {
	
	  public void insertEpoque(Epoque p);
	  public boolean deleteEpoque(Epoque p);
	  public Epoque findEpoque(Epoque p);
}

