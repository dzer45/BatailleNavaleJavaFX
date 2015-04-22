package cad.bataillenavale.model.map;

public class EmptyCase extends Case {
	
 //number of maritime which reaches it

	protected EmptyCase(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void shoot() {
		this.s = State.MISSED;
		this.map.missed++;
	}
	
	

}