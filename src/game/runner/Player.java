package game.runner;

import engine.tiles.Tile;
import game.units.Unit;

import java.util.ArrayList;

/**
 * Class outline for player objects
 * @author Chris
 *
 */
public class Player {
	//Arraylist for all the units under this player's control. Arraylist for all the tiles under the player's control
	private ArrayList<Unit> units;
	private ArrayList<Tile> tiles;
	//String for Player Type
	private String type;
	//Integers for resources
	private int resources;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
