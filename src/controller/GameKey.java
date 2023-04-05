package controller;

import game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKey implements KeyListener{

	public GamePanel gamePanel;
	Thread thread;
	
	public GameKey( GamePanel gamePanel ) {
		this.gamePanel = gamePanel;
		thread = new Thread( new boatMoveThread() );
		thread.start();
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		if( key == KeyEvent.VK_RIGHT ){
			gamePanel.boat.isRight = true;
		}
		
		else if( key == KeyEvent.VK_LEFT ){
			gamePanel.boat.isLeft = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		
		if( key == KeyEvent.VK_RIGHT ){
			gamePanel.boat.isRight = false;
		}
		
		else if( key == KeyEvent.VK_LEFT ){
			gamePanel.boat.isLeft = false;
		}
	 	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class boatMoveThread implements Runnable{

		@Override
		public void run() {
			while( true ){
				if( gamePanel.boat.isRight)
					gamePanel.boat.moveRight();
				else if( gamePanel.boat.isLeft )
					gamePanel.boat.moveLeft();
				gamePanel.delay(15);
			}
			
		}
		
	}

}
