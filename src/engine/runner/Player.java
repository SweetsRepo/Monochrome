package engine.runner;

import engine.tiles.Tile;
import engine.units.Unit;

import java.util.ArrayList;

/**
 * Class outline for player objects
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.5
 */
public abstract class Player implements Runnable {
	
	//Container for the amount of resources a player has
	private int resources;
	
	//Container for the special resources the given player has
	private int specialResources;
}
