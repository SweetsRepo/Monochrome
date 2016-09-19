package engine.runner;

import java.io.IOException;
import java.util.Scanner;

import engine.exceptions.MisconfiguredMapException;
import engine.tiles.*;

/**
 * Class which will actually start the game instance. Main Thread
 * @author Christopher Sweet - crs4263@rit.edu
 *
 */
public class Monochrome implements Runnable{

	public Monochrome() {
		
	}

	/**
	 * Run method must create additional threads for the players, parse and create the board,
	 * and instantiate all related data structures for the game.
	 */
	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a map file to load: ");
		String fname = scan.next();
		BoardParser mapLoader = new BoardParser(fname);
		try {
			Board gameBoard = mapLoader.getBoard();
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
		
		//Create player objects and start their threads
		Player p1 = new LightPlayer();
		Thread player1Thread = new Thread(p1);
		player1Thread.start();
		
		//Create player objects and start their threads
		Player p2 = new DarkPlayer();
		Thread player2Thread = new Thread(p2);
		player2Thread.start();
		
	}
	
	/**
	 * Method which will take the current thread, stop it, and start the other players thread. 
	 * This will be called at the end of each turn.
	 */
	public void swapTurn(){
		
	}

}
