package cad.bataillenavale.model.map;

import cad.bataillenavale.model.epoque.Prototype;

public class Boat extends Maritime {

	public Boat(int length, int width, int power) {
		super(length, width, power);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Prototype doClone() {
		return new Boat(this.length, this.width, this.power);
	}
}