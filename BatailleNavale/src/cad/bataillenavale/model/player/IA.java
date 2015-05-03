package cad.bataillenavale.model.player;

import java.util.Random;

import cad.bataillenavale.model.BatailleNavale;
import cad.bataillenavale.model.epoque.EpoqueManager;
import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;

public class IA extends Player {
	
	private BatailleNavale model;
	
	public IA(BatailleNavale model){
		this.model = model;
	}
	
	public void addMaritime(String maritimeName){
		
		Map myMap = model.getMapIA();
		
		Maritime m = (Maritime)model.getEpoque(model.getCurrentEpoque().getName()).cloneMaritime(maritimeName); // clone
		
		Random random = new Random();
		
		boolean added = false;
		
		while(!added){
				
			int x = random.nextInt(model.getLength() - m.getLength());
	        int y = random.nextInt(model.getWidth() - m.getLength());  
	       
			try {
				
				myMap.addMaritime(x, y, m);
				added = true; 
				System.out.println("IA added a maritime : "+x+" "+y);
				
			} catch (MapException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	public void shoot(){
		
		if(model.isGameFinished()) return;
		
		Map myMap = model.getMapIA();
		Map playerMap = model.getMapPlayer();
		
		Random random = new Random();
        
		int x = 0, y = 0;
        boolean reacheable = false;
        boolean alreadyPlayed = true;
        
        do {
            
        	x = random.nextInt(model.getLength());
            y = random.nextInt(model.getWidth());
            
            try {
            	
				reacheable = myMap.reacheableShoot(x, y);
				alreadyPlayed = playerMap.isPlayed(x, y);
				
			} catch (MapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } while((!reacheable) || (alreadyPlayed)); // we search for a place wich we can reach it and which is not already played
        // we can have infinite loop ! 
        
        try {
        	
			model.shoot(this, x, y);
			
		} catch (MapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
