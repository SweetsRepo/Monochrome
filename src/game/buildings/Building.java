package game.buildings;

/**
 * Building superclass which defines traits that all buildings shall have
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.1
 */
public abstract class Building {

	protected int hp; 
	
	public Building(int hp){
		this.hp = hp;
	}
}
