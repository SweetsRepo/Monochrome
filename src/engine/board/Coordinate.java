package engine.board;

/**
 * Class to store r,c coordinate of tile when performing DFS for attack
 * build and move. Ensures global accessibility of tiles and their relative position.
 * @author Christopher Sweet - crs4263@rit.edu
 * @version: 1.0
 */
public class Coordinate {

	public int row;
	public int col;
	
	public Coordinate(int r, int c){
		this.row = r;
		this.col = c;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
    @Override
    public boolean equals(Object o) {
        //If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
        /*Check if o is an instance of Coordinate or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coordinate)) {
            return false;
        }
        //typecast o to Coordinate so that we can compare data members 
        Coordinate c = (Coordinate) o;
         
        //Compare the data members and return accordingly 
        return ((Integer.compare(row, c.row) == 0) && (Double.compare(col, c.col) == 0));
    }
	
    @Override
    public int hashCode(){
    	return 10*row+col;
    }
	
}
