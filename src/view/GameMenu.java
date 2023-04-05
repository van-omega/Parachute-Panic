package view;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameMenuController;
import frame.GameFrame;

import model.GameButton;
import music.Music;

@SuppressWarnings("serial")
public class GameMenu extends JPanel{

	public GameFrame frame;
	public Music music;
	public GameButton start, howTo, score, credits, exit, audioOn, audioOff;
	public boolean mute;
	private JLabel background;
	
	public GameMenu( GameFrame frame, Music music ) {
		this.frame = frame;
		this.music = music;
		setGameMenu();
		components();
		new GameMenuController( this, frame, music );
	}
	
	public void setGameMenu(){
		setLayout(null);
		setVisible( false );
		setBounds( 0, 0, 800, 600 );
	}
	
	public void components(){
		
		audioOn = new GameButton( new ImageIcon( "Images/menu/audioOn.png" ), new ImageIcon( "Images/menu/audioOnHover.png" ), 40, 530, 53, 48, true );
		add( audioOn );
		
		audioOff = new GameButton( new ImageIcon( "Images/menu/mute.png" ), new ImageIcon( "Images/menu/muteHover.png" ), 40, 530, 53, 48, false );
		add( audioOff );
		
		start = new GameButton( new ImageIcon( "Images/menu/blank.png" ), new ImageIcon( "Images/menu/startHover.png" ), 251, 235, 312, 54, true );
		add( start );
		
		howTo = new GameButton( new ImageIcon( "Images/menu/blank.png" ), new ImageIcon( "Images/menu/howHover.png" ), 251, 293, 312, 54, true );
		add( howTo );
		
		score = new GameButton( new ImageIcon( "Images/menu/blank.png" ), new ImageIcon( "Images/menu/scoreHover.png" ), 251, 351, 312, 54, true );
		add( score );
		
		credits = new GameButton( new ImageIcon( "Images/menu/blank.png" ), new ImageIcon( "Images/menu/creditsHover.png" ), 251, 409, 312, 54, true  );
		add( credits );
		
		exit = new GameButton( new ImageIcon( "Images/menu/blank.png" ), new ImageIcon( "Images/menu/exitHover.png" ), 251, 467, 312, 54, true );
		add( exit );
		
		
		background = new JLabel( new ImageIcon( "Images/menu/background.png" ) );
		background.setBounds( 0, 0, 800, 600 );
		add( background );
	}
	
	public void buttonListener( ActionListener listener ){
		audioOn.addActionListener( listener );
		audioOff.addActionListener( listener );
		start.addActionListener( listener );
		howTo.addActionListener( listener );
		score.addActionListener( listener );
		credits.addActionListener( listener );
		exit.addActionListener( listener );
	}

}
