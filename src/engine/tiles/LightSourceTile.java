package engine.tiles;

import game.runner.Player;

/**
 * Class outline for LightTiles
 * @author Chris
 *
 */
public class LightSourceTile extends Tile{

	public LightSourceTile(int r, int c){
		super(r, c);
	}
	
	public int mine(Player p){
		if(p.getType() == "Light")
			return super.mine();
		else
			return 0;
	}
}
