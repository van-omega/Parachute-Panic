package controller;

import frame.GameFrame;
import game.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import music.Music;

public class GameController implements ActionListener{

	public GamePanel gamePanel;
	public GameFrame frame;
	
	public GameController( GamePanel gamePanel, GameFrame frame) {
		this.gamePanel = gamePanel;
		this.frame = frame;
		this.gamePanel.buttonListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == gamePanel.pause ){
			gamePanel.running = false;
			gamePanel.pauseLabel.setVisible(true);
			gamePanel.menu.setVisible(true);
			gamePanel.resume.setVisible(true);
			gamePanel.pause.setVisible(false);
			gamePanel.setFocusable(false);
		}
		
		else if( e.getSource() == gamePanel.resume ){
			gamePanel.running = true;
			gamePanel.pauseLabel.setVisible(false);
			gamePanel.menu.setVisible(false);
			gamePanel.resume.setVisible(false);
			gamePanel.pause.setVisible(true);
			gamePanel.setFocusable( true );
			gamePanel.setRequestFocusEnabled( true );
			gamePanel.grabFocus(); 
		}
		
		else if( e.getSource() == gamePanel.menu ){
			gamePanel.setVisible(false);
			gamePanel.restart();
			gamePanel.waveTimer.stop();
			gamePanel.sharkFinTimer.stop();
			gamePanel.spawnTime.stop();
			frame.gameMenu.setVisible(true);
			frame.music.gameMusic.stop();
			Music.loopSoundPlay(frame.music.music1);
			System.gc();
		}
		
		else if( e.getSource() == gamePanel.enter ){
			gamePanel.updateHighScore();
			gamePanel.setVisible(false);
			gamePanel.restart();
			gamePanel.waveTimer.stop();
			gamePanel.sharkFinTimer.stop();
			gamePanel.spawnTime.stop();
			frame.gameMenu.setVisible(true);
			frame.music.gameMusic.stop();
			Music.loopSoundPlay(frame.music.music1);
			System.gc();
			
		}
		
	}
	


}
