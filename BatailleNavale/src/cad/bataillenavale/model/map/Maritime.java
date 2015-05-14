package cad.bataillenavale.model.map;

import java.awt.Point;

public abstract class Maritime implements Prototype {

	protected String name;
	protected int length, width, power, remainingCase;
	protected Point point; // top left point of the maritime in the map 
	
	/**
	 * Constructeur
	 * @param name nom du bateau
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
	 * Récupérer le nom du maritime
	 * @return le nom du maritime
	 */
	public String getName() {
		return name;
	}

	/**
	 * Modifier le nom
	 * @param name le nom du maritime
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Récupérer la taille du maritime
	 * @return la longueur du maritime
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Modifier la taille 
	 * @param length la longueur du maritime
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Récupérer la taille du maritime
	 * @return la hauteur du maritime
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Modifier la taille
	 * @param width la hauteur du maritime
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Récupérer la portée du maritime
	 * @return la puissance du maritime
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Modifier la puissance
	 * @param power la puissance du maritime
	 */
	public void setPower(int power) {
		this.power = power;
	}

	/**
	 * Récupérer le nombre de cases non touchées
	 * @return le nombre de cases non détruite du maritime
	 */
	public int getRemainingCase() {
		return remainingCase;
	}
	
	/**
	 * Point de départ du maritime sur la grille
	 * @param p le point le plus haut à gauche du maritime
	 */
	public void setPoint(Point p){
		this.point = p;
	}
	
	/**
	 * La première case du maritime
	 * @return le point en haut à gauche du maritime
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