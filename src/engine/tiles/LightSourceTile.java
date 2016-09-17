package engine.tiles;

import engine.runner.Player;

/**
 * Tile which may only be mined by Light type units
 * @author Chris
 *
 */
public class LightSourceTile extends Tile{
		
	public LightSourceTile(){
		super();
	}
	
	/**
	 * Mines the tile with the added check for player type
	 */
	public int mine(){
		if(this.unit != null && this.unit.getOwner() == Controller.Light)
			return super.mine();
		else
			return 0;
	}

}
