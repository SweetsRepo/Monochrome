package engine.tiles;

import engine.exceptions.MisconfiguredMapException;
import engine.runner.DarkPlayer;
import engine.runner.LightPlayer;
import engine.runner.Player;
import engine.units.Commander;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 * Parser which will take in tile mapping files and generate a map given the specifications within it. 
 * Since the board is a singleton, it is expected that this will only be called at the start of a game
 * @author Christopher Sweet - crs4263@rit.edu
 * @version 1.0
 */
public class BoardParser {
	
	//String of the filepath neccessary for reading data from
	private String file;
	//Title of the baord/map given
	private String name;
	//Height of the board, given in tiles;
	private int height;
	//Width of the board, given in tiles;
	private int width;
	
	private Board gameBoard;
	
	public BoardParser(String path){
		this.file = path;
	}
	
	/**
	 * Read file take the file stored at the filepath location specified and gets
	 * the parses a tile board from the given information. 
	 * @return
	 * @throws IOException
	 */
	public Board parseBoard() throws IOException, MisconfiguredMapException{
		FileReader readIn = new FileReader(this.file);
		LineNumberReader textReader = new LineNumberReader(readIn);
		String aLine;
		while((aLine = textReader.readLine()) != null){
			switch(aLine){
				case "Title:":
					//Reads the next line to get the title and sets it to the variable name
					this.name = (aLine = textReader.readLine());
					break;
				case "Width:":
					this.width = Integer.parseInt((aLine = textReader.readLine()));
					break;
				case "Height:":
					this.height = Integer.parseInt((aLine = textReader.readLine()));
					break;
				case "Mapping:":
					//Get an instance of the board. Should only be called once, when initializing the game
					this.gameBoard = Board.getInstance();
					for(int r = 0; r < this.height; r++){
						ArrayList<Tile> row = new ArrayList<Tile>();
						//Convert all to upper case to avoid confusion in case statements below
						aLine = textReader.readLine().toUpperCase();
						if(this.width != aLine.length()){
							//These should all be converted into parser exceptions
							throw new MisconfiguredMapException("Map file improperly configured at line " + textReader.getLineNumber());
						}
						for(int c = 0; c < this.width; c++){
							//c - 1 to accommodate 1 based indexing scheme
							switch(aLine.charAt(c)){
								case 'N':
									row.add(new OpenSourceTile());
									break;
								case 'L':
									row.add(new LightSourceTile());
									break;
								case 'D':
									row.add(new DarkSourceTile());
									break;
								case '1':
									//Create Commander and set start location
									Commander player1 = new Commander();
									OpenSourceTile start1 = new OpenSourceTile();
									start1.setUnit(player1);
									row.add(start1);
									
									//Create player object and start the thread
									Player p1 = new LightPlayer(0);
									Thread player1Thread = new Thread(p1);
									player1Thread.start();
									p1.addUnit(player1);
									gameBoard.addPlayer(p1);
									break;
								case '2':
									//Create Commander and set start location
									Commander player2 = new Commander();
									OpenSourceTile start2 = new OpenSourceTile();
									start2.setUnit(player2);
									row.add(start2);
									
									//Create player object and start the thread
									Player p2 = new DarkPlayer(1);
									Thread player2Thread = new Thread(p2);
									player2Thread.start();
									p2.addUnit(player2);
									gameBoard.addPlayer(p2);
									break;
								default:
									throw new MisconfiguredMapException("Unexpected Character encountered, please review map file at line "+ textReader.getLineNumber());
							}
						}
						//Add new row to the 2D Array List
						gameBoard.tiles.add(row);
					}
					break;
				case "":
					//If blank line, go to next line
					break;
				default:
					System.err.println("Unexpected Header encountered, please review map file");
			}	
		}
		textReader.close();
		//Create reference to the gameboard in each of the players
		for(Player p: this.gameBoard.getPlayers()){
			p.setBoard(gameBoard);
		}
		return gameBoard;
	}
	
	/**
	 * Parses the board and returns it. Single Board instance per game - only called once
	 * @return - Board, dependent on input file
	 * @throws IOException - 
	 * @throws MisconfiguredMapException - Returns number of line which was not properly setup
	 */
	public Board getBoard() throws IOException, MisconfiguredMapException{
		return this.parseBoard();
	}
	
	//Tester
	public static void main(String[] args){
		BoardParser parser = new BoardParser( "./data/sample.txt");
		try {
			parser.parseBoard();
			System.out.println("Title: "+ parser.name);
			System.out.println("Dimensions: " + parser.height +"x" + parser.width);
			for(ArrayList<Tile> row: parser.gameBoard.tiles){
				for(Tile tile: row){
					System.out.print("     "+tile.getOwner());
				}
				System.out.println("");
			}
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
	}
}
