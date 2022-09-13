/**
 * @author MugdhaSonawane
 * 
 * @author Instructor
 * File: Loc.java
 * Class: CSC 345, Spring 2022
 * Instructor: Melanie Lotz
 * Assignment: Project 3
 * 
 * PURPOSE: the object Loc represents a location in a grid, 
 * i.e. row and column and a value associated with it
 * 
 */

public class Loc {
	
    public final int row;
    public final int col;
    private String val;

    //constructor
    //x is row, y is column
    public Loc(int x, int y, String val) {
	this.row = x;
	this.col = y;
	this.val = val;
	
	
	
	
    }

    //returns Loc in the form (row, col)
    public String toString() {
	return "(" + row + ", " + col + ")";
    }

    //returns the String value at this location
    public String getVal() {
	return val;
    }
    
    /**
     * This function returns the rows and columns of this.Loc's neighbours.
     * The order is:
     * 	up Row
     *  up Col
     *  right Row
     *  right Col
     *  down Row
     *  down Col
     *  left Row
     *  left Col
     *  
     *  I only created this because it reduces the grid accesses significantly
     *  but it does take up memory since it is created for each Loc. 
     *  
     *  It is mainly used when you're looking at the elements of a Deque and 
     *  checking if the neighbours are in there.
     *  
     *  If they are not, then they are added to the deque by accessing the grid.
     * @param gridSize - used for finding out the max value of col and row 
     * @return
     */
    public int[] neighbours(int gridSize) {
    	int neighbours[] = {row - 1, col, row, col + 1, row + 1, col, row, col - 1};
    	// this has to be the first statement because 
    	// the condition is met the most frequently
    	if (row > 0 && col > 0 && row < gridSize - 2 && col < gridSize - 2) {
    		return neighbours;
    	
    	/*THESE ARE CORNER CASES (literally)*/
    	//	top-left corner
    	} else if (row == 0 && col == 0) {
    		// no up
    	    // no left
    		neighbours[0] = row; neighbours[7] = col;
    	//	bottom right corner
    	} else if (row == gridSize - 1 && col == gridSize - 1) {
    		// no right
    		// no down
    		neighbours[3] = col; neighbours[4] = row;
    		
    	// top-right corner
    	} else if (row == 0 && col == gridSize - 1) {
    		// no right
    		// no up
    		neighbours[0] = row; neighbours[3] = col;
    		
    	// bottom left corner
    	} else if (row == gridSize - 1 && col == 0) {
    		// no left
    		// no down
    		neighbours[4] = row; neighbours[7] = col;
    		
    	// anywhere at the topmost
    	} else if (row == 0) {
    		// no up
    		neighbours[0] = row;
        // anywhere at the leftmost
    	} else if (col == 0) {
    		// no left
    		neighbours[7] = col;
    	// anywhere at the bottom
    	} else if (row == gridSize - 1) {
    		// no bottom
    		neighbours[4] = row;
    	// anywhere at the rightmost
    	} else if (col == gridSize - 1) {
    		// no right
    		neighbours[3] = col;
     		
    	}
    	return neighbours;
    }
    
    
}
