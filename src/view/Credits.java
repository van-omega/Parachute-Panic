package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import frame.GameFrame;

import model.GameButton;
import music.Music;

@SuppressWarnings("serial")
public class Credits extends JPanel implements ActionListener{

	public JLabel background;
	public GameButton x;
	public int i = -600;
	public Timer t, t2;
	public GameFrame frame;
	public Music music;
	
	public Credits(  GameFrame frame, Music music ) {
		this.frame = frame;
		this.music = music;
		setCredits();
		components();
	}
	
	public void setCredits(){
		setLayout( null );
		setVisible( false );
		setOpaque( false );
		setBounds( 0, 0, 800, -600 );
	}
	
	public void components(){
		x = new GameButton( new ImageIcon( "Images/howTo/blank.png" ), new ImageIcon( "Images/howTo/xhover.png" ), 700, 40, 82, 38, true );
		x.addActionListener( this );
		add( x );
		
		background = new JLabel( new ImageIcon( "Images/credits/background.png" ) );
		background.setBounds( 0, 0, 800, 600 );
		add( background );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == x ){
			t2 = new Timer(1, new CreditCloser() );
			t2.start();
		}
		
		this.setBounds( 0, i+=2, 800, 600 );
		if( i == 0 )
			t.stop();
		
	}
	
	public class CreditCloser implements ActionListener{

		@Override
		public void actionPerformed( ActionEvent e ) {
			frame.credits.setBounds( 0, i-=2, 800, 600 );
			if( i == -600 ){
				t2.stop();
				music.music2.stop();
				frame.gameMenu.credits.setEnabled(true);
				frame.gameMenu.start.setEnabled(true);
				frame.gameMenu.howTo.setEnabled(true);
				frame.gameMenu.exit.setEnabled(true);
				frame.gameMenu.score.setEnabled(true);
				frame.howTo.setVisible( false );
				if( !frame.gameMenu.mute )
					Music.loopSoundPlay(music.music1);
			}
				
		}
	}

}
