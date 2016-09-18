package engine.tiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import engine.exceptions.MisconfiguredMapException;
import engine.units.Commander;

/**
 * Implements the singular board which will contain most data members for the game. Additionally algorithms
 * for accessing the information found in the given Board instance. Singleton Pattern
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 0.9
 */
public class Board {

	protected static Board instance; 
	
	//Tiles representing the board
	protected ArrayList<ArrayList<Tile>> Tiles;
	
	//List of the tile available for a chosen action
	private ArrayList<Tile> tilesAvailable;

	//Dictionary representing Tiles controlled by each player - References will only be kept for Light and Dark, not Neutral
	private Hashtable<Owner, ArrayList<Tile>> tilesByPlayer;
	
	/**
	 * Singleton Implementation of the board
	 */
	private Board(){
		this.Tiles = new ArrayList<ArrayList<Tile>>();
		this.tilesAvailable = new ArrayList<Tile>();
		this.tilesByPlayer = new Hashtable<Owner, ArrayList<Tile>>();
	}
	
	/**
	 * Singleton Pattern for board
	 * @return Board Object
	 */
	public static Board getInstance(){
		if( instance == null){
			instance = new Board();
			return instance;
		}
		else
			return instance;
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all the attacks from a given tile
	 * @param r - row index
	 * @param c - column index
	 * @return - List of all the tiles to attack
	 */
	public synchronized ArrayList<Tile> findAvailableAttacks(int r, int c){
		ArrayList<Tile> attackTiles;
		this.tilesAvailable.clear();
		if(this.Tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as we want moving to the same tile to be a valid movement
			this.Tiles.get(r).get(c).setOccupied(false);
			attackTiles = availableAttacksDFS(r, c, this.Tiles.get(r).get(c).getUnit().getAttRange());
			this.Tiles.get(r).get(c).setOccupied(true);
			return attackTiles;
		}
		else{
			return new ArrayList<Tile>();
		}	
	}
	
	/**
	 * Performs a recursive depth first search to find what tiles are occupied within attacking range
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the tiles to attack
	 */
	private ArrayList<Tile> availableAttacksDFS(int r, int c, int depth){
		//If there's a unit on the tile and it hasn't been found already, add it to the list
		if(this.Tiles.get(r).get(c).isOccupied && !this.tilesAvailable.contains(this.Tiles.get(r).get(c)))
			this.tilesAvailable.add(this.Tiles.get(r).get(c));
		//Make sure the recursive depth hasn't been reached
		if(depth == 0){
			//End recursive depth
		}
		else{
			if(r - 1 >= 0)
				availableAttacksDFS(r - 1, c, depth - 1);
			if(c - 1 >= 0)
				availableAttacksDFS(r, c - 1, depth - 1);
			if(r + 1 <= this.Tiles.size() - 1)
				availableAttacksDFS(r + 1, c, depth - 1);
			if(c + 1 <= this.Tiles.get(r).size() - 1)
				availableAttacksDFS(r, c + 1, depth - 1);
			//All paths exhausted return the result
			return this.tilesAvailable;
		}
		//Terminate end of path results
		return this.tilesAvailable;
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all moves from the given tile
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available tiles to move to
	 */
	public synchronized ArrayList<Tile> findAvailableMoves(int r, int c){
		ArrayList<Tile> moveTiles;
		this.tilesAvailable.clear();
		if(this.Tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as we want moving to the same tile to be a valid movement
			this.Tiles.get(r).get(c).setOccupied(false);
			moveTiles = availableMovesDFS(r, c, this.Tiles.get(r).get(c).getUnit().getRange());
			return moveTiles;
		}
		else{
			return new ArrayList<Tile>();
		}	
	}
	
	/**
	 * Performs a recursive Depth First Search to determine what tiles are non-occupied
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available tiles to move to
	 */
	private ArrayList<Tile> availableMovesDFS(int r, int c, int depth){
		//Make sure that the recursive depth hasn't been reached
		if(depth == 0){
			if(!this.tilesAvailable.contains(this.Tiles.get(r).get(c)) && !(this.Tiles.get(r).get(c).isOccupied))
				this.tilesAvailable.add(this.Tiles.get(r).get(c));
			//End of recursive depth
		}
		//Add the tile to available moves and recurse to the next set
		else{
			//Make sure to truncate any paths which are blocked by occupied tiles
			if(!this.Tiles.get(r).get(c).isOccupied){
				//Don't allow for any duplicate tiles
				if(!this.tilesAvailable.contains(this.Tiles.get(r).get(c)))
					this.tilesAvailable.add(this.Tiles.get(r).get(c));
				if(r - 1 >= 0)
					availableMovesDFS(r - 1, c, depth - 1);
				if(c - 1 >= 0)
					availableMovesDFS(r, c - 1, depth - 1);
				if(r + 1 <= this.Tiles.size() - 1)
					availableMovesDFS(r + 1, c, depth - 1);
				if(c + 1 <= this.Tiles.get(r).size() - 1)
					availableMovesDFS(r, c + 1, depth - 1);
				//All paths exhausted, return the result
				return this.tilesAvailable;
			}
		}
		//Terminate end of depth runs.
		return null;
	}
	
	//INCOMPLETE
	/**
	 * Takes the tile and places it under control of the given unit. 
	 * @param r - row index
	 * @param c - column index
	 */
	public void takeTile(int r, int c){
		//insert control logic to count turns here
		if(this.Tiles.get(r).get(c).isOccupied){
			//If neutral tile add to the player whos unit is currently on it
			if(this.Tiles.get(r).get(c).owner == Owner.Neutral){
				this.tilesByPlayer.get(this.Tiles.get(r).get(c).owner).add(this.Tiles.get(r).get(c));
			}
			else{
				//Remove the tile from the other players control, and add it to the calling player
				this.tilesByPlayer.get(Owner.getOpposite(this.Tiles.get(r).get(c).owner)).remove(this.Tiles.get(r).get(c));
				this.tilesByPlayer.get(this.Tiles.get(r).get(c).owner).add(this.Tiles.get(r).get(c));
			}
		}
		//Do nothing case
		else{
			return;
		}
	}
	
	public static void main(String[] args){
		
		//Parse the input file to get a Board
		BoardParser parser = new BoardParser( "./data/sample.txt");
		Board board = null;
		try {
			board = parser.getBoard();
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
		
		//Test Recursive DFS Move finder
		System.out.println(board.findAvailableMoves(4, 2).size());
		for(Tile t: board.tilesAvailable){
			for(int i = 0; i < board.Tiles.size(); i++){
				if(board.Tiles.get(i).contains(t)){
					System.out.println(i + "," + board.Tiles.get(i).indexOf(t));
				}
			}
		}
		System.out.println("");
		//Pretend that the unit in question did not move between turns. 
		//This is why we should really have a unit tester.
		board.Tiles.get(4).get(2).setOccupied(true);
		
		//Test Recursive DFS Attack finder
		System.out.println(board.findAvailableAttacks(4, 2).size());
		for(Tile t: board.tilesAvailable){
			for(int i = 0; i < board.Tiles.size(); i++){
				if(board.Tiles.get(i).contains(t)){
					System.out.println(i + "," + board.Tiles.get(i).indexOf(t));
				}
			}
		}
	}
}
