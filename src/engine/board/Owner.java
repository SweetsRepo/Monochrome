package engine.board;

/**
 * Controller Enumeration to indicate who controls tiles, units, etc.
 * @author Christopher Sweet - crs4263@rit.edu
 * Light, Dark, and Neutral are applicable types. 
 */
public enum Owner { Light, Dark, Neutral;

	public static Owner getOpposite(Owner own){
		if(own == Light)
			return Dark;
		else if (own == Dark)
			return Light;
		else
			return Neutral;
	}
}