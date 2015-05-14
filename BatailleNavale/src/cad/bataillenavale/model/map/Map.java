package cad.bataillenavale.model.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Case.State;

public class Map {
	
	int missed = 0, touched = 0, maritimesRemaining = 0;
	public Case[][] cases; 
	List<Maritime> maritimes = new ArrayList<>();
	
	public Map(int length){
		this.cases = new Case[length][length];
	}
	
	private int getLength(){
		return this.cases.length;
	}
	
	private int getWidth(){
		return this.cases[0].length;
	}
	
	public List<Maritime> getMaritimes(){
		return maritimes;
	}
	
	public void addMaritime(int x, int y,  Maritime maritime) throws MapException {
		
		// tests if the Maritime can be added -----------------------------------------------
		
		// the Maritime is bigger than the array or it comes out of the array ?
		if(x + maritime.getLength() > getLength() || y + maritime.getWidth() > getWidth()){
			throw new MapException("Le Maritime sort de la grille !");
		}
		
		// the Maritime encroach on another ?
		for(int i = 0; i < maritime.getLength(); i++)
		{
			for(int j = 0; j < maritime.getWidth(); j++)
			{
				if(this.cases[x + i][y + j]  instanceof MaritimeCase){
					throw new MapException("Le Maritime empiete sur un autre !");
				}	
			}
		}
		
		// ----------------------------------------------------------------------------------
		
		maritime.setPoint(new Point(x, y));
		maritimes.add(maritime);
		this.maritimesRemaining++;
		
		try {
			
	
		// adds the Maritime -------------------------------------------
		for(int i = 0; i < maritime.getLength(); i++)
		{
			for(int j = 0; j < maritime.getWidth(); j++) 
				this.cases[x + i][y + j] = new MaritimeCase(this, maritime);
		}
		
		// updates the Reachable map ---------------------------------------------
	
		
		x -= maritime.getPower();
		y -= maritime.getPower();
		
		
		for(int i = 0; i < maritime.getLength() + (maritime.getPower()*2); i++)
		{
			for(int j = 0; j < maritime.getWidth() + (maritime.getPower()*2); j++)
			{
		
				if(x+i >= this.getLength() || y+j >= this.getWidth() || x+i < 0 || y+j < 0)
					continue;

			
				Case c = this.cases[x + i][y + j]; 
				if(c != null)
					c.incReacheable();
				else          
				{
					c = new EmptyCase(this);
					c.incReacheable();
					this.cases[x + i][y + j] = c;
				}				
			}
		} 
		
		} catch(ArrayIndexOutOfBoundsException e){}
	}
	
	/**
	 * If the coordinates can be played given the configuration of the Maritimes and their power
	 * @param x
	 * @param y
	 * @return
	 * @throws MapException 
	 */
	public boolean reacheableShoot(int x, int y) throws MapException{
		if(x < 0 || y < 0 || x >= getLength() || y >= getWidth()) 
			throw new MapException("Case hors de la map"+x+" "+y);
		
		Case c = this.cases[x][y];
		
		return c != null && this.cases[x][y].isReachable();
	}
	

	public boolean isPlayed(int x, int y) throws MapException{
		if(x < 0 || y < 0 || x >= getLength() || y >= getWidth()) 
			throw new MapException("Case hors de la map"+x+" "+y);
		return !this.cases[x][y].getState().equals(State.NOTPLAYED);
	}
	
	public void shoot(int x, int y) throws MapException {
		if(x < 0 || y < 0 || x >= getLength() || y >= getWidth()) 
			throw new MapException("Case hors de la map"+x+" "+y);
		this.cases[x][y].shoot();
	}
	
	/**
	 * Called when a maritime is destroyed to update the reachable map
	 * @param m the maritime destroyed
	 * @throws MapException the maritime is not destroyed
	 */
	public void updateReachableMap(Maritime m) throws MapException {
		
		if(!m.isDestroyed())
		{
			throw new MapException("Le Maritime n'est pas detruit !");
		}
		
		int x = m.getPoint().x - m.getPower();
		int y = m.getPoint().y - m.getPower();
		
		for(int i = 0; i < m.getLength()+m.getPower()+2; i++)
		{
			for(int j = 0; j < m.getWidth()+m.getPower()+2; j++)
			{
				if((x+i) > 0 && (y+j) > 0 && (x+i) < getLength() && (y+j) < getWidth())
					this.cases[x + i][y + j].descReacheable();	
			}
		}
		
		this.maritimesRemaining--;
	}

	/**
	 * Called to add the EmptyCases when all Maritimes are placed
	 */
	public void addEmptyCases(){
		for(int i = 0; i < getLength(); i++)
		{
			for(int j = 0; j < getWidth(); j++)
			{
				if(this.cases[i][j] == null)
					this.cases[i][j] = new EmptyCase(this);
			}
		} 
	}
	
	public int getMissed() {
		return missed;
	}

	public int getTouched() {
		return touched;
	}

	public int getMaritimeRemaining() {
		return maritimesRemaining;
	}
	
	public Case getCase(int i, int j){
		return this.cases[i][j];
	}
	
}