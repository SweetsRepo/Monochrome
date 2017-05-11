package game.buildings;

public interface FactoryPattern {
	
	/**
	 * Checks the cost of the unit requested compared to the players resources
	 * @param resourcesAvail - Players resources
	 * @param unit - Enumeration of unit costs
	 * @return - True:Unit may be produced. False:Unit may not be produced
	 */
	default boolean checkCost(int resourcesAvail, int unitCost){
		if(resourcesAvail >= unitCost)
			return true;
		else
			return false;
	}
	
}
