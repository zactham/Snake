import java.awt.Color;
import java.awt.Container;
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

	//the snake head's direction
	private int direction = 0, oldDirection;

	int nextPiece = 0;


	private int snakeLengthCounter = 1;

	private int gameboardSize = 400;
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

		// launch game
		JFrame frame = new JFrame("Sample Frame");

		frame.add(this);

		frame.setTitle("Snake");

		frame.setBackground(Color.WHITE);

		JOptionPane.showMessageDialog(start, "Use the arrow keys to move the snake around and eat apples");


		setAppleLocation();


		Square number1 = new Square(snakeX, snakeY, squareSize, Color.GREEN );
		snakePieces[0] = number1;






		//Sets the speed of the game for each mode
		if (TitleScreen.easy == true)
		{

		}



		if (TitleScreen.med == true)
		{

		}



		if (TitleScreen.hard == true)
		{

		}




		try

		{
			playInGameMusic();
		}

		catch (Exception err)
		{
			//System.out.println("2. " + err);
		}

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		centerWindow();
		frame.setSize(gameboardSize, gameboardSize);
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
		if (snakePieces[0].getX()<0 || snakePieces[0].getX()>gameboardSize || snakePieces[0].getY()<0 || snakePieces[0].getY()>gameboardSize )
			gameEnding();



		// only change direction when snakehead is on a multiple of squareSize
		if (direction != oldDirection && snakePieces[0].getX() % squareSize == 0 &&
				snakePieces[0].getY() % squareSize == 0)
		{
			oldDirection = direction;			
		}

		snakePieces[0].setDirection(oldDirection);

		//Controls the other pieces, starts at the tail
		for (int i = snakeLengthCounter; i>0; i--)
		{
			snakePieces[i-1].update(snakeSpeed);

			if (snakePieces[i-1].getX() % squareSize == 0 && snakePieces[i-1].getY() % squareSize == 0)
			{
				if (i>1)
				{
					if (snakePieces[i-1].getDirection()!= snakePieces[i-2].getDirection())
						snakePieces[i-1].setDirection(snakePieces[i-2].getDirection());
				}
			}


		}



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
		if(apple.getX() == snakePieces[0].getX() && apple.getY() == snakePieces[0].getY())
		{
			System.out.println("Collide");
			addSnakePiece();


		}
	}


	public void setAppleLocation()
	{
		//400-20-20 = 360
		int randomX = (int) (Math.random() *((gameboardSize-squareSize)/squareSize));
		randomX *= squareSize;
		//System.out.println(randomX);

		int randomY = (int) (Math.random() *((gameboardSize-squareSize))/squareSize);
		randomY *= squareSize;
		//System.out.println(randomY);

		apple.setX(randomX);
		apple.setY(randomY);
	}


	public void addSnakePiece()
	{

		nextPiece+=1;

		int tailDirection = snakePieces[snakeLengthCounter-1].getDirection();
		Square collidePiece = null;

		if (tailDirection == 1)//up
		{
			collidePiece = new Square(snakePieces[nextPiece-1].getX(), snakePieces[nextPiece-1].getY()+squareSize, 
					squareSize, Color.green);
		}

		if (tailDirection == 2)//down
		{

			collidePiece = new Square(snakePieces[nextPiece-1].getX(), snakePieces[nextPiece-1].getY()-squareSize, 
					squareSize, Color.green);
		}

		if (tailDirection == 3)//left
		{
			collidePiece = new Square(snakePieces[nextPiece-1].getX()+squareSize, snakePieces[nextPiece-1].getY(), 
					squareSize, Color.green);
		}

		if (tailDirection == 4)//right
		{
			collidePiece = new Square(snakePieces[nextPiece-1].getX()-squareSize, snakePieces[nextPiece-1].getY(), 
					squareSize, Color.green);
		}

		collidePiece.setDirection(tailDirection);
		snakeLengthCounter+=1;
		direction = oldDirection;
		snakePieces[nextPiece] = collidePiece;
	}

	public void drawGame(Graphics page)
	{
		apple.draw(page);

		for (int i = 0; i<snakeLengthCounter; i++)
		{
			snakePieces[i].draw(page);
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

		//Game Over Message
		JOptionPane.showMessageDialog(gameOver,
				"Game Over\n Your Score:\t " + score + "%");
		System.exit(0);

	}


	public void displayScore(Graphics page)
	{
		//Displays the Score
		page.setColor(Color.black);
		page.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
		page.drawString("SCORE: ", 75, 350);
		page.drawString(Integer.toString(score), 275, 350);
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

		updateGame();


		if (c == KeyEvent.VK_UP)//-2
		{
			direction = 1;
		}

		if (c == KeyEvent.VK_DOWN) //+2
		{	
			direction = 2;
		}

		if (c == KeyEvent.VK_LEFT)//-2
		{	
			direction = 3;
		}
		if (c == KeyEvent.VK_RIGHT)//+2
		{
			direction = 4;
		}



		//When S is pressed the music stops
		if (c == KeyEvent.VK_S) 
		{
			if (sound.isPlaying())
			{
				sound.stop();
			}
			else
			{
				sound.resume();
			}
		}
	}



	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}