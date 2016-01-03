package engine.exceptions;

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
