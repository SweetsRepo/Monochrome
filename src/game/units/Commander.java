package game.units;

import engine.board.Owner;
import game.buildings.FactoryPattern;

public class Commander extends Unit implements FactoryPattern{

	/**
	 * hp = 100, moves = 3, damage = 10, Attack range = 2, cannot mine, can build
	 */
	public Commander(){
		super(100, 3, 2, 10, 2, false, true);
		
	}

}
