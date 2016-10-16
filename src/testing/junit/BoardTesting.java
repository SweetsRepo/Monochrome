package testing.junit;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import engine.exceptions.MisconfiguredMapException;
import engine.tiles.Board;
import engine.tiles.BoardParser;
import engine.tiles.Tile;
import junit.framework.TestCase;

public class BoardTesting extends TestCase {

	/**Board to use for testing algorithms*/
	private Board board;
	
	/**
	 * Prepares the Board for Testing
	 */
	@Before
	public void setUp(){
		BoardParser parser = new BoardParser( "./data/sample.txt");
		try {
			this.board = parser.getBoard();
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Tests the recursive path finding algorithm used for finding all possible moves
	 */
	@Test
	public void testMoveDFS(){
		assertEquals(18, this.board.findAvailableMoves(4, 2).size());
	}
	
	/**
	 * Tests the recursive attack identification algorithm to find all possible attacks
	 */
	@Test
	public void testAttackDFS(){
		assertEquals(4, this.board.findAvailableAttacks(4, 2).size());
	}
	
}
