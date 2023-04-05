package main;



import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

import frame.GameFrame;
public class Main {
	public static void main(String[] args) {
		try{
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("images/Others/FlyBoyBB.ttf")));
		}catch(Exception e){}
		new GameFrame();
	}
}
