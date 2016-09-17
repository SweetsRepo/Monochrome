package engine.tiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import engine.exceptions.MisconfiguredMapException;
import engine.units.Commander;

public class Board {

	protected static Board instance; 
	
	//Tiles representing the board
	protected ArrayList<ArrayList<Tile>> Tiles;
	
	//List of the moves available from a given tile. Defined recursively each time a new tile is selected.
	private ArrayList<Tile> tilesAvailable;

	
	/**
	 * Singleton Implementation of the board
	 */
	private Board(){
		this.Tiles = new ArrayList<ArrayList<Tile>>();
		this.tilesAvailable = new ArrayList<Tile>();
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all the attacks from a given tile
	 * @param r - row index
	 * @param c - column index
	 * @return - List of all the tiles to attack
	 */
	public ArrayList<Tile> findAvailableAttacks(int r, int c){
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
	public ArrayList<Tile> availableAttacksDFS(int r, int c, int depth){
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
			return this.tilesAvailable;
		}
		
		return this.tilesAvailable;
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all moves from the given tile
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available tiles to move to
	 */
	public ArrayList<Tile> findAvailableMoves(int r, int c){
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
	public ArrayList<Tile> availableMovesDFS(int r, int c, int depth){
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
				return this.tilesAvailable;
			}
		}
		return this.tilesAvailable;
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
	
	public static void main(String[] args){
		
		//Parse the input file to get a Board
		TileParser parser = new TileParser( "./data/sample.txt");
		Board board = null;
		try {
			board = parser.getBoard();
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
		
		System.out.println(board.Tiles.get(4).get(2).isOccupied);
		//Test Recursive DFS Depth finder
		System.out.println(board.findAvailableMoves(4, 2).size());
		for(Tile t: board.tilesAvailable){
			for(int i = 0; i < board.Tiles.size(); i++){
				if(board.Tiles.get(i).contains(t)){
					System.out.println(i + "," + board.Tiles.get(i).indexOf(t));
				}
			}
		}
	}
}
