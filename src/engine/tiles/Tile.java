package engine.tiles;

import game.runner.Player;
import game.units.Unit;

/**
 * Parent class for all Tile objects. Specifies an integer for resources,
 * boolean for occupation state, and default method implementation.
 * @author Chris
 * @version 0.1
 */



public abstract class Tile {
	
	//Integer value of the resources available on this tile.
	private int resources;
	//Boolean value representing if the tile is currently occupied by an actor.
	protected boolean isOccupied;
	//Unit currently occupying the object. Set to null if isOccupied = false.
	protected Unit unit;
	//Enum to indicate who controls the tile. 
	protected Controller owner;
	
	/**
	 * Default Constructor - Makes tile with no resources by default.
	 */
	public Tile (){
		this.resources = 0;
		this.isOccupied = false;
		this.unit = null;
		this.owner = Controller.Neutral;
	}
	
	
	/**
	 * Mines the resources on the tile, if there are resources available.
	 * If not, then 0 is returned.
	 * @return boolean - Resources available
	 */
	public int mine(){
		if(this.resources == 0){
			return 0;
		}
		else if(this.resources < 100){
			int remainder = this.resources;
			this.resources = 0;
			return remainder;
		}
		else{
			this.resources -= 100;
			return 100;
		}
	}
	
	/**
	 * Assigns the current unit to the spot
	 * @param curr - The unit to assign
	 * Changes occupied to true
	 */
	public void setUnit(Unit curr){
		this.unit = curr;
		this.isOccupied = true;
	}
	
	/**
	 * Removes the unit currently occupying the spot.
	 * Changes occupied to false
	 */
	public void removeUnit(){
		this.unit = null;
		this.isOccupied = false;
	}
	
	/**
	 * Access the unit on the given tile for it's attributes
	 * @return
	 */
	public Unit getUnit(){
		return this.unit;
	}
	
	public void setResources(int res){
		this.resources = res;
	}
	
	public int getResources(){
		return this.resources;
	}

	public boolean isOccupied() {
		return isOccupied;
	}


	public Controller getOwner() {
		return owner;
	}


	public void setOwner(Controller owner) {
		this.owner = owner;
	}
}
