package engine.tiles;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	protected static Board instance; 
	
	//Tiles representing the board
	protected ArrayList<ArrayList<Tile>> Tiles;
	
	//List of the moves available from a given tile. Defined recursively each time a new tile is selected.
	private ArrayList<Tile> movesAvailable;

	
	/**
	 * Singleton Implementation of the board
	 */
	private Board(){
		this.Tiles = new ArrayList<ArrayList<Tile>>();
		this.movesAvailable = new ArrayList<Tile>();
	}
	
	
	/**
	 * Clear previous available moves result and then call Recursive DFS to find all moves from the given tile
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available tiles to move to
	 */
	public ArrayList<Tile> findAvailableMoves(int r, int c){
		this.movesAvailable.clear();
		if(this.Tiles.get(r).get(c).isOccupied){
			return availableMovesDFS(r, c, this.Tiles.get(r).get(c).getUnit().getRange());
		}
		else{
			return availableMovesDFS(r, c, 0);
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
			if(!this.movesAvailable.contains(this.Tiles.get(r).get(c)))
				this.movesAvailable.add(this.Tiles.get(r).get(c));
			//End of recursive depth
		}
		//Make sure that we don't have any duplicate tiles
		else if(this.movesAvailable.contains(this.Tiles.get(r).get(c))){
			//End of path due to obstacle
		}
		//Add the tile to available moves and recurse to the next set
		else{
			this.movesAvailable.add(this.Tiles.get(r).get(c));
			if(r - 1 >= 0)
				availableMovesDFS(r - 1, c, depth - 1);
			if(c - 1 >= 0)
				availableMovesDFS(r, c - 1, depth - 1);
			if(r + 1 <= this.Tiles.size() - 1)
				availableMovesDFS(r + 1, c, depth - 1);
			if(c + 1 <= this.Tiles.size() - 1)
				availableMovesDFS(r, c + 1, depth - 1);
			return this.movesAvailable;
		}
		//Make the Java Compiler happy, this is unreachable
		return this.movesAvailable;
		
		
	}

	public Tile availableTileHelper(int r, int c){
		if(!Tiles.get(r).get(c).isOccupied)
			return Tiles.get(r).get(c);
		return null;
	}
	
	/**
	 * Accesses the Tile at the given Row and Column Coordinate
	 * @param r - Row index
	 * @param c - Column index
	 * @return - Tile in the given position
	 */
	public Tile accessTile(int r, int c){
		try{
			return Tiles.get(r).get(c);
		}
		catch (IndexOutOfBoundsException e){
			System.err.println("That shouldn't have been accessed");
			return null;
		}
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
		
		//Test Singleton implementation
		Board test = Board.getInstance();
		int hashcode = test.hashCode();
		Board test2 = Board.getInstance();
		int hashcode2 = test2.hashCode();
		System.out.println(hashcode == hashcode2);
		
		//Set the Board Tiles Manually
		ArrayList<Tile> row0 = new ArrayList<Tile>();
		ArrayList<Tile> row1 = new ArrayList<Tile>();
		ArrayList<Tile> row2 = new ArrayList<Tile>();
		ArrayList<Tile> row3 = new ArrayList<Tile>();
		row0.addAll(Arrays.asList(new OpenSourceTile(), new OpenSourceTile(), new OpenSourceTile()));
		row1.addAll(Arrays.asList(new OpenSourceTile(), new OpenSourceTile(), new OpenSourceTile()));
		row2.addAll(Arrays.asList(new OpenSourceTile(), new OpenSourceTile(), new OpenSourceTile()));
		row3.addAll(Arrays.asList(new OpenSourceTile(), new OpenSourceTile(), new OpenSourceTile()));
		test2.Tiles.addAll(Arrays.asList(row0, row1, row2, row3));
		
		//Test Recursive DFS Depth finder
		System.out.println(test2.findAvailableMoves(2, 2).size());
		
		
	}
}
