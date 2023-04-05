package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import frame.GameFrame;

import model.GameButton;
import music.Music;

@SuppressWarnings("serial")
public class Score extends JPanel implements ActionListener{
	
	public JLabel background, scoreLbl[];
	public GameButton x;
	public int i = -600;
	public Timer t, t2;
	public GameFrame frame;
	public Music music;
	
	public Score( GameFrame frame, Music music ) {
		this.frame = frame;
		this.music = music;
		setScore();
		components();
	}
	
	public void setScore(){
		setLayout(null);
		setVisible(false);
		setOpaque(false);
		setBounds(0, 0, 800, 600);
	}
	
	public void components(){
		scoreLbl = new JLabel[5];
		for( int i = 0; i < 5; i++ ){
			scoreLbl[i] = new JLabel();
			scoreLbl[i].setFont(new Font("Flyboy BB", Font.PLAIN, 36 ));
			scoreLbl[i].setForeground(Color.BLUE);
			scoreLbl[i].setBounds( 250, 165 + 57 * i, 400, 100 );
			add(scoreLbl[i]);
		}
		
		x = new GameButton( new ImageIcon( "Images/howTo/blank.png" ), new ImageIcon( "Images/howTo/xhover.png" ), 700, 40, 82, 38, true );
		x.addActionListener( this );
		add( x );
		
		background = new JLabel( new ImageIcon( "Images/others/score.png" ) );
		background.setBounds( 0, 0, 800, 600 );
		add( background );
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == x ){
			t2 = new Timer(1, new ScoreCloser() );
			t2.start();
		}
		
		this.setBounds( 0, i+=2, 800, 600 );
		if( i == 0 ){
			showScores();
			t.stop();
		}
		
	}
	
	public class ScoreCloser implements ActionListener{

		@Override
		public void actionPerformed( ActionEvent e ) {
			frame.score.setBounds( 0, i-=2, 800, 600 );
			if( i == -600 ){
				t2.stop();
				music.music2.stop();
				frame.gameMenu.credits.setEnabled(true);
				frame.gameMenu.start.setEnabled(true);
				frame.gameMenu.howTo.setEnabled(true);
				frame.gameMenu.exit.setEnabled(true);
				frame.gameMenu.score.setEnabled(true);
				frame.score.setVisible( false );
				if( !frame.gameMenu.mute )
					Music.loopSoundPlay(music.music1);
			}
				
		}
		
	}
	
	private void showScores(){
		try{
			File file = new File("images/Others/highscore.txt");
			FileReader read = new FileReader(file);
			BufferedReader in = new BufferedReader(read);
			for(int i = 0; i < 5; i++){
				scoreLbl[i].setText(in.readLine());
			}
			in.close();
		}catch(Exception e){
			System.err.print(e.getLocalizedMessage());
		}
	}

}
