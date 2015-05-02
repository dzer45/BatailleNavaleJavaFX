package cad.bataillenavale.persistance;

import cad.bataillenavale.model.player.Player;

public interface PlayerDAO {
	
	  public int insertPlayer(Player p);
	  public boolean deletePlayer(Player p);
	  public Player findPlayer(Player p);
}
