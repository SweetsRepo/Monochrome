package engine.tiles;

import java.util.ArrayList;

public class Board {

	protected static Board instance; 
	
	protected ArrayList<ArrayList<Tile>> Tiles;

	
	/**
	 * Singleton Implementation of the board
	 */
	private Board(){
		this.Tiles = new ArrayList<ArrayList<Tile>>();
	}
	
	//INCOMPLETE
	/**
	 * Checks the unit at the tile selected, get's it's movement range, and determines a possible movement set through DFS.
	 * @param r - row index
	 * @param c - column index
	 * @return - List of the available tiles to move to
	 */
	public ArrayList<Tile> availableMoves(int r, int c){
		ArrayList<Tile> moves = new ArrayList<Tile>();
		ArrayList<Tile> visited = new ArrayList<Tile>();
		
		return moves;
	}
	
	//INCOMPLETE - May or may not work, probably not in this state
	/**
	 * Performs a recursive Depth First Search to determine what tiles are non-occupied
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @param moves - List of moves which are available to the player
	 * @param visited - List of tiles which have been visited already. Required to stop DFS
	 * @return
	 */
	public ArrayList<Tile> availableMovesBFS(int r, int c, int depth, ArrayList<Tile> moves, ArrayList<Tile> visited){
		if(depth == 0 || visited.contains(this.Tiles.get(r).get(c))){
			//Do nothing case
		}
		else{
			//Add to the checked list
			visited.add(Tiles.get(r).get(c));
			if(!Tiles.get(r).get(c).isOccupied){
				moves.add(Tiles.get(r).get(c));
				//Check boundary cases before recursing
				if(r - 1 > 0)
					availableMovesBFS(r - 1, c, depth - 1, moves, visited);
				if(c - 1 > 0)
					availableMovesBFS(r, c - 1, depth - 1, moves, visited);
				if(r + 1 < this.Tiles.size() - 1)
					availableMovesBFS(r + 1, c, depth - 1, moves, visited);
				if(c + 1 < this.Tiles.size() - 1)
					availableMovesBFS(r, c + 1, depth - 1, moves, visited);
			}
		}
		return moves;
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
}
