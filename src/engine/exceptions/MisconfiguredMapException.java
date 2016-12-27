package engine.exceptions;

/**
 * MisconfiguredMapException: Thrown when the map file created by the user does not meet the
 * requirements of the engine.
 * @author Christopher Sweet - crs4262@rit.edu
 * @version 0.1
 */
public class MisconfiguredMapException extends Exception{

	/**
	 * MisconfiguredMapException constructor. Passes the specified message to the
	 * super class.
	 * 
	 * @param message
	 *            a message describing this Map File Error
	 */
	public MisconfiguredMapException(String message) {
		super(message);
	}

}
