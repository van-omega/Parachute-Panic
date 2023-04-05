package model;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Man implements Runnable{

	public int x, destinationX = 0;
	public double dy = 0.9, y, destinationY = 0;
	public ImageIcon manIcon, manChuteIcon;
	public Image currentImg;
	public boolean clicked = false, running = true;
	public Thread t1;
	
	public Man( int x, double y ) {
		setX( x );
		setY( y );
		loadImages();
		currentImg = manIcon.getImage();
	}
	
	public void loadImages(){
		manIcon = new ImageIcon( "Images/inGame/man.png" );
		manChuteIcon = new ImageIcon( "Images/inGame/manChute.png" );
	}
	
	public void move(){
		y+=dy;
	}
	
	public void setX( int x ){
		this.x = x;
	}
	
	public void setY( double y ){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setImage( ImageIcon icon ){
		this.currentImg = icon.getImage();
	}
	
	public Image getImage(){
		return currentImg;
	}
	
	public Rectangle getBounds(){
		return new Rectangle( x, (int)y, currentImg.getWidth(null), currentImg.getHeight( null ) );
	}
	
	public Rectangle getOriginalBounds(){
		return new Rectangle( x + 22, (int)y + 65, manIcon.getImage().getWidth(null), manIcon.getImage().getHeight(null));
	}

	@Override
	public void run() {
		for( int i = 0; i < 30; i++ ){
			if(x==-1)
				x++;
			else if( x+currentImg.getWidth(null) == 800 )
				x--;
			x+=destinationX;
			if( i % 3 == 0 )
				y+=destinationY;
			delay(20);
		}
		
	}
	
	public void kill(){
		running = false;
	}
	
	public void threadStart(){
		running = true;
		t1 = new Thread(this);
		t1.start();
	}
	
	public void delay(long time) {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		timeDiff = System.currentTimeMillis() - beforeTime;
		sleep = time - timeDiff;
		if (sleep < 0)
			sleep = 2;
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
		beforeTime = System.currentTimeMillis();
	}
}
