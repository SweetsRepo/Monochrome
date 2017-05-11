package engine.board;

import game.buildings.Building;
import game.runner.Player;
import game.units.Unit;

/**
 * Parent class for all Tile objects. Specifies an integer for resources,
 * boolean for occupation state, and default method implementation.
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.9
 */



public abstract class Tile {
	
	//Integer value of the resources available on this tile.
	private int resources;

	//Boolean value representing if the tile is currently occupied by an actor.
	protected boolean isOccupied;
	
	//Unit currently occupying the object. Set to null if isOccupied = false.
	protected Unit unit;
	
	//Building currently occupying this object. Set to null if isOccupied = false
	protected Building building;
	
	//Enum to indicate who controls the tile. 
	protected Owner owner;
	
	/**
	 * Default Constructor - Makes tile with no resources by default.
	 */
	public Tile (){
		this.resources = 0;
		this.isOccupied = false;
		this.unit = null;
		this.building = null;
		this.owner = Owner.Neutral;
	}
	
	
	/**
	 * Mines the resources on the tile, if there are resources available.
	 * If not, then 0 is returned.
	 * @return boolean - Resources available
	 */
	public int mine(){
		if(this.unit != null){
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
		//Catch-all return of 0 if no unit is present
		return 0;
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
	 * Assigns the current unit to the spot
	 * @param curr - The unit to assign
	 * Changes occupied to true
	 */
	public void setBuilding(Building curr){
		this.building = curr;
		this.isOccupied = true;
	}
	
	/**
	 * Removes the unit currently occupying the spot.
	 * Changes occupied to false
	 */
	public void removeBuilding(){
		this.building = null;
		this.isOccupied = false;
	}
	
	public Unit getUnit(){
		return this.unit;
	}
	
	public Building getBuilding(){
		return this.building;
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
	
	public void setOccupied(boolean occupied){
		this.isOccupied = occupied;
	}

	public Owner getOwner() {
		return owner;
	}


	public void setOwner(Owner owner) {
		this.owner = owner;
	}
}
