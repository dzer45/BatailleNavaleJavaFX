package cad.bataillenavale.persistance;

import cad.bataillenavale.model.BatailleNavale;

public interface GameDAO {
	
	  public void  save(BatailleNavale p);
	  public BatailleNavale restore();
}
