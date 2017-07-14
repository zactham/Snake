import java.awt.Color;
import java.awt.Graphics;

public class Square
{
	private int x;
	private int y;
	private int size;//size of the sides of the squares
	private Color c;//color of the square
	private int direction;

	public Square(int xCoord, int yCoord, int squareSize, Color col)
	{
		x = xCoord;
		y = yCoord;
		size = squareSize;
		c = col;
	}

	public void setDirection(int d)
	{
		direction = d;
	}

	public int getDirection()
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
	}

	//returns true if the square collides (intersects) with another Square object that is passed in
	public boolean collide(Square otherSq)
	{
		return true;
	}

	public void moveTo(Square otherSq)
	{
		this.setX(otherSq.getX());
		this.setY(otherSq.getX());
	}

	public void update(int speed)
	{
		if(direction == 1)
			this.setY(this.getY()-speed);
		if(direction == 2)
			this.setY(this.getY()+speed);
		if(direction == 3)
			this.setX(this.getX()-speed);
		if(direction == 4)
			this.setX(this.getX()+speed);
	}
}
