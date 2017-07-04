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
	boolean appleinBoard = true; 

	public int direction = 0;
	

	public static int snakeLengthCounter = 1;

	public int snakeX = 200;
	public int snakeY = 200;
	public int snakeXOld = 200;
	public int snakeYOld = 200;


	public int gameboardSize = 400;
	public int squareSize = 20;

	public final int max = 100;

	Square apple = new Square(0, 0, squareSize, Color.red);
	Square[] snakePieces = new Square [max];



	private int score = 0;


	int counter = 1;
	boolean scored = false;

	int round = 0;




	private boolean soundPlaying = true;

	public boolean correct = false;


	public MyTimer timer;
	public int turnTime = 2500;

	public JFrame restart;
	public JFrame gameOver;
	public JFrame start;



	// Constructor
	public SnakeGame()
	{
		setFocusable(true);
		// Register for mouse events on the panel
		addKeyListener(this);

		score = 0;

		counter = 1;
		scored = false;

		round = 0;

		soundPlaying = true;

		correct = false;

		turnTime = 2500;
	}




	public void init() throws InterruptedException
	{

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

			turnTime = 800;

		if (TitleScreen.med == true)

			turnTime = 500;

		if (TitleScreen.hard == true)

			turnTime = 400;

		timer = new MyTimer(turnTime);

		timer.start();

		try

		{
			playMusicMain();
		}

		catch (Exception err)
		{
			//System.out.println("2. " + err);
		}

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		round = 0;

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


	public void MainLoop() // throws InterruptedException
	{
		updateGame();
		repaint();
	}


	public void updateGame()
	{
			if(direction == 1)
				snakePieces[0].setY(snakePieces[0].getY()-1);
			if(direction == 2)
				snakePieces[0].setY(snakePieces[0].getY()+1);
			if(direction == 3)
				snakePieces[0].setX(snakePieces[0].getX()-1);
			if(direction == 4)
				snakePieces[0].setX(snakePieces[0].getX()+1);
	}

	public class AL implements ActionListener
	{
		public final void actionPerformed(ActionEvent e)
		{

			if (soundPlaying)
			{
				Sound.audioClip.stop();
				soundPlaying = false;
			}
			else
			{
				Sound.audioClip.start();
				soundPlaying = true;
			}
		}
	}

	public void playInGameMusic() throws InterruptedException

	{
		Sound.play("IngameMusic.wav");

	}

	public void playMusicMain() throws InterruptedException

	{
		playInGameMusic();
	}

	public void playSoundEffect() throws InterruptedException

	{
		Sound.play("SMACK Sound Effect.wav");
	}


	public void setAppleLocation()
	{

		int randomX = (int) (Math.random() * (gameboardSize-squareSize-squareSize));
		randomX += squareSize;

		int randomY = (int) (Math.random() * (gameboardSize-squareSize-squareSize));
		randomY += squareSize;

		apple.setX(randomX);
		apple.setY(randomY);
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
		if (round==max-1)
		{
			if (soundPlaying)
			{
				Sound.audioClip.stop();
				soundPlaying = false;
			}
			else
			{
				Sound.audioClip.start();
				soundPlaying = true;
			}

			//Game Over Message
			JOptionPane.showMessageDialog(gameOver,
					"Click the X and then hit F11 to RESTART or Click the X in the top right to QUIT\n Your Percentage:\t " + score + "%");
		}
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



		//Pressing the keys 1 2 3 on the num pad on the right side of the keyboard
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
		if (c == KeyEvent.VK_S) {
			if (soundPlaying)
			{
				Sound.audioClip.stop();
				soundPlaying = false;
			}
			else
			{
				Sound.audioClip.start();
				soundPlaying = true;
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

