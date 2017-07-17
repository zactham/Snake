import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class TitleScreen extends JApplet
{
	private Sound sound;
	
	public static boolean easy = false;
	public static boolean med = false;
	public static boolean hard = false;
	public static TitleScreen theApp;
	
	private SnakeGame board;

	private ImageIcon titleScreenImage;//image
	private JFrame start;

	//All buttons

	//Image button
	public JButton 	titleScButton;

	//Main Buttons
	private JFrame help;
	private JFrame credits;

	public void init () 
	{		
		theApp = this;
		
		sound = new Sound();
		playMusic();


		//Adds the image and creates a button out of it
		titleScreenImage = new ImageIcon(this.getClass().getResource("TSImage.jpg"));//image	
		titleScButton = new JButton (titleScreenImage);//image button
		getContentPane().add(titleScButton);
		setSize(320,240);
		centerWindow();//centers the window

		
		titleScButton.addKeyListener(new KeyAdapter()
		
		{
			public void keyPressed(KeyEvent arg0) 
			{
				// TODO Auto-generated method stub
				int c = arg0.getKeyCode();

				if (c == KeyEvent.VK_ENTER)
				{
					
					easy = true;
					sound.stop();

					TitleScreen.theApp.launchSnakeGame();
				}
							
				if (c == KeyEvent.VK_E)
				{
					//System.out.println("Easy");

					easy = true;
					sound.stop();

					TitleScreen.theApp.launchSnakeGame();
				}
				
				if (c == KeyEvent.VK_M)
				{
					//System.out.println("Medium");

					med = true;

					sound.stop();

					TitleScreen.theApp.launchSnakeGame();
				}
				
				if (c == KeyEvent.VK_H)
				{
					//System.out.println("Hard");

					hard = true;

					sound.stop();

					theApp.launchSnakeGame();
				}
				
				if (c == KeyEvent.VK_S) 
				{
					Sound.toggle(sound);
				}
			}
		});
		JOptionPane.showMessageDialog(start, "Press 'E' for Easy, 'M' for Medium, 'H' for Hard");

		//Based on where they click in easy medium or hard something happens
		titleScButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				//Music Toggle
				if (e.getX() > 0 && e.getX() < 27 && e.getY() > 745 && e.getY() < 769)
				{

					//System.out.println("Music toggle");
					if (sound.isPlaying())
					{
						sound.stop();
					}
					else
					{
						sound.resume();
					}

				}
				//Help
				if (e.getX() > 806 && e.getX() < 830 && e.getY() > 752 && e.getY() < 775)
				{

					//	System.out.println("Help");
					JOptionPane.showMessageDialog(help,
							"Use the 9 num pad on the right side of the keyboard to whack the moles.");


				}

				//Credits
				if (e.getX() > 770 && e.getX() < 830 && e.getY() > 1 && e.getY() < 14)
				{

					//	System.out.println("Credits");
					JOptionPane.showMessageDialog(help,
							"Created by Zac Thamer and Mustafa Thamer");


				}

			}

			public void mouseEntered(MouseEvent arg0) {

			}

			public void mouseExited(MouseEvent arg0) {

			}

			public void mousePressed(MouseEvent arg0) {

			}

			public void mouseReleased(MouseEvent arg0) {

			}
		});
	}


	public void launchSnakeGame()
	{
		hideWindow();
		board = new SnakeGame();
		board.init();
	}

	public void playMusic()
	{
		sound.play("TitleScreenMusic.wav");
	}


	public void hideWindow()
	{
		Container c = getParent();
		while (c.getParent()!=null) 
			c = c.getParent();
		c.setVisible(false);		
	}
	
	//Centers the window
	public void centerWindow()
	{
		//gets top level window
		Window window;
		Container c = getParent();
		while (c.getParent()!=null) 
			c = c.getParent();

		// center window
		if (c instanceof Window)//if it is the top window...
		{
			//centers it
			window = (Window)c;
			window.pack();
			window.setLocationRelativeTo(null);					
		}
	}

}

