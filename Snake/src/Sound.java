import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound
{

	public static Clip audioClip;


	public static void play(String audioFilePath)
	{
		try
		{
			File audioFile = new File(audioFilePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();
		}

		catch (Exception err)
		{
			System.out.println("1. " + err);
		}
	}


	// app entry point
	public static void main(String[] args) throws InterruptedException
	{
		for (int i = 1; i >0; i++ )
		{
			
			
			//play("TitleScreenMusic.wav");

			
			
			
			
			
			
			// sleep for secs while sound plays
			Thread.sleep(1000 * 29);

			//Ingame: 4min 5 sec so 245 sec
			//TitleScreen: 29 sec

			// close sound
			audioClip.close();
		}
	}

}

