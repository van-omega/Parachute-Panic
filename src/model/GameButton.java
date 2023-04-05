package model;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class GameButton extends JButton{

	public GameButton( ImageIcon icon , ImageIcon hover , int x, int y, int width, int height, boolean show ) {
		super();
		setIcon( icon );
		setRolloverIcon( hover );
		setBounds( x, y, width, height );
		setFocusable( false );
		setOpaque( false );
		setContentAreaFilled( false );
		setBorderPainted( false );
		setVisible(show);
	}

}