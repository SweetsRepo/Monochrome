package engine.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.exceptions.MisconfiguredMapException;
import engine.tiles.*;

/**
 * Class which will actually start the game instance. Main Thread
 * @author Christopher Sweet - crs4263@rit.edu
 *
 */
public class Monochrome implements Runnable{

	//Board for the given match
	protected Board gameBoard;

	//ArrayList of all the player threads.
	protected Runnable[] players;
	
	//Number of players, currently capped at 2
	public static final int NUMPLAYERS = 2;
	
	public Monochrome() {
		
	}

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
			this.gameBoard = mapLoader.getBoard();
			this.players = this.gameBoard.getPlayers();
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method which will take the current thread, stop it, and start the other players thread. 
	 * This will be called at the end of each turn.
	 * @param: null
	 * @return: null
	 */
	public void swapTurn(){
		if(this.gameBoard.pid == NUMPLAYERS)
			this.gameBoard.pid = 1;
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

}
