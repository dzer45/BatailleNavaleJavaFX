package cad.bataillenavale.persistance;

import java.util.List;

import cad.bataillenavale.model.epoque.Epoque;
import cad.bataillenavale.model.map.Maritime;

public interface EpoqueDAO {

	public void insertEpoque(Epoque p);

	public boolean deleteEpoque(Epoque p);

	public Epoque findEpoque(Epoque p);

	public List<Epoque> getAllEpoque();

	public void insertMaritime(String epoqueName, Maritime m);



}
