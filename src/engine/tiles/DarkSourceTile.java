package engine.tiles;

import game.runner.Player;

/**
 * Class outline for DarkTiles
 * @author Chris
 *
 */
public class DarkSourceTile extends Tile{

	//Boolean value representing if the tile in curretnly controlled by a player. Used for Mining tiles
	protected boolean isControlled;
	
	public DarkSourceTile(int r, int c){
		super(r, c);
	}
	
	/**
	 * Mines the tile with the added check for player type
	 */
	public int mine(Player p){
		if(p.getType() == "Dark")
			return super.mine();
		else
			return 0;
	}
	
	public boolean isControlled() {
		return isControlled;
	}

	public void setControlled(boolean isControlled) {
		this.isControlled = isControlled;
	}
}