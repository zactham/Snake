import java.awt.Color;
import java.awt.Graphics;

public class Square
{
	private int x;
	private int y;
	private int size;//size of the sides of the squares
	private Color c;//color of the square
	private Color mainColor = Color.GREEN;//The color that is used most
	private EnumDirections direction;

	public Square(int xCoord, int yCoord, int squareSize, Color col)
	{
		x = xCoord;
		y = yCoord;
		size = squareSize;
		c = col;
	}

	public void setDirection(EnumDirections d)
	{
		direction = d;
	}

	public EnumDirections getDirection()
	{
		return direction;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int newX)
	{
		x = newX;
	}

	public void setY(int newY)
	{
		y = newY;
	}

	public void draw(Graphics page)
	{
		page.setColor(c);
		page.fillRect(x, y, size, size);
		if (c == mainColor)
		{
			page.setColor(Color.black);
		page.drawRect(x, y, size, size);
		}
	}

	//returns true if the square collides (intersects) with another Square object that is passed in
	public boolean collide(Square otherSq)
	{
		if(this.getX() == otherSq.getX() && this.getY() == otherSq.getY())
			return true;
		return false;
	}

	public void moveTo(Square otherSq)
	{
		this.setX(otherSq.getX());
		this.setY(otherSq.getX());
	}

	public void update(int speed)
	{
		if(direction == EnumDirections.UP)
			this.setY(this.getY()-speed);
		if(direction == EnumDirections.DOWN)
			this.setY(this.getY()+speed);
		if(direction == EnumDirections.LEFT)
			this.setX(this.getX()-speed);
		if(direction == EnumDirections.RIGHT)
			this.setX(this.getX()+speed);
	}

	public boolean onGrid (int gridSize)
	{
		if (this.getX() % gridSize == 0 && this.getY() % gridSize == 0)
		{
			return true;
		}
		else
			return false;
	}
}
