package cad.bataillenavale.model.map;

import java.awt.Point;

import cad.bataillenavale.model.epoque.Prototype;

public abstract class Maritime implements Prototype {

	protected String name;
	protected int length, width, power, remainingCase;
	protected Point point; // top left point of the maritime in the map 
	
	protected Maritime(String name, int length, int width, int power){
		this.name = name;
		this.length = length;
		this.width = width;
		this.power = power;
		this.remainingCase = length * width;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getRemainingCase() {
		return remainingCase;
	}
	
	public void setPoint(Point p){
		this.point = p;
	}
	
	public Point getPoint(){
		return point;
	}

	public void play(){
		this.remainingCase--;
	}

	public boolean isDestroyed(){
		return remainingCase == 0;
	}
}