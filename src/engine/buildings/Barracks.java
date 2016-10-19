package engine.buildings;

import engine.units.Unit;

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
	
	public Unit constructUnit(int resourcesAvail, int resourcesCost){
		if(resourcesAvail >= resourcesCost)
			return null;
		else
			return null;
	}
}
