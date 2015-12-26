package engine;

/**
 * Parent class for all Tile objects. Specifies an integer for resources,
 * Player who has control(unimplemented), and default method implementation.
 * @author Chris
 * @version 0.1
 */
public abstract class Tile {
	
	//Integer value of the resources available on this tile.
	private int resources;
	
	public Tile(int resources){
		this.resources = resources;
	}
	
	public int getResourcesRemaining(){
		return this.resources;
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
}
