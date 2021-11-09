import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;

/**
 * This class provides a graphical model of a board game.
 * The class creates a rectangular panel of clickable squares. 
 * If a square is clicked by the user a method is invoked upon the corresponding GameSquare instance.
 * The class is intended to be used as a basis for tile based games.
 * @author Joe Finney
 */
public class GameBoard extends JFrame
{
	private JPanel boardPanel = new JPanel();

	private int boardHeight;
	private int boardWidth;
    private GameSquare[][] board; 
    private Constructor<?> makeSquare;

	/**
	 * Create a new game board of the given size.
	 * As soon as an instance of this class is created, it is visualized
	 * on the screen.
	 * 
	 * @param title the name printed in the window bar.
	 * @param width the width of the game area, in squares.
	 * @param height the height of the game area, in squares.
     * @param squareType the name of the class to construct for each position on the board. Required to be a concrete subclass of GameSquare.
	 */
	public GameBoard(String title, int width, int height, String squareType)
	{
		super();

        try {
            Class<?> parameters[] = new Class<?>[] { int.class, int.class, GameBoard.class };
            Class<?> c = Class.forName(squareType);
            makeSquare = c.getConstructor(parameters);            
        } catch (Exception e)
        {
            System.out.println("Could not find a valid subclass of GameSquare called " + squareType);
            return;
        }

        this.boardWidth = width;
		this.boardHeight = height;

		// Create game state
		this.board = new GameSquare[width][height];

		// Set up window
		setTitle(title);
		setSize(width*20,height*20);
		setContentPane(boardPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardPanel.setLayout(new GridLayout(height,width));
		
		for (int y = 0; y<height; y++)
		{
			for (int x = 0; x<width; x++)
            {
                try
                {
                    board[x][y] = (GameSquare) makeSquare.newInstance(x, y, this);
                }
                catch (Exception e)
                {
                    System.out.println("Could not find a valid constructor " +squareType + "(int, int, GameBoard)");
                }

                board[x][y].addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        GameSquare s = (GameSquare) e.getSource();

                        if (e.getButton() == MouseEvent.BUTTON1)
                            s.leftClicked();
                        else
                            s.rightClicked();
                    }
                });
                    
				boardPanel.add(board[x][y]);
			}
		}

		// make our window visible
		setVisible(true);
				
	}

	/**
	 * Returns the GameSquare instance at a given location. 
	 * @param x the x co-ordinate of the square requested.
	 * @param y the y co-ordinate of the square requested.
	 * @return the GameSquare instance at a given location if the x and y co-ordinates are valid - null otherwise. 
	 */
	public GameSquare getSquareAt(int x, int y)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
			return null;

		return board[x][y];
	}

	/**
	 * Calls the reset() method on all GameSqaures on this board.
	 * 
	 * @param n a value to pass as a parameter to each square.
	 */
	public void reset(int n)
	{
		for (int y = 0; y<boardHeight; y++)
			for (int x = 0; x<boardWidth; x++)
				board[x][y].reset(n);
	}
}
