package game.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.board.*;
import engine.exceptions.MisconfiguredMapException;
import game.buildings.Building;
import game.units.Unit;
import game.units.Worker;

/**
 * Class which will actually start the game instance. Main Thread
 * @author Christopher Sweet - crs4263@rit.edu
 *
 */
public class Monochrome implements Runnable{

	//Board for the given match
	protected Board gameBoard;

	//ArrayList of all the player threads.
	protected Player[] players;
	

	/**
	 * Run method must create additional threads for the players, parse and create the board,
	 * and instantiate all related data structures for the game.
	 * @param: null
	 * @return: null
	 */
	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a map file to load: ");
		String fname = scan.next();
		BoardParser mapLoader = new BoardParser(fname);
		try {
			this.gameBoard = mapLoader.parseBoard();
			this.players = this.gameBoard.getPlayers();
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
		scan.close();
	}
	
	/**
	 * Method which will take the current thread, stop it, and start the other players thread. 
	 * This will be called at the end of each turn.
	 * @param: null
	 * @return: null
	 */
	public void swapTurn(){
		if(this.gameBoard.pid == GameSettings.NUMPLAYERS)
			this.gameBoard.pid = 0;
		else
			this.gameBoard.pid++;
		for(int i = 0; i < players.length; i++){
			try{
				//Notify the main thread that this player's turn is now in effect
				if(this.gameBoard.pid == i)
					players[i].notify();
				//Put all other players to sleep
				else
					players[i].wait();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Serves as the sender upon a board tile click. 
	 * Provides a list of possible operations dependent upon the tile attributes
	 * @param r - row index
	 * @param c - column index
	 */
	public void boardTileClick(int r, int c){
		//ArrayList of the possible player options when clicking on this tile
		ArrayList<String> options = new ArrayList<String>();
		//Build the options list
		options.add("Cancel");
		//If the tile is occupied move and attack are valid options iff the unit belongs to the active player.
		if(this.gameBoard.getTiles().get(r).get(c).isOccupied()){
			if(this.getActivePlayer().getUnits().contains(this.gameBoard.getTiles().get(r).get(c).getUnit())){
				options.add("Move");
				options.add("Attack");
				options.add("Claim");
				if(this.gameBoard.getTiles().get(r).get(c).getUnit().canMine())
					options.add("Mine");
				if(this.gameBoard.getTiles().get(r).get(c).getUnit().canBuild())
					options.add("Build");
				if(this.gameBoard.getTiles().get(r).get(c).getBuilding() instanceof Building)
					options.add("Produce");
			}
		}
		//Allow the user to select their option - Update the UI
		System.out.print("Options for this tile: ");
		for(String opt : options){
			System.out.println(opt);
		}
		//Allow for user click in the UI
		Scanner scan = new Scanner(System.in);
		String choice = "";
		System.out.println("Echo one of the options above");
		while(!options.contains(choice)){
			choice = scan.nextLine();
		}
		//Tiles which are available for the selected action
		ArrayList<Coordinate> availableTiles;
		int row;
		int col;
		switch(choice){
			case "Cancel":
				scan.close();
				return;
			case "Move":
				availableTiles = this.gameBoard.findAvailableMoves(r, c);
				//Highlight the available tiles and let the user select one
				System.out.println("Enter the row and column index of the tile you would like to select");
				row = scan.nextInt();
				col = scan.nextInt();
				//Check to make sure the selected tile is a valid tile
				if(availableTiles.contains(this.gameBoard.getTiles().get(row).get(col)))
					this.gameBoard.moveUnit(r, c, row, col);
				break;
			case "Attack":
				availableTiles = this.gameBoard.findAvailableAttacks(r, c);
				//Highlight the available tiles and let the user select one
				System.out.println("Enter the row and column index of the tile you would like to select");
				row = scan.nextInt();
				col = scan.nextInt();
				if(availableTiles.contains(this.gameBoard.getTiles().get(row).get(col)))
					this.gameBoard.attackUnit(r, c, row, col);
				break;
			case "Build":
				availableTiles = this.gameBoard.findAvailableBuilds(r, c);
				//Highlight the available tiles and let the user select one
				System.out.println("Enter the row and column index of the tile you would like to select");
				row = scan.nextInt();
				col = scan.nextInt();
				System.out.println("Enter the name of the building type you would like to build");
				choice = scan.nextLine();
				if(availableTiles.contains(this.gameBoard.getTiles().get(row).get(col)))
					this.gameBoard.buildOnTile(row, col, choice);
				break;
			case "Produce":
				availableTiles = this.gameBoard.findAvailableBuilds(r, c);
				//Highlight the available tiles and let the user select one
				System.out.println("Enter the row and column index of the tile you would like to select");
				row = scan.nextInt();
				col = scan.nextInt();
				System.out.println("Enter the name of the unit type you would like to build");
				choice = scan.nextLine();
				if(availableTiles.contains(this.gameBoard.getTiles().get(row).get(col)))
					this.gameBoard.createOnTile(row, col, choice);
				break;
			case "Mine":
				//Add the resources gained from mining to the player's resources
				this.getActivePlayer().setResources(this.getActivePlayer().getResources() + this.gameBoard.getTiles().get(r).get(c).mine());
				break;
			case "Claim":
				//If the tile is controlled by the enemy removes it from their control.
				//If the tile is neutral, it is added to the players control
				this.gameBoard.takeTile(r, c);
		}
		scan.close();
	}

	public Board getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;
	}

	public Runnable[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public Player getActivePlayer(){
		return this.players[this.gameBoard.pid];
	}

}
