import java.util.Random;

public class Driver
{
	public static void main(String[] Args)
	{
		int width = 10;
		int height = 10;

		GameBoard b = new GameBoard("Escape!", width, height, "MazeSquare");

		// Attempt to read a level number from the command line. Default to level 1.
		int level = 1;

		try{
			level = Integer.parseInt(Args[0]);
		}catch (Exception e) {}

		Random generator = new Random(level);
		int wallCount = 75 + ((int)level / 10);

		for (int i=0; i<wallCount; i++)
		{
			int x = generator.nextInt(width);
			int y = generator.nextInt(height);
			int direction = generator.nextInt(4);

			// Set the relevant square to a wall.
			GameSquare s;
			s = b.getSquareAt(x, y);
			if (s != null)
				s.setWall(direction, true);

			// Ensure the neightbouring square agrees (if applicable)
			if (direction == GameSquare.WALL_LEFT)
			{
				x--;
				direction = GameSquare.WALL_RIGHT;
			}

			else if (direction == GameSquare.WALL_RIGHT)
			{
				x++;
				direction = GameSquare.WALL_LEFT;
			}

			else if (direction == GameSquare.WALL_TOP)
			{
				y--;
				direction = GameSquare.WALL_BOTTOM;
			}

			else if (direction == GameSquare.WALL_BOTTOM)
			{
				y++;
				direction = GameSquare.WALL_TOP;
			}

			s = b.getSquareAt(x, y);
			if (s != null)
				s.setWall(direction, true);
		}

		// Ensure the outside edge of the maze has a border.
		for (int x=0; x<width;x++)
		{
			b.getSquareAt(x, 0).setWall(GameSquare.WALL_TOP, true);
			b.getSquareAt(x, height-1).setWall(GameSquare.WALL_BOTTOM, true);
		}

		for (int y=0; y<height;y++)
		{
			b.getSquareAt(0, y).setWall(GameSquare.WALL_LEFT, true);
			b.getSquareAt(width-1, y).setWall(GameSquare.WALL_RIGHT, true);
		}
	}
}
