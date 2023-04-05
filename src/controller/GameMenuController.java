package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import music.Music;

import frame.GameFrame;

import view.GameMenu;

public class GameMenuController implements ActionListener{

	public GameMenu gameMenu;
	public GameFrame frame;
	public Music music;
	
	public GameMenuController( GameMenu gameMenu, GameFrame frame, Music music ) {
		this.frame = frame;
		this.music = music;
		this.gameMenu = gameMenu;
		this.gameMenu.buttonListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event ) {
		if( event.getSource() == gameMenu.start ){
			music.music1.stop();
			Music.loopSoundPlay( music.gameMusic );
			frame.gameMenu.setVisible( false );
			frame.gamePanel.setVisible( true );
			frame.gamePanel.setFocusable( true );
			frame.gamePanel.setRequestFocusEnabled( true );
			frame.gamePanel.grabFocus(); 
			frame.gamePanel.startTimers();
		}
		
		else if( event.getSource() == gameMenu.howTo ){
			music.music1.stop();
			frame.howTo.setVisible( true );
			disableMenuButtons();
			frame.howTo.t = new Timer( 1, frame.howTo );
			frame.howTo.t.start();
			if( !gameMenu.mute )
				Music.loopSoundPlay(music.music2);
		}
		
		else if( event.getSource() == gameMenu.score ){
			music.music1.stop();
			frame.score.setVisible(true);
			disableMenuButtons();
			frame.score.t = new Timer( 1, frame.score );
			frame.score.t.start();
			if(!gameMenu.mute)
				Music.loopSoundPlay(music.music2);
		}
		
		else if( event.getSource() == gameMenu.credits ){
			music.music1.stop();
			frame.credits.setVisible(true);
			disableMenuButtons();
			frame.credits.t = new Timer( 1, frame.credits );
			frame.credits.t.start();
			if( !gameMenu.mute )
				Music.loopSoundPlay(music.music2);
		}
		
		else if( event.getSource() == gameMenu.audioOn ){
			gameMenu.audioOn.setVisible( false );
			gameMenu.audioOff.setVisible( true );
			frame.music.music1.stop();
			gameMenu.mute = true;
		}
		
		else if( event.getSource() == gameMenu.audioOff ){
			gameMenu.audioOff.setVisible( false );
			gameMenu.audioOn.setVisible( true );
			Music.loopSoundPlay(frame.music.music1);
			gameMenu.mute = false;
		}
		
		else if( event.getSource() == gameMenu.exit )
			System.exit(0);
	}
	
	public void disableMenuButtons(){
		frame.gameMenu.credits.setEnabled(false);
		frame.gameMenu.start.setEnabled(false);
		frame.gameMenu.howTo.setEnabled(false);
		frame.gameMenu.exit.setEnabled(false);
		frame.gameMenu.score.setEnabled(false);
	}

}
