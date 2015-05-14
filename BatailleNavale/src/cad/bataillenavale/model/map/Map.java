package cad.bataillenavale.model.map;

import java.awt.Point;

import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Case.State;

public class Map {
	
	int missed = 0, touched = 0, maritimesRemaining = 0;
	public Case[][] cases; 
	
	/**
	 * Constructeur
	 * @param length taille de la grille (carée)
	 */
	public Map(int length){
		this.cases = new Case[length][length];
	}
	
	/**
	 * Récupérer la taille de la grille
	 * @return taille en longueur de la grille
	 */
	private int getLength(){
		return this.cases.length;
	}
	
	/**
	 * Récupérer la taille de la grille
	 * @return taille en hauteur de la grille
	 */
	private int getWidth(){
		return this.cases[0].length;
	}
	
	/**
	 * Ajouter un maritime à la grille
	 * @param x
	 * @param y
	 * @param maritime le maritime à ajouter
	 * @throws MapException si le maritime empiète sur un autre ou si il sort de la grille
	 */
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
	 * Si la coordonnées peut être jouée compte tenu de la configuration des maritimes et de leur puissances
	 * @param x
	 * @param y
	 * @return vrai si c'est le cas
	 * @throws MapException si la case sort de la grille
	 */
	public boolean isReacheable(int x, int y) throws MapException{
		if(x < 0 || y < 0 || x >= getLength() || y >= getWidth()) 
			throw new MapException("Case hors de la map"+x+" "+y);
		
		Case c = this.cases[x][y];
		
		return c != null && this.cases[x][y].isReachable();
	}
	

	/**
	 * Si il est possible de tirer sur la case compte tenu de la reachable map
	 * @param x
	 * @param y
	 * @return vrai si c'est le cas
	 * @throws MapException si la case sort de la grille
	 */
	public boolean isPlayed(int x, int y) throws MapException{
		if(x < 0 || y < 0 || x >= getLength() || y >= getWidth()) 
			throw new MapException("Case hors de la map"+x+" "+y);
		return !this.cases[x][y].getState().equals(State.NOTPLAYED);
	}
	
	/**
	 * Tirer sur un maritime
	 * @param x 
	 * @param y
	 * @throws MapException si la case tirée sort de la map
	 * @return vrai si une case à été détruite
	 */
	public boolean shoot(int x, int y) throws MapException {
		if(x < 0 || y < 0 || x >= getLength() || y >= getWidth()) 
			throw new MapException("Case hors de la map"+x+" "+y);
		
		int nb = touched;
		
		this.cases[x][y].shoot();
		
		if(nb < touched) return true;
		else return false;
	}
	
	/**
	 * Met a jour la Reachable map lorsque un maritime est détruit
	 * @param m le maritime détruit
	 * @throws MapException si le maritime n'est pas détruit
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
	 * Ajoute les EmptyCases lorsque tous les Maritimes sont placés
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
	
	/**
	 * Récupérer le nombre de tirs tombés dans l'eau
	 * @return nombre de EmptyCase touchées
	 */
	public int getMissed() {
		return missed;
	}

	/**
	 * Récupérer le nombre de tir dans le mille
	 * @return nombre de MaritimeCase touchées
	 */
	public int getTouched() {
		return touched;
	}

	/**
	 * Récupérer le nombre de maritimes restant
	 * @return nombre de maritime pas encore détruit
	 */
	public int getMaritimeRemaining() {
		return maritimesRemaining;
	}
	
	/**
	 * Retourne la case à l'index x y
	 * @param x
	 * @param y
	 * @return la case correspondante
	 */
	public Case getCase(int x, int y){
		return this.cases[x][y];
	}
	
}