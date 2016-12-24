package engine.board;

import engine.runner.Player;

/**
 * Tile which may only be mined by Dark Type Units
 * @author Christopher Sweet
 * @version 0.5
 */
public class DarkSourceTile extends Tile{
	
	public DarkSourceTile(){
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