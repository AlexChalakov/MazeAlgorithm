
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
	private boolean visitedSq = false;
	private boolean currentSq;
	private MazeSquare square;

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

	public void DFS(MazeSquare square,int counterPath){
		setHighlight(true);
		visitedSq = true;
		visitedSq = currentSq;

		int direction = 1;

		switch(direction){
            case 1: //Up
                board.getSquareAt(getXLocation(), getYLocation() + 1);
            case 2: //Down
                board.getSquareAt(getXLocation(), getYLocation() - 1);
            case 3: //Right
                board.getSquareAt(getXLocation() + 1, getYLocation());
            case 4: //Left
                board.getSquareAt(getXLocation() - 1, getYLocation());
            default:
                System.out.println("Direction unclear!");
        }

		if(target == true){
			System.out.println("Solution found");
			return;
		}

		if(getWall(direction)|| visitedSq){
			
		}
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the end point for the search.
	 */	
    public void leftClicked()
	{
		setHighlight(true);
		this.target = true;
	}
    
    /**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the start point for the search. 
	 */	
	public void rightClicked()
	{
		setHighlight(true);
		DFS(square, 1);
		MazeSquare.shortestCount = 0;
		System.out.println(" *** COMPLETE: SHORTEST ROUTE " + (MazeSquare.shortestCount == 0 ? "IMPOSSIBLE" : MazeSquare.shortestCount) + " ***");
	}

	/**
	 * A method that is invoked when a reset() method is called on GameBoard.
	 * 
	 * @param n An unspecified value that matches that provided in the call to GameBoard reset()
	 */
	public void reset(int n){}
}
