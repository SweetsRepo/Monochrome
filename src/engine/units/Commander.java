package engine.units;

import engine.tiles.Controller;

public class Commander extends Unit {

	/**
	 * hp = 100, moves = 2, damage = 10, range = 1, cannot mine, can build
	 */
	public Commander(){
		super(100, 2, 10, 1, false, true, Controller.Neutral);
		
	}

}
