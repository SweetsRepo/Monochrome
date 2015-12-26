package engine;

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
	public void parseBoard() throws IOException{
		FileReader readIn = new FileReader(this.file);
		LineNumberReader textReader = new LineNumberReader(readIn);
		String aLine;
		while((aLine = textReader.readLine()) != null && !aLine.equals("/break/")){
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
					for(int r = this.height; r > 0; r--){
						ArrayList<Tile> row = new ArrayList<Tile>();
						aLine = textReader.readLine();
						for(int c = 0; c < aLine.length(); c++){
							switch(aLine.charAt(c)){
								case 'X':
									//Parse generic tile
									//Add to ArrayList
									break;
							}
						}
						//Add new row to the 2D Array List
						this.boardMapping.add(row);
					}
					break;
				default:
					System.err.println("Unexpected character encountered, please review map file");
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
			System.out.println(parser.boardMapping != null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
