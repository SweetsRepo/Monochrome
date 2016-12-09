package engine.tiles;

import engine.runner.Player;

/**
 * Tile which may only be mined by Light type units
 * @author Christopehr Sweet - crs4263@rit.edu
 * @version 0.5
 */
public class LightSourceTile extends Tile{
		
	public LightSourceTile(){
		super();
	}
	
	/**
	 * Mines the tile with the added check for player type
	 */
	public int mine(){
		if(this.unit != null)
			return super.mine();
		else
			return 0;
	}

}
