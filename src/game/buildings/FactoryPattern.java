package game.buildings;

import game.runner.GameSettings;

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
	
	/**
	 * Returns the building object to construct given the resource constraints are met
	 * @param resourcesAvail - Resources held by the active unit player
	 * @param building - Cost of the building given by the game db
	 * @return Building object to place on tile
	 */
	default public Building constructBuilding(int resourcesAvail, String building){
		switch(building){
		case "Bunker":
			if(checkCost(resourcesAvail, GameSettings.BUNKER_COST))
				return new Bunker();
			break;
		case "Barracks":
			if(checkCost(resourcesAvail, GameSettings.BARRACKS_COST))
				return new Barracks();
			break;
		case "Research Center":
			if(checkCost(resourcesAvail, GameSettings.RESEARCHCENTER_COST))
				return new ResearchCenter();
			break;
		}
	//Building could not be produced
	return null;
	}
}
