package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import music.Music;

import frame.GameFrame;

@SuppressWarnings("serial")
public class SplashScreen extends JPanel implements Runnable{

	private JLabel splash;
	public GameFrame frame;
	public Music music;
	
	public SplashScreen( GameFrame frame, Music music ) {
		this.frame = frame;
		this.music = music;
		setSplashScreen();
		splash = new JLabel( new ImageIcon( "Images/others/splash.gif" ) );
		splash.setBounds( 0, 0, 800, 600 );
		add( splash );
	}
	
	public void setSplashScreen(){
		setLayout( null );
		setVisible( true );
		setBounds( 0, 0, 800, 600 );
		new Thread( this ).start();
	}

	@Override
	public void run() {
		int time = 0;
		while( time < 11 ){
			time++;
			try{
				Thread.sleep(500);
			}catch(InterruptedException e) { 
				System.out.println("interrupted"); 
			 };
		}
		System.out.println("out");
		frame.gameMenu.setVisible( true );
		this.setVisible( false );
		Music.loopSoundPlay(music.music1);
	}

}
