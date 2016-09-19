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
	
	private ArrayList<Unit> units;
	
	/**
	 * Default Constructor for both player type objects
	 */
	public Player(){
		this.units = new ArrayList<Unit>();
		this.resources = 500;
		this.specialResources = 0;
	}
}
