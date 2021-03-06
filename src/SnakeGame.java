import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.*;



public class SnakeGame extends JPanel implements KeyListener
{
	private Sound sound;

	private Color outline = Color.black;



	//the snake head's direction
	private EnumDirections direction, oldDirection;


	private int snakeLengthCounter = 1;

	private int gameboardSize = 800;
	private int squareSize = 20;

	// make initial snakeX,snakeY a multiple of squareSize
	private int snakeX = ((gameboardSize/squareSize)/2 - 1)*squareSize;
	private int snakeY = snakeX;

	// snake speed must be a even factor of squareSize
	private int snakeSpeed = squareSize/10;

	private final int max = 100;

	Square apple = new Square(0, 0, squareSize, Color.red);
	Square[] snakePieces = new Square [max];


	private int score = 0;


	private JFrame gameOver;
	private JFrame start;


	// Constructor
	public SnakeGame()
	{
		setFocusable(true);
		// Register for mouse events on the panel
		addKeyListener(this);

		score = 0;
	}




	public void init()
	{

		sound = new Sound();

		setPreferredSize(new Dimension(gameboardSize, gameboardSize));

		// launch game
		JFrame frame = new JFrame("Sample Frame");
		frame.getContentPane().add(this);
		frame.setTitle("Snake");
		frame.setBackground(Color.WHITE);

		JOptionPane.showMessageDialog(start, "Use the arrow keys to move the snake around and eat apples");


		setAppleLocation();


		Square number1 = new Square(snakeX, snakeY, squareSize, Color.GREEN );
		snakePieces[0] = number1;


		gameMode();


		playInGameMusic();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		centerWindow();
		frame.setLocationRelativeTo(TitleScreen.theApp);


		// runs the mainLoop every 15 milliseconds or 60 frames per seconds 
		ActionListener timerAction = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				MainLoop();

			}

		};


		// Frame rate, updates the frame every 15ms --- 60fps
		Timer timer = new Timer(15, timerAction);
		timer.setRepeats(true);
		timer.start();

		direction = EnumDirections.NONE;
		oldDirection = direction;
	}
	
	public void gameMode()
	{

		//Sets the speed of the game for each mode
		if (TitleScreen.easy == true)
		{
			snakeSpeed = squareSize/10;
			//speed = 2
		}



		if (TitleScreen.med == true)
		{
			snakeSpeed = squareSize/5;
			//speed = 4
		}



		if (TitleScreen.hard == true)
		{
			snakeSpeed = squareSize/4;
			//speed = 5
		}
	}


	public void MainLoop()
	{
		updateGame();
		collide();
		repaint();
	}


	public void updateGame()
	{
		//If the snake head goes outside the grid the game ends
		if (snakePieces[0].getX()<0 || snakePieces[0].getX()>=gameboardSize || snakePieces[0].getY()<0 || snakePieces[0].getY()>=gameboardSize )
			gameEnding();

		//Controls the other pieces, starts at the tail
		for (int i = snakeLengthCounter; i>0; i--)
		{
			snakePieces[i-1].update(snakeSpeed);

			if (snakePieces[i-1].onGrid(squareSize))
			{
				if (i>1)
				{
					if (snakePieces[i-1].getDirection()!= snakePieces[i-2].getDirection())
						snakePieces[i-1].setDirection(snakePieces[i-2].getDirection());
				}
			}

		}

		// only change direction when snakehead is on a multiple of squareSize
		if (direction != oldDirection && snakePieces[0].onGrid(squareSize))
		{
			oldDirection = direction;			
		}

		snakePieces[0].setDirection(oldDirection);

	}

	public void playInGameMusic()

	{
		sound.play("IngameMusic.wav");
	}

	public void playSoundEffect()
	{
		sound.play("SMACK Sound Effect.wav");
	}

	public void collide()
	{
		if(apple.collide(snakePieces[0]))
		{
			System.out.println("Collide");
			score+=3;
			addSnakePiece();
			addSnakePiece();
			addSnakePiece();
			setAppleLocation();
		}

		for (int i=1; i<snakeLengthCounter; i++)
		{
			if(snakePieces[0].collide(snakePieces[i]))
			{
				gameEnding();
			}
		}
	}


	public void setAppleLocation()
	{
		//400-20 = 380
		int randomX = (int) (Math.random() *((gameboardSize-squareSize)/squareSize));
		randomX *= squareSize;

		System.out.println(randomX);

		int randomY = (int) (Math.random() *((gameboardSize-squareSize))/squareSize);
		randomY *= squareSize;

		System.out.println(randomY);

		apple.setX(randomX);
		apple.setY(randomY);
	}


	public void addSnakePiece()
	{

		Square tail = snakePieces[snakeLengthCounter-1];

		Square collidePiece = null;

		if (tail.getDirection()== EnumDirections.UP)//up
		{
			collidePiece = new Square(snakePieces[snakeLengthCounter-1].getX(), snakePieces[snakeLengthCounter-1].getY()+squareSize, 
					squareSize, Color.green);
		}

		if (tail.getDirection()== EnumDirections.DOWN)//down
		{
			collidePiece = new Square(snakePieces[snakeLengthCounter-1].getX(), snakePieces[snakeLengthCounter-1].getY()-squareSize, 
					squareSize, Color.green);
		}

		if (tail.getDirection()== EnumDirections.LEFT)//left
		{
			collidePiece = new Square(snakePieces[snakeLengthCounter-1].getX()+squareSize, snakePieces[snakeLengthCounter-1].getY(), 
					squareSize, Color.green);
		}

		if (tail.getDirection()== EnumDirections.RIGHT)//right
		{
			collidePiece = new Square(snakePieces[snakeLengthCounter-1].getX()-squareSize, snakePieces[snakeLengthCounter-1].getY(), 
					squareSize, Color.green);
		}

		collidePiece.setDirection(tail.getDirection());
		snakeLengthCounter+=1;
		snakePieces[snakeLengthCounter-1] = collidePiece;
	}

	public void drawGame(Graphics page)
	{
		apple.draw(page,outline);

		for (int i = 0; i<snakeLengthCounter; i++)
		{
			snakePieces[i].draw(page,outline);
		}
	}

	// Centers the window
	public void centerWindow()
	{
		// gets top level window
		Window window;
		Container c = getParent();
		while (c.getParent() != null)
			c = c.getParent();

		// center window
		if (c instanceof Window)// if it is the top window...
		{
			// centers it
			window = (Window) c;
			window.pack();
			window.setLocationRelativeTo(null);
		}
	}


	public void gameEnding()
	{
		//When the game ends

		sound.stop();

		int result = JOptionPane.showConfirmDialog(this, 
				"Your Score: " + score + " - Play Again?", 
				"Game Over", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.NO_OPTION)
		{
			// no
			System.exit(0);
		}
		else
		{
			// yes, play again
			resetGame();

		}
	}

	//
	// reset the game so we can play again
	// reset direction, oldDirection, snakeLengthCounter, Score, apple location, ...
	//
	private void resetGame()
	{
		snakeX = ((gameboardSize/squareSize)/2 - 1)*squareSize;
		snakeY = snakeX;

		snakeLengthCounter = 1;


		snakePieces = new Square [max];
		// create original head piece, set snake head to original snake X,Y

		score = 0;

		
		gameMode();
		playInGameMusic();
		Square number1 = new Square(snakeX, snakeY, squareSize, Color.GREEN );
		snakePieces[0] = number1;
		setAppleLocation();
		
		direction = EnumDirections.NONE;
		oldDirection = direction;

	}

	public void displayScore(Graphics page)
	{
		//Displays the Score
		page.setColor(Color.black);
		page.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
		page.drawString("SCORE: ", (gameboardSize/2)-110, gameboardSize-50);
		page.drawString(Integer.toString(score), (gameboardSize/2)+120, gameboardSize-50);
	}

	@Override
	protected void paintComponent(Graphics page)
	{
		super.paintComponent(page);		// paint baseclass members too

		displayScore(page);
		drawGame(page);
	}

	public int getScore()
	{
		return score;
	}




	public void keyPressed(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub
		int c = arg0.getKeyCode();

		if (c == KeyEvent.VK_UP)//-2
		{
			direction = EnumDirections.UP;
		}

		if (c == KeyEvent.VK_DOWN) //+2
		{	
			direction = EnumDirections.DOWN;
		}

		if (c == KeyEvent.VK_LEFT)//-2
		{	
			direction = EnumDirections.LEFT;
		}
		if (c == KeyEvent.VK_RIGHT)//+2
		{
			direction = EnumDirections.RIGHT;
		}


		//When S is pressed the music stops
		if (c == KeyEvent.VK_S) 
		{
			Sound.toggle(sound);
		}
	}



	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}