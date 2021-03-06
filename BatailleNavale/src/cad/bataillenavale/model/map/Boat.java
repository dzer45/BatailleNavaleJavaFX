package cad.bataillenavale.model.map;


public class Boat extends Maritime {

	public Boat(String name, int length, int width, int power) {
		super(name, length, width, power);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Cloner le bateau : retourne une nouvelle instance avec les mêmes propriétés
	 */
	@Override
	public Prototype doClone() {
		return new Boat(this.name, this.length, this.width, this.power);
	}
}