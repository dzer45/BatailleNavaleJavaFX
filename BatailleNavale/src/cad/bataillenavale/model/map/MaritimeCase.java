package cad.bataillenavale.model.map;

import cad.bataillenavale.model.exception.MapException;

public class MaritimeCase extends Case {

	private Maritime maritime;
	
	public MaritimeCase(Map map, Maritime m){
		super(map);
		this.maritime = m;
	}

	public Maritime getMaritime() {
		return maritime;
	}

	@Override
	protected void shoot() {
		this.s = State.TOUCHED;
		this.map.touched++;
		this.maritime.play();
		if(this.maritime.isDestroyed())
		{
			try {
				this.map.updateReachableMap(maritime);
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}