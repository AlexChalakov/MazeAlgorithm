/**
 * This class, once completed, should provide a recursive depth first solution
 * to finding the SHORTEST route between two squares on a GameBoard.
 * 
 * @see GameBoard
 * @see GameSquare
 * @author Joe Finney
 */
public class MazeSquare extends GameSquare
{
	private GameBoard board;			// A reference to the GameBoard this square is part of.
	private boolean target;				// true if this square is the target of the search.

	private boolean visitedSq[][] = new boolean [10][10];	// true if visited, false if not.
	private int shorterPath[][] = new int [10][10];  //paths
	
	private int targetX = -1;
	private int targetY = -1;

	private static int shortestCount;	// The shortest path found so far in this search.
	
	/**
	 * Create a new GameSquare, which can be placed on a GameBoard.
	 * @param x the x co-ordinate of this square on the game board.
	 * @param y the y co-ordinate of this square on the game board.
	 * @param board the GameBoard that this square resides on.
	 */
	public MazeSquare(int x, int y, GameBoard board)
	{
		super(x, y);
		this.board = board;
	}

	/**
	 * Method for Depth-First Search, which is my algorithm for finding the paths
	 * @param gameSquare the GameBoard that this square resides on.
	 * @param x  the x co-ordinate of this square on the game board.
	 * @param y  the y co-ordinate of this square on the game board.
	 * @param counter the int counting the path
	 */
	public void DFS(GameSquare gameSquare, int x, int y, int counter){
		visitedSq[x][y] = true; //taking the coordinates of target square and making it visited
		shorterPath[x][y] = counter;	//initialising the paths to be the path from arguments - starts from 0
		
		if(x == targetX && y == targetY){ //if target is reached
			return;
		} 
		
		//starts with checking directions 
		if(!gameSquare.getWall(0) && x > 0){ //checks left wall
			if(!visitedSq[x-1][y] || shorterPath[x-1][y] > counter){	//if left square hasn't been visited or 
					DFS(board.getSquareAt(x-1, y), x-1, y, counter+1); //recursive - go to the square and add 1 to the path variable
				}
			}
		if(!gameSquare.getWall(1) && x < 9){ //checks right wall
				if(!visitedSq[x+1][y] || shorterPath[x+1][y] > counter){	//if right square hasn't been visited
					DFS(board.getSquareAt(x+1, y), x+1, y, counter+1); //recursive - go to the square and add 1 to the path variable
				}
			}
		if(!gameSquare.getWall(2) && y > 0){ //checks top wall
				if(!visitedSq[x][y-1] || shorterPath[x][y-1] > counter){ //if top square hasn't been visited
					DFS(board.getSquareAt(x, y-1), x, y-1, counter+1); //recursive - go to the square and add 1 to the path variable
				}
			}
		if(!gameSquare.getWall(3) && y < 9){ //checks bottom wall
				if(!visitedSq[x][y+1] || shorterPath[x][y+1] > counter){ //if bottom square hasn't been visited
					DFS(board.getSquareAt(x, y+1), x, y+1, counter+1); //recursive - go to the square and add 1 to the path variable
				}
			}
		return;
	}

	/**
	 * The method that finds the shortest paths out of them all.
	 * @param gameSquare the GameBoard that this square resides on.
	 * @param counter the int counting the path
	 */
	public void shortestPath (GameSquare gameSquare, int counter){
		GameSquare nextSquare; //the nextsquare in our path
		gameSquare.setHighlight(true); //highlight the current (or previous ones) to be yellow - visited
		shortestCount++; //adding to the shortest path 

		//coordinates to get the location
		int x = gameSquare.getXLocation(); 
		int y = gameSquare.getYLocation();

		//making sure the path length can't be 0
		if(counter == 0){
			return;
		}

		if(!gameSquare.getWall(0) && shorterPath[x-1][y] == counter - 1){ //if there is a wall on the left and the shortest path coordinates pass through the shortest counter
			nextSquare = board.getSquareAt(x-1, y); //the next left square is added to the path
		} 
		else if ((!gameSquare.getWall(1) && shorterPath[x+1][y] == counter - 1)){
			nextSquare = board.getSquareAt(x+1, y); //the next right square is added to the path
		} 
		else if((!gameSquare.getWall(2) && shorterPath[x][y-1] == counter - 1)){
			nextSquare = board.getSquareAt(x, y-1); //the next top square is added to the path
		} else {
			nextSquare = board.getSquareAt(x, y+1); //the next bottom square is added to the path
		}

		shortestPath(nextSquare, counter-1);	//making sure that the SHORTEST PATH IS ALWAYS THE SMALLEST WEIGHT NUMBER
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the end point for the search.
	 */	
    public void leftClicked()
	{
		board.reset(0); //reseting every time i click the left button so it starts over again
		this.target = true; //end point
		this.setHighlight(true); //highlighting the left click
	}
    
    /**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the start point for the search. 
	 */	
	public void rightClicked()
	{
		boolean solutionFound = false;

		//making all the squares false
		for(int i = 0; i < 10;i++){
			for(int j = 0; j < 10; j++){
				visitedSq[i][j] = false;
			}
		}

		//if going through all the squares, the one i click is highlighted then a solution has been found
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(board.getSquareAt(i, j).isHighlighted()){
					solutionFound = true; 
					targetX = i;
					targetY = j;
					break;
				}
			}
			if(solutionFound){
				break;
			}
		}

		//if a solution has been found
		if(solutionFound){
			MazeSquare.shortestCount = 0; //before the DFS method so that it changes

			DFS(this, getXLocation(), getYLocation(), 0); //calling method
			board.reset(0); //resetting

			shortestPath(board.getSquareAt(targetX, targetY), shorterPath[targetX][targetY]); //finding the shortest path

			System.out.println(" *** COMPLETE: SHORTEST ROUTE " + (MazeSquare.shortestCount == 0 ? "IMPOSSIBLE" : MazeSquare.shortestCount) + " ***");
		}
	}

	/**
	 * A method that is invoked when a reset() method is called on GameBoard.
	 * 
	 * @param n An unspecified value that matches that provided in the call to GameBoard reset()
	 */
	public void reset(int n)
	{
		this.setHighlight(false);
		if(this.target){
			this.target = false;
		}
	}
}
