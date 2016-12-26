package engine.units;

import engine.buildings.FactoryPattern;

public class Worker extends Unit implements FactoryPattern {

	public Worker(){
		super(100, 2, 5, 1, true, true);
	}

	/**
	 * Enumeration of all the buildings and their production cost
	 */
	public static enum BuildingCosts{ BUILDING0(100);
		private int cost;
		BuildingCosts(int cost){
			this.cost = cost;
		}
		public int getCost(){
			return this.cost;
		}
	};
	
	public void constructUnit(int resourcesAvail, String unit){
		switch(unit){
			case "Building0":
				if(checkCost(resourcesAvail, BuildingCosts.BUILDING0.cost))
					return;
				break;
		}
	}
	
}
