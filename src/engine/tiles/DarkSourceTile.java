package engine.tiles;

import game.runner.Player;

/**
 * Tile which may only be mined by Dark Type Units
 * @author Chris
 *
 */
public class DarkSourceTile extends Tile{
	
	public DarkSourceTile(){
		super();
	}
	
	/**
	 * Mines the tile with the added check for player type
	 */
	public int mine(){
		if(this.unit != null && this.unit.getOwner() == Controller.Dark)
			return super.mine();
		else
			return 0;
	}
	
}