package cad.bataillenavale.model.map;

import java.awt.Point;

public abstract class Maritime implements Prototype {

	protected String name;
	protected int length, width, power, remainingCase;
	protected Point point; // top left point of the maritime in the map 
	
	/**
	 * Constructeur
	 * @param name du bateau
	 * @param length longeur du bateau
	 * @param width hauteur du bateau
	 * @param power puissance du bateau
	 */
	protected Maritime(String name, int length, int width, int power){
		this.name = name;
		this.length = length;
		this.width = width;
		this.power = power;
		this.remainingCase = length * width;
	}
	
	/**
	 * 
	 * @return le nom du maritime
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return la longueur du maritime
	 */
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * 
	 * @return la hauteur du maritime
	 */
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 
	 * @return la puissance du maritime
	 */
	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * 
	 * @return le nombre de cases non détruite du maritime
	 */
	public int getRemainingCase() {
		return remainingCase;
	}
	
	public void setPoint(Point p){
		this.point = p;
	}
	
	/**
	 * La première case du maritime
	 * @return le point en haut a gauche du maritime
	 */
	public Point getPoint(){
		return point;
	}

	/**
	 * Décrémenter le nombre de case non détruites
	 */
	public void descRemaningCase(){
		this.remainingCase--;
	}

	/** 
	 * Si le maritimes est détruit (si toutes ses cases sont détruites)
	 * @return vrai si c'est le cas
	 */
	public boolean isDestroyed(){
		return remainingCase == 0;
	}
}