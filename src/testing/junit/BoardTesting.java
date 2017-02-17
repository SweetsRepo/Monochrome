package testing.junit;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import engine.board.Board;
import engine.board.BoardParser;
import engine.board.Owner;
import engine.board.Tile;
import engine.exceptions.MisconfiguredMapException;
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
			this.board = parser.parseBoard();
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

	/**
	 * Tests the recursive build identification algorithm to find all possible tiles to build on
	 */
	@Test
	public void testBuildDFS(){
		assertEquals(20, this.board.findAvailableBuilds(4, 2).size());
	}
	
	
	/**
	 * Tests the tile taking functions as follows.
	 * 1. Take a Neutral Tile
	 * 2. Make sure no subsequent moves by that player affect it
	 * 3. Swap Player and return tile to neutral.
	 * 4. Take the Neutral Tile
	 */
	@Test
	public void testTakeTile(){
		//Manually set occupied to allow for methods to run
		this.board.getTiles().get(0).get(0).setOccupied(true);
		assertEquals(Owner.Neutral, this.board.getTiles().get(0).get(0).getOwner());
		this.board.takeTile(0, 0);
		assertEquals(Owner.Light, this.board.getTiles().get(0).get(0).getOwner());
		this.board.takeTile(0, 0);
		assertEquals(Owner.Light, this.board.getTiles().get(0).get(0).getOwner());
		//Manually swap turn
		this.board.pid = 4;
		this.board.takeTile(0, 0);
		assertEquals(Owner.Neutral, this.board.getTiles().get(0).get(0).getOwner());
		this.board.takeTile(0, 0);
		assertEquals(Owner.Dark, this.board.getTiles().get(0).get(0).getOwner());
		this.board.takeTile(0, 0);
	}
}
