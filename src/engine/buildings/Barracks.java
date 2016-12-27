package engine.buildings;

import engine.runner.GameSettings;
import engine.units.*;

/**
 * Building which will allow the player to build units
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.1
 */
public class Barracks extends Building implements FactoryPattern{
	
	public Barracks() {
		super(100);
	}

	/**
	 * Constructs the unit specified in the input string if the resource >= cost
	 * @param resourcesAvail - Player Resources to spend
	 * @param unit - Unit Type being requested
	 * @return - null if unit is too expensive. Unit if not
	 */
	public Unit constructUnit(int resourcesAvail, String unit){
		switch(unit){
			case "Scout":
				if(checkCost(resourcesAvail, GameSettings.SCOUT_COST))
					return new Scout();
				break;
			case "Worker":
				if(checkCost(resourcesAvail, GameSettings.WORKER_COST))
					return new Worker();
				break;
			case "Heavy":
				if(checkCost(resourcesAvail, GameSettings.HEAVY_COST))
					return new Heavy();
				break;
			case "Support":
				if(checkCost(resourcesAvail, GameSettings.SUPPORT_COST))
					return new Support();
				break;
			case "Genadier":
				if(checkCost(resourcesAvail, GameSettings.GRENADIER_COST))
					return new Grenadier();
				break;
			case "Turret":
				if(checkCost(resourcesAvail, GameSettings.TURRET_COST))
					return new Turret();
				break;
			case "LongRange":
				if(checkCost(resourcesAvail, GameSettings.LONGRANGE_COST))
					return new LongRange();
				break;
			case "Medic":
				if(checkCost(resourcesAvail, GameSettings.MEDIC_COST))
					return new Medic();
				break;
			case "AngelOfDeath":
				if(checkCost(resourcesAvail, GameSettings.ANGELOFDEATH_COST))
					return new AngelOfDeath();
				break;
			case "Swarm":
				if(checkCost(resourcesAvail, GameSettings.SWARM_COST))
					return new Swarm();
				break;
			case "Harbinger":
				if(checkCost(resourcesAvail, GameSettings.HARBINGER_COST))
					return new Harbinger();
				break;
		}
		//Unit could not be produced
		return null;
	}
}
