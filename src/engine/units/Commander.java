package engine.units;

import engine.tiles.Owner;

public class Commander extends Unit {

	/**
	 * hp = 100, moves = 3, damage = 10, Attack range = 2, cannot mine, can build
	 */
	public Commander(){
		super(100, 3, 10, 2, false, true);
		
	}

}
