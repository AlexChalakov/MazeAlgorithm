import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class provides a representation of a single game square.
 * The class is abstract, and should be extended to provide domain 
 * specific functionality.
 * @author Joe Finney
 */
public abstract class GameSquare extends JButton
{
	/* Enumeration of directions */
	public static int WALL_LEFT = 0;
	public static int WALL_RIGHT = 1;
	public static int WALL_TOP = 2;
	public static int WALL_BOTTOM = 3;

	/** The x co-ordinate of this square. **/
	private int xLocation;

	/** The y co-ordinate of this square. **/
	private int yLocation;

	/** Defines if this square has a wall on any of its sides*/
	private boolean[] wall = new boolean[4];

	/** Defines if this square should be highlighted */
	private boolean highlighted;

	/** Defines the size of this square **/
	private int size;

	/** Defined the width of a wall, as a percentage of the square's size */
	private int wallWidth;

	/**
	 * Create a new GameSquare, which can be placed on a GameBoard.
	 * 
	 * @param x the x co-ordinate of this square on the game board.
	 * @param y the y co-ordinate of this square on the game board.
	 */
	public GameSquare(int x, int y)
	{
		this.xLocation = x;
		this.yLocation = y;
		this.size = 20;
		this.wallWidth = 20;

		for (int i=0; i<wall.length; i++)
			this.wall[i] = false;

		this.setBorder(null);
		this.updateImage();
	}

	/**
	 * Changes the image displayed by this square to match the status of the
	 * wallLeft, wallRight, WallUp, WallDown and highlighted variables.
	 */	
	private void updateImage()
	{
		BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		int w = (int) (((double) size / 100.0) * wallWidth);

		// First, colour in the background in either GREY or ORANGE
		g.setColor(highlighted ? Color.ORANGE : Color.LIGHT_GRAY);
		g.fillRect(0, 0, size, size);

		// Now fill in each wall, if set.
		g.setColor(Color.BLUE);
		if (wall[WALL_LEFT])
			g.fillRect(0, 0, w, size);

		if (wall[WALL_RIGHT])
			g.fillRect(size-w, 0, size, size);
		
		if (wall[WALL_TOP])
			g.fillRect(0, 0, size, w);
		
		if (wall[WALL_BOTTOM])
			g.fillRect(0, size-w, size, size);

		g.dispose();
		this.setIcon(new ImageIcon(img));
	}

    /**
     * Determines the location of this square on the board
     * @return the x coordinate of this square.
     */
    public int getXLocation()
    {
        return xLocation;
    }

    /**
     * Determines the location of this square on the board
     * @return the x coordinate of this square.
     */
    public int getYLocation()
    {
        return yLocation;
    }

	public void setWall(int direction, boolean hasWall)
	{
		if (direction >= WALL_LEFT && direction <= WALL_BOTTOM)
		{
			this.wall[direction] = hasWall;
			this.updateImage();
		}
	}

	public boolean getWall(int direction)
	{
		return wall[direction];
	}

	public void setHighlight(boolean highlighted)
	{
		this.highlighted = highlighted;
		this.updateImage();
	}

	public boolean isHighlighted()
	{
		return highlighted;
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * 
	 */	
    public abstract void leftClicked();
    
    /**
	 * A method that is invoked when a user clicks on this square.
	 * 
	 */	
	public abstract void rightClicked();

	/**
	 * A method that is invoked when a reset() method is called on GameBoard.
	 * 
	 * @param n An unspecified value that matches that provided in the call to GameBoard reset()
	 */
	public abstract void reset(int n);
}
