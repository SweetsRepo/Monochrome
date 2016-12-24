package engine.buildings;

import engine.board.Owner;
import engine.buildings.Barracks.UnitCosts;

public abstract class FactoryPattern {
	
	/**
	 * Checks the cost of the unit requested compared to the players resources
	 * @param resourcesAvail - Players resources
	 * @param unit - Enumeration of unit costs
	 * @return - True:Unit may be produced. False:Unit may not be produced
	 */
	protected boolean checkCost(int resourcesAvail, UnitCosts unit){
		if(resourcesAvail >= unit.getCost())
			return true;
		else
			return false;
	}
	
}
