package testing.junit;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import game.runner.GameSettings;
import game.runner.Monochrome;
import game.runner.Player;

public class PlayerThreadTesting extends TestCase {

	public Monochrome testGame;
	
	
	/**
	 * Set up parameters prior to running the test case
	 */
	@Before
	public void setUp(){
		testGame = new Monochrome();
		testGame.run();
	}
	
	/**
	 * Tests the ability to lock other players out from making moves
	 * when it is not their turn. Later tests will contain a queue
	 * for actions taken when waiting.
	 */
	@Test
	public void testLockout(){
		//Ensure that the active player make changes
		int activePlayer = this.testGame.getGameBoard().pid;
		//Loop through the players and try to make moves
		for(int i = 0; i < GameSettings.NUMPLAYERS; i++){
			Player p = this.testGame.getGameBoard().getPlayers()[i];
		}
	}
	
	/**
	 * Tests the ability of the game board to swap players turns. 
	 * Later tests will contain queued actions check
	 */
	@Test
	public void testTurnSwap(){
		
	}
	
}
