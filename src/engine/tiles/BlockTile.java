package engine.tiles;

public class BlockTile extends Tile {
	
	public BlockTile() {
		super();
		this.isOccupied = true;
	}
	
	/**
	 * Doesn't allow for the tile to ever be occupied
	 */
	@Override
	public void setOccupied(boolean occupied){
		this.isOccupied = true;
	}
	
}
