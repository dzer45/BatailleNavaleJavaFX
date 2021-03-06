package model.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import cad.bataillenavale.model.exception.MapException;
import cad.bataillenavale.model.map.Boat;
import cad.bataillenavale.model.map.Case;
import cad.bataillenavale.model.map.Case.State;
import cad.bataillenavale.model.map.Map;
import cad.bataillenavale.model.map.Maritime;
import cad.bataillenavale.model.map.MaritimeCase;

public class TestMap {

	private final int LENGTH = 20, WIDTH = 10, MARLENGTH = 3, MARWIDTH = 2, POWER = 2, X = 4, Y = 4;
	
	private Map emptyMap, map;
	private Maritime maritime;
	
	@Before
	public void setUp() throws Exception {
		emptyMap = new Map(LENGTH);
		map = new Map(LENGTH);
		maritime = new Boat("boat",MARLENGTH, MARWIDTH, POWER);
		map.addMaritime(X, Y, maritime);
		map.addEmptyCases();
	}
	
	@Test
	public void testAddMaritime() throws MapException {
		int x = X, y = Y;
		
		Maritime maritime = new Boat("boat",MARLENGTH, MARWIDTH, POWER);
		emptyMap.addMaritime(x, y, maritime);
		
		boolean good = true;
		
		for(int i = 0; i < maritime.getLength(); i++)
		{
			for(int j = 0; j < maritime.getWidth(); j++)
			{
				if(!(emptyMap.cases[x + i][y + j] instanceof MaritimeCase))
				{
					good = false;
				}
			}
		}
	
		x -= maritime.getPower();
		y -= maritime.getPower();
		
		for(int i = 0; i < maritime.getLength()+maritime.getPower()+2; i++)
		{
			for(int j = 0; j < maritime.getWidth()+maritime.getPower()+2; j++)
			{
				Case c = emptyMap.cases[x + i][y + j]; 
				if(!c.isReachable())
				{
					good = false;
				}
			}
		}
		
		if(emptyMap.getMaritimeRemaining() != 1) good = false;
		
		assertTrue(good);
	}
	
	@Test(expected = MapException.class)
	public void testAddMaritimeTooBigException() throws MapException {
		
		Maritime maritime = new Boat("boat",LENGTH+1, WIDTH+1, POWER);
		emptyMap.addMaritime(X, Y, maritime);
		
		fail("Exception not raised");
	}
	
	@Test(expected = MapException.class)
	public void testAddMaritimeEncroachException() throws MapException {
		
		Maritime maritime = new Boat("boat",MARLENGTH, MARWIDTH, POWER);
		map.addMaritime(X, Y, maritime);
		
		fail("Exception not raised");
	}

	@Test
	public void testReachableEmptyCase() throws MapException {
		int x = 3, y = 3;
		//System.out.println(map.cases[x][y].getClass()+" : "+map.cases[x][y].getReachable());
		assertTrue(map.isReacheable(x, y));
	}
	
	@Test
	public void testNotReachableEmptyCase() throws MapException {
		int x = 0, y = 0;
		//System.out.println(map.cases[x][y].getClass()+" : "+map.cases[x][y].getReachable());
		assertFalse(map.isReacheable(x, y));
	}
	
	@Test
	public void testReachableMaritimeCase() throws MapException {
		int x = 5, y = 5;
		//System.out.println(map.cases[x][y].getClass()+" : "+map.cases[x][y].getReachable());
		assertTrue(map.isReacheable(x, y));
	}

	@Test
	public void testShootTouched() throws MapException { // MaritimeCase played
		int x = 5, y = 5;
		map.shoot(x, y);
		
		//assertEquals(State.TOUCHED, map.cases[x][y].getState());
		assertTrue(map.cases[x][y].getState().equals(State.TOUCHED) && map.getTouched() == 1);
	}
	
	@Test
	public void testShootMissed() throws MapException { // EmptyCase played
		int x = 0, y = 0;
		map.shoot(x, y);
		
		//assertEquals(State.MISSED, map.cases[x][y].getState());
		assertTrue(map.cases[x][y].getState().equals(State.MISSED) && map.getMissed() == 1);
	}
	
	@Test
	public void testMaritimeDestroyed() throws MapException { 
		
		// plays all the MaritimeCase 
		for (int i = 0; i < MARLENGTH; i++) {
			for (int j = 0; j < MARWIDTH; j++) {
				map.shoot(X+i, Y+j);
			}
		}		
		
		// tests if the Reachable map has been updated
		boolean good = true;
		
		int x = X, y = Y;		
		x -= POWER;
		y -= POWER;
		
		for(int i = 0; i < MARLENGTH+POWER+2; i++)
		{
			for(int j = 0; j < MARWIDTH+POWER+2; j++)
			{
				Case c = map.cases[x + i][y + j]; 
				if(c.isReachable())
				{
					good = false;
				}
			}
		}
		
		if(map.getMaritimeRemaining() != 0) good = false;
		
		assertTrue(good);
	}
	
	@Test(expected = MapException.class)
	public void testMaritimeNotDestroyedException() throws MapException { 
		map.updateReachableMap(maritime); // maritime is not destroyed
		
		fail("Exception not raised");
	}
	
	@Test
	public void testAdd2MaritimesReachable() throws MapException {		
		Maritime maritime = new Boat("boat", 1, 1, 10);
		emptyMap.addMaritime(0, 0, maritime);
		
		maritime = new Boat("boat", 1, 1, 10);
		emptyMap.addMaritime(5, 5, maritime);
		
		Case c = emptyMap.cases[2][2]; 
	
		assertTrue(c.getReachable() == 2);
	}
	
	@Test
	public void testDestroyOneOf2MaritimesReachable() throws MapException {		
		Maritime maritime = new Boat("boat", 1, 1, 10);
		emptyMap.addMaritime(0, 0, maritime);
		
		maritime = new Boat("boat", 1, 1, 10);
		emptyMap.addMaritime(5, 5, maritime);
		
		emptyMap.shoot(0, 0);
		
		Case c = emptyMap.cases[2][2]; 
	
		assertTrue(c.getReachable() == 1);
	}
}
