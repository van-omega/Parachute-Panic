package music;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

	public Clip music1, music2, gameMusic;
	
	public Music() {
		music1 = createClip( "track4" );
		music2 = createClip( "track5" );
		gameMusic = createClip( "track1" );
	}
	
	static File file;
	static AudioInputStream audioIn;
	
	static Clip createClip(String sound){
		Clip clip;
		try{
			file = new File("Audio/"+sound+".wav");
			audioIn = AudioSystem.getAudioInputStream(file);
			
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			return clip;
		}catch(Exception e){
			System.err.println("Can't read: "+sound);
		}
		return null;
	}
	
	static boolean soundsIsOn = true;
	public static void normalSoundPlay(Clip sound){
		if(soundsIsOn){
			sound.stop();
			sound.setFramePosition(0);
			sound.start();
		}
	}
	
	public static void loopSoundPlay(Clip music){
		if(soundsIsOn){
			music.stop();
			music.setFramePosition(0);
			music.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

}
