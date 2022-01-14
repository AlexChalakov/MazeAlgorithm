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
	private int weights[][] = new int [10][10];
	private boolean solution = false;			// true if solution is found
	private int targetX = -1;
	private int targetY = -1;

	private static int shortestCount;	// The shortest path found so far in this search.

	//private ArrayList<MazeSquare> counterPath = new ArrayList<>();	//the current path when the DFS is still searching
	//private static ArrayList<MazeSquare> shortestPath = new ArrayList<>(); //the shortest path
	
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

	public void DFS(GameSquare gameSquare, int x, int y, int weight){
		//setHighlight(true); // so that it highlights it when it moves
		visitedSq[x][y] = true; //taking the current square as target square and making it visited
		weights[x][y] = weight;
		//counterPath.add(sqCurrent); //adding the current square to the first piece of the path

		if(x == targetX && y == targetY){ //if target is reached
			//System.out.println("Solution found!!!");
			solution = true;
			return;
			//visitedSq[x][y] = false;

			/*if (counterPath.size() < MazeSquare.shortestCount || MazeSquare.shortestCount == 0){ //if shortestCount is 0 or bigger than our current path
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
			return;*/
		}  //first check wall, then if visited, so that we can move around even on visited squares
			
		if(!gameSquare.getWall(0) && x > 0){ //checks left wall
				//System.out.println("LEFT"); test
				//MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation() - 1, sqCurrent.getYLocation());
			if(!visitedSq[x-1][y] || weights[x-1][y] > weight){
					DFS(board.getSquareAt(x-1, y), x-1, y, weight+1); //recursive
				}
			}
		if(!gameSquare.getWall(1) && x < 9){ //checks right wall
				//System.out.println("RIGHT"); test
				//MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation() + 1, sqCurrent.getYLocation());
				if(!visitedSq[x+1][y] || weights[x+1][y] > weight){
					DFS(board.getSquareAt(x+1, y), x+1, y, weight+1); //recursive
				}
			}
		if(!gameSquare.getWall(2) && y > 0){ //checks top wall
				//System.out.println("TOP"); test
				//MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation(), sqCurrent.getYLocation() - 1);
				if(!visitedSq[x][y-1] || weights[x][y-1] > weight){
					DFS(board.getSquareAt(x, y-1), x, y-1, weight+1); //recursive
				}
			}
		if(!gameSquare.getWall(3) && y < 9){ //checks bottom wall
				//System.out.println("BOTTOM"); test
				//MazeSquare current = (MazeSquare) board.getSquareAt(sqCurrent.getXLocation(), sqCurrent.getYLocation() + 1);
				if(!visitedSq[x][y+1] || weights[x][y+1] > weight){
					DFS(board.getSquareAt(x, y+1), x, y+1, weight+1); //recursive
				}
			}
			//counterPath.remove(gameSquare); //removes dead edges - early returns, whenever a square has been visited and it backtracks, it removes it from the count
			return;
		}

		/*for (MazeSquare highlighter : MazeSquare.shortestPath) { //looping through the shortestPath and highlighting every square
			highlighter.setHighlight(true);
		}*/

	public void shortestPath (GameSquare gameSquare, int weight){
		GameSquare nextSquare;
		gameSquare.setHighlight(true);
		shortestCount++;

		int x = gameSquare.getXLocation();
		int y = gameSquare.getYLocation();

		if(weight == 0){
			return;
		}

		if(!gameSquare.getWall(0) && weights[x-1][y] == weight - 1){
			nextSquare = board.getSquareAt(x-1, y);
		} 
		else if ((!gameSquare.getWall(1) && weights[x+1][y] == weight - 1)){
			nextSquare = board.getSquareAt(x+1, y);
		} 
		else if((!gameSquare.getWall(2) && weights[x][y-1] == weight - 1)){
			nextSquare = board.getSquareAt(x, y-1);
		} else {
			nextSquare = board.getSquareAt(x, y+1);
		}

		shortestPath(nextSquare, weight-1);
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the end point for the search.
	 */	
    public void leftClicked()
	{
		board.reset(0);
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

		for(int i = 0; i < 10;i++){
			for(int j = 0; j < 10; j++){
				visitedSq[i][j] = false;
			}
		}

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

		if(solutionFound){
			MazeSquare.shortestCount = 0; //before the DFS method so that it changes

			DFS(this, getXLocation(), getYLocation(), 0); //calling method
			board.reset(0);

			shortestPath(board.getSquareAt(targetX, targetY), weights[targetX][targetY]);

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
		if(this.target)
			this.target = false;
	}
}
