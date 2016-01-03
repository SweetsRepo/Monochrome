package engine.tiles;

/**
 * Class outline for Tiles which may be mined by either player
 * @author Chris
 *
 */
public class OpenSourceTile extends Tile {

	//Boolean value representing if the tile in curretnly controlled by a player. Used for Mining tiles
	protected boolean isControlled;
		
	public OpenSourceTile(int r, int c) {
		super(r, c);
		// TODO Auto-generated constructor stub
	}

	public boolean isControlled() {
		return isControlled;
	}

	public void setControlled(boolean isControlled) {
		this.isControlled = isControlled;
	}
}
