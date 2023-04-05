package model;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Boat {

	private int x, y, dx = 1;
	public ImageIcon boatIcon;
	public boolean isRight = false, isLeft = false;
	
	public Boat( int x, int y ) {
		setX( x );
		setY( y );
		loadImage();
	}
	
	public void moveRight(){
		if( x+boatIcon.getIconWidth() <= 800 )
			x+=dx;
	}
	
	public void moveLeft(){
		if( x>=0)
			x-=dx;
	}
	
	public void loadImage(){
		boatIcon = new ImageIcon( "Images/inGame/boat.png" );
	}
	
	public void setX( int x ){
		this.x = x;
	}
	
	public void setY( int y ){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Image getImage(){
		return boatIcon.getImage();
	}
	
	public Rectangle getBounds(){
		return new Rectangle( x, y+9, 215, 1 );
	}

}
