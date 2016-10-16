package engine.buildings;

import engine.tiles.Owner;

public abstract class BuildingFactory {

	private int hp;
	private Owner owner;
	
	public BuildingFactory constructBuilding(int resources, int cost){
		if(resources >= cost)
			return null;
		else 
			return null;
	}
	
}
