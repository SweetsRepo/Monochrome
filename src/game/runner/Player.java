package game.runner;

import game.units.Unit;

import java.util.ArrayList;

/**
 * Class outline for player objects
 * @author Chris
 *
 */
public class Player {
	//Arraylist for all the units under this player's control.
	private ArrayList<Unit> units;
	//String for Player Type
	private String type;
	
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
