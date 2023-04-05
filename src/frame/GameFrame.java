package frame;

import game.GamePanel;

import javax.swing.JFrame;

import music.Music;

import view.Credits;
import view.GameMenu;
import view.HowTo;
import view.Score;
import view.SplashScreen;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	public GamePanel gamePanel;
	public GameMenu gameMenu;
	public HowTo howTo;
	public Credits credits;
	public SplashScreen splashScreen;
	public Music music;
	public Score score;
	
	public GameFrame() {
		super( "Parachute Panic" );
		components();
		setGameFrame();
	}
	
	public void setGameFrame(){
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout( null );
		pack();
		setSize( 806, 628 );
		setResizable( false );
		setLocationRelativeTo( null );
		setVisible( true );
	}
	
	public void components(){
		
		music = new Music();
		
		splashScreen = new SplashScreen( this, music ); 
		this.getContentPane().add( splashScreen );
		
		howTo = new HowTo( this, music );
		this.getContentPane().add( howTo );
		
		score = new Score( this, music );
		this.getContentPane().add(score);
		
		credits = new Credits( this, music );
		this.getContentPane().add( credits );
		
		gamePanel = new GamePanel( this );
		this.getContentPane().add( gamePanel );
		
		gameMenu = new GameMenu( this, music );
		this.getContentPane().add( gameMenu );
	}
}
