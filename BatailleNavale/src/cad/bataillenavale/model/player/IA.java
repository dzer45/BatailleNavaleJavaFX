package cad.bataillenavale.model.player;

public class IA extends Player {
	
	private Difficult difficult;
	
	public void addMaritime(String maritimeName){
		difficult.addMaritime(maritimeName);
	}
	
	public void shoot(){
		difficult.shoot(this);
	}

	public Difficult getDifficult() {
		return difficult;
	}

	public void setDifficult(Difficult difficult) {
		this.difficult = difficult;
	}
	
}
