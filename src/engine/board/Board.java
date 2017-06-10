package engine.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TreeSet;

import engine.exceptions.MisconfiguredMapException;
import game.buildings.Barracks;
import game.buildings.Building;
import game.runner.Player;
import game.units.Commander;
import game.units.Unit;
import game.units.Worker;

/**
 * Implements the singular board which will contain most data members for the game. Additionally algorithms
 * for accessing the information found in the given Board instance. Singleton Pattern
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 1.0.1
 */
public class Board {

	protected static Board instance; 
	
	//Tiles representing the board
	protected ArrayList<ArrayList<Tile>> tiles;
	
	//List of the tile available for a chosen action
	private HashSet<Coordinate> tilesAvailable;
	

	//Dictionary representing Tiles controlled by each player - References will only be kept for Light and Dark, not Neutral
	private Hashtable<Owner, ArrayList<Tile>> tilesByPlayer;
	
	//Player ID representing the player who currently has access to the board
	public int pid; 
	
	//List of the players
	private ArrayList<Player> players;
	
	/**
	 * Singleton Implementation of the board
	 */
	private Board(){
		this.tiles = new ArrayList<ArrayList<Tile>>();
		this.tilesAvailable = new HashSet<Coordinate>();
		this.tilesByPlayer = new Hashtable<Owner, ArrayList<Tile>>();
		this.tilesByPlayer.put(Owner.Light, new ArrayList<Tile>());
		this.tilesByPlayer.put(Owner.Dark, new ArrayList<Tile>());
		this.players = new ArrayList<Player>();
		this.pid = 0;
	}
	
	/**
	 * Singleton Pattern for board
	 * @return Board Object
	 */
	public static Board getInstance(){
		return instance;
	}
	
