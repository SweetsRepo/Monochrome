package engine.tile;

import engine.units.Unit;

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
	private boolean isOccupied;
	//Boolean value representing if the tile in curretnly controlled by a player.
	private boolean isControlled;
	//Unit currently occupying the object. Set to null if isOccupied = false.
	private Unit unit;
	
	/**
	 * Default Constructor - Makes tile with 5000 resources by default.
	 */
	public Tile (){
		this.resources = 5000;
		this.isOccupied = false;
		this.isControlled = false;
		this.unit = null;
	}
	
	/**
	 * Creates new tile object with the specified amount of resources
	 * @param resources (int) - The amount of resources for the given object.
	 */
	public Tile(int resources){
		this.resources = resources;
		this.isOccupied = false;
		this.isControlled = false;
		this.unit = null;
	}
	
	/**
	 * Mines the resources on the tile, if there are resources available.
	 * If not, then false is returned, signaling no mining could occur.
	 * @return boolean - Resources available
	 */
	public boolean mine(){
		if(this.resources != 0){
			this.resources -= 10;
			return true;
		}
		else
			return false;
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
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
}
