package engine.runner;

import engine.board.Board;
import engine.board.Owner;
import engine.board.Tile;
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

	//ArrayList to contain all the units that the player has
	protected ArrayList<Unit> units;
	
	//Owner identification for usage with tile operations
	protected Owner faction;
	
	//Integer representing the player ID
	public int pid;
	
	/**
	 * Default Constructor for both player type objects
	 */
	public Player(int pid, Owner faction){
		this.units = new ArrayList<Unit>();
		this.resources = 500;
		this.specialResources = 0;
		this.pid = pid;
		this.faction = faction;
	}

	
	
	public ArrayList<Unit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<Unit> units) {
		this.units = units;
	}
	
	public void addUnit(Unit unit){
		this.units.add(unit);
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

	public int getSpecialResources() {
		return specialResources;
	}

	public void setSpecialResources(int specialResources) {
		this.specialResources = specialResources;
	}



	public Owner getFaction() {
		return faction;
	}



	public void setFaction(Owner faction) {
		this.faction = faction;
	}
}
