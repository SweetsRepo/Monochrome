package engine.units;

import engine.board.Owner;

public class Commander extends Unit {

	/**
	 * hp = 100, moves = 3, damage = 10, Attack range = 2, cannot mine, can build
	 */
	public Commander(){
		super(100, 3, 2, 10, 2, false, true);
		
	}

}
