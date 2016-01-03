package engine.tiles;

import engine.exceptions.MisconfiguredMapException;
import game.units.Commander;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

/**
 * Parser which will take in tile mapping files and generate a map given the specifications within it. 
 * @author Chris
 * @version 0.1
 */
public class TileParser {
	
	//String of the filepath neccessary for reading data from
	private String file;
	//Title of the baord/map given
	private String name;
	//Height of the board, given in tiles;
	private int height;
	//Width of the board, given in tiles;
	private int width;
	//Board mapping given, Stored as a nested set of ArrayLists
	private ArrayList<ArrayList<Tile>> boardMapping;
	
	public TileParser(String path){
		this.file = path;
	}
	
	/**
	 * Read file take the file stored at the filepath location specified and gets
	 * the parses a tile board from the given information. 
	 * @return
	 * @throws IOException
	 */
	public void parseBoard() throws IOException, MisconfiguredMapException{
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
					this.boardMapping = new ArrayList<ArrayList<Tile>>();
					for(int r = 1; r <= this.height; r++){
						ArrayList<Tile> row = new ArrayList<Tile>();
						//Convert all to upper case to avoid confusion in case statements below
						aLine = textReader.readLine().toUpperCase();
						if(this.width != aLine.length()){
							//These should all be converted into parser exceptions
							throw new MisconfiguredMapException("Map file improperly configured at line " + textReader.getLineNumber());
						}
						for(int c = 1; c <= this.width; c++){
							//c - 1 to accomodate 1 based indexing scheme
							switch(aLine.charAt(c - 1)){
								case 'N':
									row.add(new NeutralTile(r, c));
									break;
								case 'L':
									row.add(new LightSourceTile(r, c));
									break;
								case 'D':
									row.add(new DarkSourceTile(r, c));
									break;
								case 'O':
									row.add(new OpenSourceTile(r, c));
									break;
								case '1':
									Commander player1 = new Commander();
									NeutralTile start1 = new NeutralTile(r, c);
									row.add(start1);
									break;
								case '2':
									Commander player2 = new Commander();
									NeutralTile start2 = new NeutralTile(r, c);
									row.add(start2);
									break;
								default:
									throw new MisconfiguredMapException("Unexpected Character encountered, please review map file at line "+ textReader.getLineNumber());
							}
						}
						//Add new row to the 2D Array List
						this.boardMapping.add(row);
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
	}
	public static void main(String[] args){
		TileParser parser = new TileParser( "./data/sample.txt");
		try {
			parser.parseBoard();
			System.out.println("Title: "+ parser.name);
			System.out.println("Dimensions: " + parser.height +"x" + parser.width);
			for(ArrayList<Tile> row: parser.boardMapping){
				for(Tile tile: row){
					System.out.println("     "+tile.getCoordinates());
				}
			}
		} catch (IOException | MisconfiguredMapException e) {
			e.printStackTrace();
		}
	}
}
