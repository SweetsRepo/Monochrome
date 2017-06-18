package testing.junit;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import engine.board.Board;
import engine.board.BoardParser;
import engine.board.Coordinate;
import engine.board.Owner;
import engine.board.Tile;
import engine.exceptions.MisconfiguredMapException;
import game.units.Heavy;
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
	
	/**
	 * Tests the act of moving a unit from one tile to another. 
	 * Check that connections are cleaned up at source and target tile
	 */
	@Test
	public void testMoveUnit(){
		this.board.moveUnit(4, 2, 4, 0);
		assertFalse(this.board.getTiles().get(4).get(2).isOccupied());
		assertNull(this.board.getTiles().get(4).get(2).getUnit());
		assertTrue(this.board.getTiles().get(4).get(0).isOccupied());
		assertNotNull(this.board.getTiles().get(4).get(0).getUnit());
	}
	
	/**
	 * Tests the attack function. 
	 * Ensure that if attack is underpowered, unit persists
	 * Ensure that if attack is overpowered, unit is removed and attacker takes place
	 */
	@Test
	public void testAttackUnit(){
		for(int i = 0; i < 9; i++){
			this.board.attackUnit(4, 2, 3, 3);
			assertNotNull(this.board.getTiles().get(3).get(3).getUnit());
		}
		this.board.attackUnit(4, 2, 3, 3);
		assertNull(this.board.getTiles().get(3).get(3).getUnit());
		
	}
	
	/**
	 * Tests the ability to build buildings on a tile
	 * 
	 * TODO: Can only build on the tile that your unit is on. Fix this so it uses builds list
	 */
	@Test
	public void testBuildOnTile(){
		this.board.buildOnTile(4, 2, 6, 3, "Barracks");
		assertTrue(this.board.getTiles().get(6).get(3).isOccupied());
	}
	
	/**
	 * Tests the ability to mine on a tile.
	 * Ensure if there is none left mine doesn't return too much
	 */
	@Test
	public void testMineOnTile(){
		assertEquals(this.board.mineTile(4,2), 100);
		assertEquals(this.board.mineTile(4,2), 1);
		assertEquals(this.board.mineTile(4,2), 0);

	}
	
	/**
	 * Tests the ability to create a unit on tile and then move them out.
	 * Further tests attempts to overwrite the unit on tile. This will be checked on the frontend
	 */
	@Test
	public void testCreateOnTile(){
		this.board.buildOnTile(4, 2, 6, 3, "Barracks");
		assertTrue(this.board.getTiles().get(6).get(3).isOccupied());
		this.board.createOnTile(6, 3, "Scout");
		assertNotNull(this.board.getTiles().get(6).get(3).getUnit());
		this.board.moveUnit(6, 3, 6, 2);
		assertNull(this.board.getTiles().get(6).get(3).getUnit());
		this.board.createOnTile(6, 3, "Scout");
		this.board.createOnTile(6, 3, "Heavy");
		assertTrue(this.board.getTiles().get(6).get(3).getUnit() instanceof Heavy);
	}
	
}
