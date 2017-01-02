package engine.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.TreeSet;

import engine.buildings.Barracks;
import engine.buildings.Building;
import engine.exceptions.MisconfiguredMapException;
import engine.runner.Player;
import engine.units.Commander;
import engine.units.Unit;
import engine.units.Worker;

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
	private ArrayList<Tile> tilesAvailable;

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
		this.tilesAvailable = new ArrayList<Tile>();
		this.tilesByPlayer = new Hashtable<Owner, ArrayList<Tile>>();
		this.players = new ArrayList<Player>();
		this.pid = 0;
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
		if(this.tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as we want moving to the same tile to be a valid movement
			this.tiles.get(r).get(c).setOccupied(false);
			attackTiles = availableAttacksDFS(r, c, this.tiles.get(r).get(c).getUnit().getAttRange());
			this.tiles.get(r).get(c).setOccupied(true);
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
		if(this.tiles.get(r).get(c).isOccupied && !this.tilesAvailable.contains(this.tiles.get(r).get(c)))
			this.tilesAvailable.add(this.tiles.get(r).get(c));
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
		if(this.tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as we want moving to the same tile to be a valid movement
			this.tiles.get(r).get(c).setOccupied(false);
			moveTiles = availableMovesDFS(r, c, this.tiles.get(r).get(c).getUnit().getRange());
			this.tiles.get(r).get(c).setOccupied(true);
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
			if(!this.tilesAvailable.contains(this.tiles.get(r).get(c)) && !(this.tiles.get(r).get(c).isOccupied))
				this.tilesAvailable.add(this.tiles.get(r).get(c));
			//End of recursive depth
		}
		//Add the tile to available moves and recurse to the next set
		else{
			//Make sure to truncate any paths which are blocked by occupied tiles
			if(!this.tiles.get(r).get(c).isOccupied){
				//Don't allow for any duplicate tiles
				if(!this.tilesAvailable.contains(this.tiles.get(r).get(c)))
					this.tilesAvailable.add(this.tiles.get(r).get(c));
				if(r - 1 >= 0)
					availableMovesDFS(r - 1, c, depth - 1);
				if(c - 1 >= 0)
					availableMovesDFS(r, c - 1, depth - 1);
				if(r + 1 <= this.tiles.size() - 1)
					availableMovesDFS(r + 1, c, depth - 1);
				if(c + 1 <= this.tiles.get(r).size() - 1)
					availableMovesDFS(r, c + 1, depth - 1);
				//All paths exhausted, return the result
				return this.tilesAvailable;
			}
		}
		//Terminate end of depth runs.
		return null;
	}
	
	/**
	 * Clear previous available tiles result and then call Recursive DFS to find all build options from a given tile
	 * @param r - row index
	 * @param c - column index
	 * @param depth - remaining depth to search
	 * @return - List of all the available build options
	 */
	public synchronized ArrayList<Tile> findAvailableBuilds(int r, int c){
		ArrayList<Tile> buildTiles;
		this.tilesAvailable.clear();
		if(this.tiles.get(r).get(c).isOccupied){
			//Treat the tile that the unit is on as unoccupied, as building on the same tile is allowable
			this.tiles.get(r).get(c).setOccupied(false);
			buildTiles = availableBuildDFS(r, c, this.tiles.get(r).get(c).getUnit().getBuildRange());
			this.tiles.get(r).get(c).setOccupied(true);
			return buildTiles;
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
	 * @return - List of all the available build options
	 */
	private ArrayList<Tile> availableBuildDFS(int r, int c, int depth){
		//Make sure that the recursive depth hasn't been reached
		if(depth == 0){
			if(!this.tilesAvailable.contains(this.tiles.get(r).get(c)) && !(this.tiles.get(r).get(c).isOccupied))
				this.tilesAvailable.add(this.tiles.get(r).get(c));
			//End of recursive depth
		}
		//Add the tile to available moves and recurse to the next set
		else{
			//Make sure to truncate any paths which are blocked by occupied tiles
			if(!this.tiles.get(r).get(c).isOccupied){
				//Don't allow for any duplicate tiles
				if(!this.tilesAvailable.contains(this.tiles.get(r).get(c)))
					this.tilesAvailable.add(this.tiles.get(r).get(c));
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
				return this.tilesAvailable;
			}
		}
		//Terminate end of depth runs.
		return null;
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
			}
			else{
				//Remove the tile from the other players control
				this.tilesByPlayer.get(Owner.getOpposite(this.getPlayers()[this.pid].getFaction())).remove(this.tiles.get(r).get(c));
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
	 * Attacks the unit at tile[er, ec] with unit at tile[sr, sc]
	 * If the unit is killed the attacker moves up to the new tile
	 * @param sr - start row
	 * @param sc - start column 
	 * @param er - end row
	 * @param ec - end column
	 */
	public synchronized void attackUnit(int sr, int sc, int er, int ec){
		Unit underAttack = this.tiles.get(er).get(ec).getUnit();
		Unit dealingAttack = this.tiles.get(sr).get(sc).getUnit();
		//DamageDealt method returns a boolean indicating if the unit under attack is still alive post attack
		if(underAttack.damageDealt(dealingAttack.getAttDamage())){
			this.tiles.get(er).get(ec).removeUnit();
			//NOTE: Create a reference from the unit to it's controlling player
			//Remove the unit from the player's unit list
			this.moveUnit(sr, sc, er, ec);
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
	public synchronized void produceOnTile(int r, int c, String unit){
		Unit produce = ((Barracks)(this.tiles.get(r).get(c).getBuilding())).constructUnit(this.getPlayers()[this.pid].getResources(), unit);
		if(produce != null)
			this.tiles.get(r).get(c).setUnit(produce);
	}
	
	public ArrayList<Tile> getTilesAvailable() {
		return tilesAvailable;
	}

	public void setTilesAvailable(ArrayList<Tile> tilesAvailable) {
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
		return (Player[])(this.players.toArray());
	}
}