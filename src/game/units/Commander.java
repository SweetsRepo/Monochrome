package game.units;

import engine.tiles.Controller;

public class Commander extends Unit {

	/**
	 * hp = 100, moves = 1, damage = 10, range = 1, cannot mine, can build
	 */
	public Commander(){
		super(100, 1, 10, 1, false, true, Controller.Neutral);
		
	}

}
