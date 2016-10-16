package engine.buildings;

import engine.units.Unit;

/**
 * Building which will allow the player to build units
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.1
 */
public class Barracks {

	public static enum UnitCosts{};
	
	public Unit constructUnit(int resourcesAvail, int resourcesCost){
		if(resourcesAvail >= resourcesCost)
			return null;
		else
			return null;
	}
}
