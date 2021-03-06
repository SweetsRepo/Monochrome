package game.units;

import game.buildings.*;
import game.runner.GameSettings;

/**
 * Defines the worker unit who creates new buildings for the player
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.3
 */
public class Worker extends Unit implements FactoryPattern {

	public Worker(){
		super(100, 2, 1, 5, 1, true, true);
	}

	public Building constructBuilding(int resourcesAvail, String building){
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
