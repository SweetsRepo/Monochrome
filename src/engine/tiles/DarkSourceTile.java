package engine.tiles;

import game.runner.Player;

/**
 * Class outline for DarkTiles
 * @author Chris
 *
 */
public class DarkSourceTile extends Tile{

	public DarkSourceTile(int r, int c){
		super(r, c);
	}
	
	public int mine(Player p){
		if(p.getType() == "Dark")
			return super.mine();
		else
			return 0;
	}
}