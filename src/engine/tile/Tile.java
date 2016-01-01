package engine.tile;

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
	//Integer values representing the location of the tile on the board.
	private int row;
	private int column;
	//Boolean value representing if the tile is currently occupied by an actor.
	private boolean isOccupied;
	//Boolean value representing if the tile in curretnly controlled by a player.
	private boolean isControlled;
	//Unit currently occupying the object. Set to null if isOccupied = false.
	private Unit unit;
	
	/**
	 * Default Constructor - Makes tile with 5000 resources by default.
	 */
	public Tile (int r, int c){
		this.resources = 5000;
		this.row = r;
		this.column = c;
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
	
	/**
	 * Assigns the current unit to the spot
	 * @param curr - The unit to assign
	 * Changes occupied to true
	 */
	public void setUnit(Unit curr){
		this.unit = curr;
		setOccupied(true);
	}
	
	/**
	 * Removes the unit currently occupying the spot.
	 * Changes occupied to false
	 */
	public void removeUnit(){
		this.unit = null;
		setOccupied(false);
	}
	/**
	 * Returns a string representation of the coordinates
	 * @return - Row x Column
	 */
	public String getCoordinates(){
		return this.row +" x "+ this.column;
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
