package engine.buildings;

import engine.units.*;

/**
 * Building which will allow the player to build units
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.1
 */
public class Barracks {

	/**
	 * Enumeration of all the units and their production cost
	 */
	public static enum UnitCosts{ SCOUT(100), WORKER(200), HEAVY(300), SUPPORT(400), GRENADIER(500), 
		TURRET(600), LONGRANGE(700), MEDIC(800), ANGELOFDEATH(900), SWARM(1000), HARBINGER(1100); 
		private int cost;
		UnitCosts(int cost){
			this.cost = cost;
		}
		public int getCost(){
			return this.cost;
		}
	};
	
	public Unit constructUnit(int resourcesAvail, String unit){
		switch(unit){
			case "Scout":
				if(checkCost(resourcesAvail, UnitCosts.SCOUT))
					return new Scout();
				break;
			case "Worker":
				if(checkCost(resourcesAvail, UnitCosts.WORKER))
					return new Worker();
				break;
			case "Heavy":
				if(checkCost(resourcesAvail, UnitCosts.HEAVY))
					return new Heavy();
				break;
			case "Support":
				if(checkCost(resourcesAvail, UnitCosts.SUPPORT))
					return new Support();
				break;
			case "Genadier":
				if(checkCost(resourcesAvail, UnitCosts.GRENADIER))
					return new Grenadier();
				break;
			case "Turret":
				if(checkCost(resourcesAvail, UnitCosts.TURRET))
					return new Turret();
				break;
			case "LongRange":
				if(checkCost(resourcesAvail, UnitCosts.LONGRANGE))
					return new LongRange();
				break;
			case "Medic":
				if(checkCost(resourcesAvail, UnitCosts.MEDIC))
					return new Medic();
				break;
			case "AngelOfDeath":
				if(checkCost(resourcesAvail, UnitCosts.ANGELOFDEATH))
					return new AngelOfDeath();
				break;
			case "Swarm":
				if(checkCost(resourcesAvail, UnitCosts.SWARM))
					return new Swarm();
				break;
			case "Harbinger":
				if(checkCost(resourcesAvail, UnitCosts.HARBINGER))
					return new Harbinger();
				break;
		}
		//Unit could not be produced
		return null;
	}
	
	/**
	 * Checks the cost of the unit requested compared to the players resources
	 * @param resourcesAvail - Players resources
	 * @param unit - Enumeration of unit costs
	 * @return - True:Unit may be produced. False:Unit may not be produced
	 */
	private boolean checkCost(int resourcesAvail, UnitCosts unit){
		if(resourcesAvail >= unit.getCost())
			return true;
		else
			return false;
	}
}
