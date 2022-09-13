/**
 * @author MugdhaSonawane
 * 
 * @author Instructor
 * 
 * File: Puzzle.java
 * Class: CSC 345, Spring 2022
 * Instructor: Melanie Lotz
 * Assignment: Project 3
 * 
 * PURPOSE: this finds the path of a word in a grid of 
 * letters
 * 
 */
public class Puzzle {
	
	/* this is a global variable because 
	 * i needed to extract out the found 
	 * path from dfs, before locations were 
	 * popped off the stack in the return 
	 * calls of dfs recursion
	 */
	private Deque<Loc> pathway;
	
	private Grid grid;
	
	/* this is for getting the correct 
	 * neighbours' coordinates of a Loc
	 */
	private int gridSize;
	
	/*this one is a control for stopping bfs*/
	private boolean pathFound;
	
	public Puzzle(Grid grid) {
		this.grid = grid;
		this.gridSize = grid.size();
		pathway = new Deque<Loc>();
		pathFound = false;
	}
	
	/**
	 * This function finds the path for a word and returns 
	 * it as a String of row and column tuples
	 * 
	 * @param word the word we're searching for
	 * @param r starting location
	 * @param c starting location
	 * @return string that represents the path
	 * @throws EmptyDequeException
	 */
	public String find(String
			   word, int r, int c) {

		Loc l = grid.getLoc(r, c);
		Deque<Loc> toVisit = new Deque<Loc>();
		Deque<Loc> visitedItems = new Deque<Loc>();
		toVisit.addToBack(l);		
	 
		bfs(word, toVisit, visitedItems);
		String retval = this.pathwayFormatting();
		
		// resetting for the next call
		pathway = new Deque<Loc>();
		pathFound = false;
		return retval;
		
	}
	
	/**
	 * This function formats the pathway into a proper string
	 * 
	 * @return retVal, string that represents the pathway
	 * @throws EmptyDequeException
	 */
	private String pathwayFormatting() {
		String retVal = "";
		int control = pathway.size();
		for (int x = 0; x < control; x++) {
			try {
				retVal += pathway.getFirst().toString();
			} catch (EmptyDequeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retVal;
	}
	
	/**
	 * This is the bfs algorithm. It searches for the neighbours, 
	 * and the neighbours of neighbours and so on until it finds 
	 * the first letter in the word we're supposed to look for
	 * 
	 * @param word
	 * @param toVisit, a deque with all the locations that need to be visited
	 * @param visitedItems, a deque with all the locations that have been visited
	 * @throws EmptyDequeException
	 */
	private void bfs(String word, Deque<Loc> toVisit, Deque<Loc> visitedItems) {
		if (toVisit.isEmpty()) {
			return;
		}
		// make an object with 'r' and 'c' Loc object
		Loc itemToVisit = null;
		try {
			itemToVisit = toVisit.getFirst();
		} catch (EmptyDequeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			// add itemToVisit to a data structure that will contain
			// items that have already been visited
		
		// the add to visitedItems is added before visiting the item because you 
		// don't know what will happen with DFS
		// either the path will be found: the position of the line 67 won't matter
		// but if the path is not found, you want the itemToVisit included in the
		// visitedItems
		visitedItems.addToBack(itemToVisit);
		if (itemToVisit.getVal().equals(Character.toString(word.charAt(0)))) {

			Deque<Loc> path = new Deque<Loc>();

			// dfs initiated here
			dfs(path, itemToVisit, word);
			

			// only make toVisit empty when the path has been found
			// so that bfs will finish
			if (pathFound && toVisit.size() > 0) {
				toVisit.makeEmpty();
			}
			
		} else {
		
			int[] neighbours = itemToVisit.neighbours(this.gridSize);
			
			// -2 cause you're doing x + 1 in the for loop
			// so the last x should be 1 less than the last index, not 
			// the length
			
			// itemToVisit.neighbours(this.gridSize).length - 2 -- will always be 6
			// because there are only 4 neighbours
			// the last index is 7
			// so the last x should be 6
			for (int x = 0; x <= 6; x+=2) {
				//x -- row
				//x+1 -- col
				
				/*this bit of code reduces the number of gridAccesses significantly*/
				if (!toVisit.inDeque(neighbours[x], neighbours[x+1]) 
						&& !visitedItems.inDeque(neighbours[x], neighbours[x+1])) {
					toVisit.addToBack(grid.getLoc(neighbours[x], neighbours[x+1]));
				}
	
			}
		}
		bfs(word, toVisit, visitedItems);
	}
	

	/**
	 * This is the dfs algorithm. Once it gets the location of the 
	 * first letter, it tries to find a path that outlines the word. 
	 * If it can't, the function returns to the bfs algorithm and the 
	 * search continues. 
	 * 
	 * @param path
	 * @param startItem
	 * @param word
	 * @throws EmptyDequeException
	 */
	private void dfs(Deque<Loc> path, Loc startItem, String word) {
		// location that is being examined is being pushed onto the stack
		path.addToBack(startItem);
		if (path.size() == word.length()) {
			// you don't want to go further
			// make the path to equal the global variable
			// you're equalling pathway to the reference of path
			// you need to make a copy of path and make it equal to pathway
			
			this.pathway = this.pathCopy(path);
			return;
		} else {
			// 3. get the neighbours of the most recent Loc on the stack
			int[] possibleSteps = startItem.neighbours(gridSize);
			
			String currentLetter = Character.toString(word.charAt(path.size()));
	
			// itemToVisit.neighbours(this.gridSize).length - 2 -- will always be 6
			for (int x = 0; x <= 6 && pathFound == false; x+=2) {
				// check if the val of the neighbours equals the currentLetter
				// I think you'll have to access the grid here to get the value
				// because even though you have the neighbours location, you don't 
				// have the Loc Object for neighbours that will let you know the value
				Loc currentNeighbour = grid.getLoc(possibleSteps[x], possibleSteps[x+1]);
				if (currentLetter.equals(currentNeighbour.getVal())) {
					// this is where you will call the dfs() function 
					// with the start item being path.peekLast();
					dfs(path, currentNeighbour, word);
				}
			}

			// if there wasn't a match from the neighbours of the current 
			// location being examined then it's not the location we want. 
			// So we pop it off the stack
			if (path.size() > 0) {
				try {
					path.getLast();
				} catch (EmptyDequeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return;
		
	}
	
	/**
	 * This function makes a copy of the deque and also turns the control 
	 * of 'pathFound' to true. 
	 * 
	 * It's used in the dfs because I don't want to lose the found path when 
	 * the recursion calls are returned
	 * 
	 * @param path
	 * @return
	 * @throws EmptyDequeException
	 */
	private Deque<Loc> pathCopy(Deque<Loc> path) {
		pathFound = true;
		Deque<Loc> pathCopy = new Deque<Loc>();
		int control = path.size();
		for (int x = 0; x < control; x++) {
			try {
				pathCopy.addToBack(path.getFirst());
			} catch (EmptyDequeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pathCopy;
	}
	
}
