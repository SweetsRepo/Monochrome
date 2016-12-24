package engine.board;

/**
 * Class outline for Tiles which may be mined by either player
 * @author Chris
 *
 */
public class OpenSourceTile extends Tile {
		
	public OpenSourceTile() {
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