	/**
	 * Resets the board to initial state. Allows for reparsing
	 * of a board file (I.E. Changing from one match to another)
	 * @return Board Object
	 */
	public static Board getNewInstance(){
		instance = new Board();
		return instance;
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all the attacks from a given tile
	 * @param r - row index
	 * @param c - column index
	 * @return - List of all the tiles to attack
	 */
	public synchronized ArrayList<Coordinate> findAvailableAttacks(int r, int c){
		ArrayList<Coordinate> attackTiles;
		this.tilesAvailable.clear();
		if(this.tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as we want moving to the same tile to be a valid movement
			this.tiles.get(r).get(c).setOccupied(false);
			attackTiles = availableAttacksDFS(r, c, this.tiles.get(r).get(c).getUnit().getAttRange());
			this.tiles.get(r).get(c).setOccupied(true);
			return attackTiles;
		}
		else{
			return new ArrayList<Coordinate>();
		}	
	}
	
	/**
	 * Performs a recursive depth first search to find what tiles are occupied within attacking range
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the tiles to attack
	 */
	private ArrayList<Coordinate> availableAttacksDFS(int r, int c, int depth){
		//If there's a unit on the tile and it hasn't been found already, add it to the list
		Coordinate coor = new Coordinate(r, c);
		if(this.tiles.get(r).get(c).isOccupied)
			this.tilesAvailable.add(coor);
		//Make sure the recursive depth hasn't been reached
		if(depth == 0){
			//End recursive depth
		}
		else{
			if(r - 1 >= 0)
				availableAttacksDFS(r - 1, c, depth - 1);
			if(c - 1 >= 0)
				availableAttacksDFS(r, c - 1, depth - 1);
			if(r + 1 <= this.tiles.size() - 1)
				availableAttacksDFS(r + 1, c, depth - 1);
			if(c + 1 <= this.tiles.get(r).size() - 1)
				availableAttacksDFS(r, c + 1, depth - 1);
			if(r - 1 >= 0 && c - 1 >= 0)
				availableAttacksDFS(r - 1, c - 1, depth - 1);
			if(r + 1 <= this.tiles.size() && c - 1 >= 0)
				availableAttacksDFS(r + 1, c - 1, depth - 1);
			if(r - 1 >= 0 && c + 1 <= this.tiles.get(r).size())
				availableAttacksDFS(r - 1, c + 1, depth - 1);
			if(r + 1 <= this.tiles.size() && c + 1 <= this.tiles.get(r).size())
				availableAttacksDFS(r + 1, c + 1, depth - 1);
			//All paths exhausted return the result
			return new ArrayList<Coordinate>(this.tilesAvailable);
		}
		//Terminate end of path results
		return new ArrayList<Coordinate>();
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all moves from the given tile
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available tiles to move to
	 */
	public synchronized ArrayList<Coordinate> findAvailableMoves(int r, int c){
		ArrayList<Coordinate> moveTiles;
		this.tilesAvailable.clear();
		if(this.tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as we want moving to the same tile to be a valid movement
			this.tiles.get(r).get(c).setOccupied(false);
			moveTiles = availableMovesDFS(r, c, this.tiles.get(r).get(c).getUnit().getRange());
			this.tiles.get(r).get(c).setOccupied(true);
			return moveTiles;
		}
		else{
			return new ArrayList<Coordinate>();
		}	
	}
	
	/**
	 * Performs a recursive Depth First Search to determine what tiles are non-occupied
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available tiles to move to
	 */
	private ArrayList<Coordinate> availableMovesDFS(int r, int c, int depth){
		Coordinate coor = new Coordinate(r, c);
		//Make sure that the recursive depth hasn't been reached
		if(depth == 0){
			if(!(this.tiles.get(r).get(c).isOccupied))
				this.tilesAvailable.add(coor);
			//End of recursive depth
		}
		//Add the tile to available moves and recurse to the next set
		else{
			//Make sure to truncate any paths which are blocked by occupied tiles
			if(!this.tiles.get(r).get(c).isOccupied){
				this.tilesAvailable.add(coor);
				if(r - 1 >= 0)
					availableMovesDFS(r - 1, c, depth - 1);
				if(c - 1 >= 0)
					availableMovesDFS(r, c - 1, depth - 1);
				if(r + 1 <= this.tiles.size() - 1)
					availableMovesDFS(r + 1, c, depth - 1);
				if(c + 1 <= this.tiles.get(r).size() - 1)
					availableMovesDFS(r, c + 1, depth - 1);
				//All paths exhausted, return the result
				return new ArrayList<Coordinate>(this.tilesAvailable);
			}
		}
		//Terminate end of depth runs.
		return new ArrayList<Coordinate>();
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all build options from a given tile
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available build options
	 */
	public synchronized ArrayList<Coordinate> findAvailableBuilds(int r, int c){
		ArrayList<Coordinate> buildTiles;
		this.tilesAvailable.clear();
		if(this.tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as building on the same tile is allowable
			this.tiles.get(r).get(c).setOccupied(false);
			buildTiles = availableBuildDFS(r, c, this.tiles.get(r).get(c).getUnit().getBuildRange());
			this.tiles.get(r).get(c).setOccupied(true);
			return buildTiles;
		}
		else{
			return new ArrayList<Coordinate>();
		}
	}
	
	/**
	 * Performs a recursive Depth First Search to determine what tiles are non-occupied
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available build options
	 */
	private ArrayList<Coordinate> availableBuildDFS(int r, int c, int depth){
		Coordinate coor = new Coordinate(r, c);
		//Make sure that the recursive depth hasn't been reached
		if(depth == 0){
			if(!(this.tiles.get(r).get(c).isOccupied))
				this.tilesAvailable.add(coor);
			//End of recursive depth
		}
		//Add the tile to available moves and recurse to the next set
		else{
			//Make sure to truncate any paths which are blocked by occupied tiles
			if(!this.tiles.get(r).get(c).isOccupied){
				this.tilesAvailable.add(coor);
				if(r - 1 >= 0)
					availableBuildDFS(r - 1, c, depth - 1);
				if(c - 1 >= 0)
					availableBuildDFS(r, c - 1, depth - 1);
				if(r + 1 <= this.tiles.size() - 1)
					availableBuildDFS(r + 1, c, depth - 1);
				if(c + 1 <= this.tiles.get(r).size() - 1)
					availableBuildDFS(r, c + 1, depth - 1);
				if(r - 1 >= 0 && c - 1 >= 0)
					availableBuildDFS(r - 1, c - 1, depth - 1);
				if(r + 1 <= this.tiles.size() && c - 1 >= 0)
					availableBuildDFS(r + 1, c - 1, depth - 1);
				if(r - 1 >= 0 && c + 1 <= this.tiles.get(r).size())
					availableBuildDFS(r - 1, c + 1, depth - 1);
				if(r + 1 <= this.tiles.size() && c + 1 <= this.tiles.get(r).size())
					availableBuildDFS(r + 1, c + 1, depth - 1);
				//All paths exhausted, return the result
				return new ArrayList<Coordinate>(this.tilesAvailable);
			}
		}
		//Terminate end of depth runs.
		return new ArrayList<Coordinate>();
	}
	
	/**
	 * Takes the tile and places it under control of the given unit. 
	 * @param r - row index
	 * @param c - column index
	 */
	public synchronized void takeTile(int r, int c){
		//Don't operate if there are no units on the tile, or the tile is already controlled by the given player
		if(this.tiles.get(r).get(c).isOccupied || this.tiles.get(r).get(c).getOwner() == this.getPlayers()[this.pid].getFaction()){
			//If neutral tile add to the player who's unit is currently on it
			if(this.tiles.get(r).get(c).owner == Owner.Neutral){
				this.tilesByPlayer.get(this.getPlayers()[this.pid].getFaction()).add(this.tiles.get(r).get(c));
				this.tiles.get(r).get(c).owner = this.getPlayers()[this.pid].getFaction();
			}
			//If enemy tile remove it from the other players control
			else if(this.tiles.get(r).get(c).owner != this.getPlayers()[this.pid].getFaction()){
				//Remove the tile from the other players control
				this.tilesByPlayer.get(Owner.getOpposite(this.getPlayers()[this.pid].getFaction())).remove(this.tiles.get(r).get(c));
				this.tiles.get(r).get(c).owner = Owner.Neutral;
			}
		}
		//Do nothing case
		else{
			return;
		}
	}
	
	/**
	 * Moves the unit found at tile[sr, sc] and moves it to tile[er, ec]
	 * @param sr - start row
	 * @param sc - start column
	 * @param er - end row
	 * @param ec - end column
	 */
	public synchronized void moveUnit(int sr, int sc, int er, int ec){
		Unit unitRef = this.tiles.get(sr).get(sc).getUnit();
		this.tiles.get(sr).get(sc).removeUnit();
		this.tiles.get(er).get(ec).setUnit(unitRef);
	}
	
	/**
	 * Attacks the unit at tile[tr, tc] with unit at tile[sr, sc]
	 * If the unit is killed the attacker moves up to the new tile
	 * @param sr - source row
	 * @param sc - source column 
	 * @param tr - target row
	 * @param tc - target column
	 */
	public synchronized void attackUnit(int sr, int sc, int tr, int tc){
		Unit underAttack = this.tiles.get(tr).get(tc).getUnit();
		Unit dealingAttack = this.tiles.get(sr).get(sc).getUnit();
		//DamageDealt method returns a boolean indicating if the unit under attack is still alive post attack
		if(underAttack.damageDealt(dealingAttack.getAttDamage())){
			this.tiles.get(tr).get(tc).removeUnit();
			//NOTE: Create a reference from the unit to it's controlling player
			//Remove the unit from the player's unit list
			
		}
	}
	
	/**
	 * Builds the building specified by the string on tile (r,c)
	 * @param r - row index
	 * @param c - column index
	 * @param unit - String representation of the building
	 */
	public synchronized void buildOnTile(int r, int c, String building){
		Building build = ((Worker)(this.tiles.get(r).get(c).getUnit())).constructBuilding(this.getPlayers()[this.pid].getResources(), building);
		if(build != null)
			this.tiles.get(r).get(c).setBuilding(build);
	}
	
	/**
	 * Builds the unit specified by the string on tile (r,c)
	 * @param r - row index
	 * @param c - column index
	 * @param unit - String representation of the unit
	 */
	public synchronized void createOnTile(int r, int c, String unit){
		Unit create = ((Barracks)(this.tiles.get(r).get(c).getBuilding())).constructUnit(this.getPlayers()[this.pid].getResources(), unit);
		if(create != null)
			this.tiles.get(r).get(c).setUnit(create);
	}
	
	public HashSet<Coordinate> getTilesAvailable() {
		return tilesAvailable;
	}

	public void setTilesAvailable(HashSet<Coordinate> tilesAvailable) {
		this.tilesAvailable = tilesAvailable;
	}

	public Hashtable<Owner, ArrayList<Tile>> getTilesByPlayer() {
		return tilesByPlayer;
	}

	public void setTilesByPlayer(Hashtable<Owner, ArrayList<Tile>> tilesByPlayer) {
		this.tilesByPlayer = tilesByPlayer;
	}

	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
		this.tiles = tiles;
	}
	
	public void addPlayer(Player p){
		this.players.add(p);
	}
	
	public Player[] getPlayers(){
		return this.players.toArray(new Player[]{});
	}
}
