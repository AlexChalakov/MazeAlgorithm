import java.util.ArrayList;

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
	private boolean visitedSq;			// true if visited, false if not
	private boolean solution;			// true if solution is found

	private static int shortestCount;	// The shortest path found so far in this search.

	private ArrayList<MazeSquare> counterPath = new ArrayList<>();	//the current path when the DFS is still searching
	private static ArrayList<MazeSquare> shortestPath = new ArrayList<>(); //the shortest path
	
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

	public void DFS(MazeSquare sqCurrent){
		setHighlight(true); // so that it highlights it when it moves
		sqCurrent.visitedSq = true; //taking the current square as target square and making it visited
		counterPath.add(sqCurrent); //adding the current square to the first piece of the path

		if(sqCurrent.target == true){ //if target is reached
			System.out.println("Solution found!!!");
			solution = true;
			sqCurrent.visitedSq = false;

			if (counterPath.size() < MazeSquare.shortestCount || MazeSquare.shortestCount == 0){ //if shortestCount is 0 or bigger than our current path
				MazeSquare.shortestCount = counterPath.size();	//make shortestCount equal our current path >0
				for(MazeSquare loop : counterPath){	//looping through our current path
					MazeSquare.shortestPath.add(loop); //and adding the elements into the shortest path
				}
			}

			if (MazeSquare.shortestPath.size() > counterPath.size()){
				MazeSquare.shortestPath = counterPath;
			}
			counterPath.remove(sqCurrent);
			//board.reset(counterPath.size());
			return;
		} else if (!solution){ //first check wall, then if visited, so that we can move around even on visited squares
			if(!sqCurrent.getWall(1)){ //checks right wall
				//System.out.println("RIGHT"); test
				MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation() + 1, sqCurrent.getYLocation());
				if(!current.visitedSq){
					DFS(current); //recursive
				}
			}
			if(!sqCurrent.getWall(0)){ //checks left wall
				//System.out.println("LEFT"); test
				MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation() - 1, sqCurrent.getYLocation());
				if(!current.visitedSq){
					DFS(current); //recursive
				}
			}
			if(!sqCurrent.getWall(2)){ //checks top wall
				//System.out.println("TOP"); test
				MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation(), sqCurrent.getYLocation() - 1);
				if(!current.visitedSq){
					DFS(current); //recursive
				}
			}
			if(!sqCurrent.getWall(3)){ //checks bottom wall
				//System.out.println("BOTTOM"); test
				MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation(), sqCurrent.getYLocation() + 1);
				if(!current.visitedSq){
					DFS(current); //recursive
				}
			}
			counterPath.remove(sqCurrent); //removes dead edges - early returns, whenever a square has been visited and it backtracks, it removes it from the count
		}

		for (MazeSquare highlighter : MazeSquare.shortestPath) { //looping through the shortestPath and highlighting every square
			highlighter.setHighlight(true);
		}
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the end point for the search.
	 */	
    public void leftClicked()
	{
		board.reset(0);
		setHighlight(true); //highlighting the left click
		this.target = true; //end point
	}
    
    /**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the start point for the search. 
	 */	
	public void rightClicked()
	{
		MazeSquare.shortestCount = 0; //before the DFS method so that it changes
		DFS(this); //calling method
		System.out.println(" *** COMPLETE: SHORTEST ROUTE " + (MazeSquare.shortestCount == 0 ? "IMPOSSIBLE" : MazeSquare.shortestCount) + " ***");
	}

	/**
	 * A method that is invoked when a reset() method is called on GameBoard.
	 * 
	 * @param n An unspecified value that matches that provided in the call to GameBoard reset()
	 */
	public void reset(int n)
	{
		this.setHighlight(false);
		if(this.target)
			this.target = false;
	}
}
