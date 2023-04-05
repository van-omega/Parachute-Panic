package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.GameController;
import controller.GameKey;
import frame.GameFrame;

import model.Boat;
import model.GameButton;
import model.Man;
import model.Tree;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{

	public Timer spawnTime, waveTimer, sharkFinTimer; 		// timer for animations
	public ImageIcon background, shark, sharkFin, life, windIcon, heartUp;
	public ImageIcon[] waves = new ImageIcon[4];
	public double wavesX1 = 0, wavesX2 = 0, wavesX3 = 0, wavesX4 = 0;
	public int sharkFinX = 0, sharkY = 600, plyrLives = 5, initX, initY, finalX, finalY, rotation = 0, level = 1, time = 5, pumasok = 0, score = 0, temp;
	public boolean drawWind = false, running = true;
	public Shape windRect;
	public Stack<ImageIcon> livesStack = new Stack<ImageIcon>(); // for lives
	public Queue<ImageIcon> sharkQueue = new LinkedList<ImageIcon>();   // for x and y of powerUps
	public LinkedList<LinkedList<Man>> manList = new LinkedList<LinkedList<Man>>(); // 2D linkedList of parachuters
	public Tree<Integer> tree;	// tree of integers used to showScores
	public Boat boat;
	public Man man;
	public GameKey gameKey;
	public GameButton pause, resume, menu, enter;
	public JLabel pauseLabel, scoreLabel, gameOver;
	public GameFrame frame;
	AffineTransform at;
	
	Random random = new Random();
	
	public GamePanel( GameFrame frame ) {
		super();
		this.frame = frame;
		setGamePanel();
		components();
		new GameController( this, frame );
	}
	
	public void setGamePanel(){
		setLayout( null );
		setBounds( 0, 0, 800, 600 );
		setOpaque( true );
		setDoubleBuffered( true );
		setFocusable( true );
		setVisible( false );
	}
	
	public void restart(){
		score = 0; level = 1; time = 0; wavesX1 = 0; wavesX2 = 0; wavesX3 = 0; wavesX4 = 0; sharkFinX = 0;
		running = true;
		boat = new Boat( 305, 515 );
		manList.clear();
		livesStack();
		pause.setVisible(true);
		resume.setVisible(false);
		menu.setVisible(false);
		pauseLabel.setVisible(false);
		enter.setVisible(false);
		gameOver.setVisible(false);
		
	}
	
	public void buttonListener( ActionListener listener ){
		pause.addActionListener(listener);
		menu.addActionListener(listener);
		resume.addActionListener(listener);
		enter.addActionListener(listener);
	}
	
	public void gameOver(){
		
		enter = new GameButton( new ImageIcon( "Images/inGame/menuBlank.png" ), new ImageIcon( "Images/inGame/menuHover.png" ), 336, 361, 129, 38, false );
		add( enter );
		
		gameOver = new JLabel( new ImageIcon( "Images/others/gameOver2.png" ) );
		gameOver.setBounds( 0, 0, 800, 600 );
		gameOver.setVisible(false);
		add( gameOver );
	}
	
	public void pause(){
		
		resume = new GameButton( new ImageIcon( "Images/inGame/resumeBlank.png" ), new ImageIcon( "Images/inGame/resumeHover.png" ), 417, 326, 177, 38, false );
		add(resume);
		
		menu = new GameButton( new ImageIcon( "Images/inGame/menuBlank.png" ), new ImageIcon( "Images/inGame/menuHover.png" ), 208, 326, 129, 38, false );
		add(menu);
		
		pauseLabel = new JLabel( new ImageIcon( "Images/inGame/pauseLabel.png" ) );
		pauseLabel.setBounds(0, 0, 800, 600);
		pauseLabel.setVisible(false);
		add(pauseLabel);
		
		scoreLabel = new JLabel( "0" );
		scoreLabel.setBounds( 100, 60, 200, 30);
		scoreLabel.setFont(new Font("Flyboy BB", Font.PLAIN, 30));
		scoreLabel.setForeground(Color.WHITE);
		add(scoreLabel);
		
		pause = new GameButton( new ImageIcon( "Images/inGame/pause.png" ), new ImageIcon( "Images/ingame/pauseHover.png" ), 735, 20, 49, 51, true );
		add( pause );
	}
	
	public void icons(){
		background = new ImageIcon( "Images/inGame/background.png" );
		shark = new ImageIcon( "Images/inGame/shark.png" );
		sharkFin = new ImageIcon( "Images/inGame/sharkfin.png" );
		life = new ImageIcon( "Images/inGame/life.png" );
		windIcon = new ImageIcon( "Images/others/wind.png" );
		heartUp = new ImageIcon( "Images/inGame/heart.png" );
	}
	
	public void livesStack(){
		livesStack.clear();
		for( int i = 0; i < 5; i++ )							
			livesStack.push(life);					// pushing "life of player" to stack
		for( int i = 0; i < 4; i++ )
			waves[i] = new ImageIcon( "Images/inGame/wave" + i + ".png" );
	}
	
	public void sharkQueue(){
		enqueue( sharkQueue, sharkFin );
	}
	
	public <E> void enqueue(Queue<E> q, E item){		
		// generic enqueue method for Queues
		q.add(item);
	}
	
	public <E> E dequeue( Queue<E> q ){				
		// generic dequeue method for Queues
		return q.remove();
	}
	
	public void models(){
		boat = new Boat( 305, 515 );
		man = new Man( 300, 30 );
	}
	
	public void listeners(){
		gameKey = new GameKey( this );
		addKeyListener( gameKey );
		MouseHandler handler = new MouseHandler();
		addMouseListener( handler );
	}
	
	public void components(){
		gameOver();
		pause();
		icons();
		livesStack();
		sharkQueue();
		models(); 
		listeners();
	}
	
	public void startTimers(){
		 waveTimer = new Timer(15, new WaveTimer() );
		 waveTimer.start();
		 
		 sharkFinTimer = new Timer( 15, new SharkFinTimer() );
		 sharkFinTimer.start();
		 
		 spawnTime = new Timer( 15, new ManSpawn() );
		 spawnTime.start();
	}
	
	@Override
	public void paintComponent( Graphics g ){
        Graphics2D g2d = (Graphics2D) g.create();
		g.drawImage( background.getImage() , 0, 0, null );   					  // background
		
		Iterator<ImageIcon> it = livesStack.iterator();
		for(int i = 0; it.hasNext(); i++ ){
			g.drawImage(((ImageIcon) it.next()).getImage(), 90 + 25 * i, 35, null );
		}
		
		g.drawImage( waves[3].getImage(), (int)wavesX4, 380, null );  			 // 4th wave( highest wave )
		if( wavesX4 > 0 )
			g.drawImage( waves[3].getImage(), (int)wavesX4-1000, 380, null );
		
		if( !sharkQueue.isEmpty() )
			g.drawImage( sharkQueue.peek().getImage(), sharkFinX, 410, null );	 // shark fin
		
		g.drawImage( waves[2].getImage(), (int)wavesX3, 420, null );			 // 3rd wave
		if( wavesX3 < 0 )
			g.drawImage( waves[2].getImage(), 1000+(int)wavesX3, 420, null );
		
		if( !sharkQueue.isEmpty() )
			g.drawImage( sharkQueue.peek().getImage(), sharkFinX, 465, null );	 // shark fin
		
		g.drawImage( waves[1].getImage(), (int)wavesX2, 475, null );			 // 2nd wave
		if( wavesX2 > 0 )
			g.drawImage( waves[1].getImage(), (int)wavesX2 - 1000, 475, null );
		
		g.drawImage( boat.getImage(), boat.getX(), boat.getY(), null );			 // boat
		for( int i = 0; i < manList.size(); i++ )
			for( int j = 0; j < manList.get(i).size(); j++ )
				g.drawImage( manList.get(i).get(j).getImage(), manList.get(i).get(j).getX(), (int)manList.get(i).get(j).getY(), null );			 // man
		
		g.drawImage( shark.getImage(), 200, sharkY, null );						 // shark 
		g.drawImage( waves[0].getImage(), (int)wavesX1, 530, null );			 // 1st wave
		if( wavesX1 < 0 )
			g.drawImage( waves[0].getImage(), 1000+(int)wavesX1, 530, null );
		
		if( drawWind ){					// drawing the wind direction
			g2d.drawImage( windIcon.getImage(), at, this );
			g2d.transform(at);
			g2d.setTransform(at);
		}
	}
	
	public class ManSpawn implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if( running){
				if( manList.size() == 0 ){
					temp = time;
					createMan();
					if( !( time % 10 == 0 || time % 5 == 0 ) )
						level++;
				}
				if( time - temp > 50 && time >= 5 ){
					for( int i = 0; i < manList.size(); i++ ){
						for( int j = 0; j < manList.get(i).size(); j++ ){
							manList.get(i).get(j).move();
							if(manList.get(i).get(j).getY() > 560 ){
								manList.get(i).remove(j);
								livesStack.pop();
								if(livesStack.isEmpty() )
									break;
							}
						}
						if(manList.get(i).size()==0)
							manList.remove(i);
					}
				}
				
				if( livesStack.isEmpty() ){
					running = false;
					setFocusable(false);
					enter.setVisible(true);
					gameOver.setVisible(true);
				}
				
				score+= manIsSaved()? 1 : 0;
				scoreLabel.setText( score + "" );
				time++;
				repaint();
			}
		}
		
	}
	
	public void createMan(){
		//generate man
		for( int i = 0; i < 1 + random.nextInt(level); i++ ){
			manList.add(new LinkedList<Man>() );
			for( int j = 0; j < 1 + random.nextInt(level); j++ ){
				int xpos = 20 * ( 1 + random.nextInt(38) );
				manList.get(i).add( new Man( xpos, -50 * (j+i+1) * 2 ) );
			}
		}
	}
	
	public boolean manIsSaved(){
		//man land to a boat
		for( int i = 0; i < manList.size(); i++ ){
			for( int j = 0; j < manList.get(i).size(); j++ ){
				if( manList.get(i).get(j).getOriginalBounds().intersects(boat.getBounds()) && manList.get(i).get(j).clicked ){
					manList.get(i).remove(j);
					return true;
				}
			}
		}
		return false;
	}
	
	public class WaveTimer implements ActionListener{
		//wave animation
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(running){
				wavesX1 = wavesX1 <= -998 ? 0 : wavesX1-0.9;
				wavesX2 = wavesX2 >= 998 ? 0 : wavesX2+1.3;
				wavesX3 = wavesX3 <= -998 ? 0 : wavesX3-1.1;
				wavesX4 = wavesX4 >= 998 ? 0 : wavesX4+1;
			}
			
		}
	}
	
	public class SharkFinTimer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(running){
				sharkFinX++;
				if(sharkFinX == 800 ){
					enqueue( sharkQueue, dequeue(sharkQueue));
					sharkFinX = -40;
					
				}
			}
			
		}
		
	}
	
	public void updateHighScore(){
		try {
			ArrayList<Integer> scores = new ArrayList<Integer>();					// append latest score
			File file2 = new File("images/Others/highscore.txt");
			FileWriter write = new FileWriter(file2, true);
			BufferedWriter out = new BufferedWriter(write);
			out.append(score+ "");
			out.newLine();
			out.close();
			
			File file = new File("Images/others/highscore.txt");					// read all the scores
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null ) {
				scores.add( Integer.valueOf(line) );
			}
			fileReader.close();
			
			System.out.println("scores.size = " + scores.size());					// putting the scores into the tree
			tree = new Tree<Integer>();
			for( int i = 0; i < scores.size(); i++ ){
				tree.insertNode(scores.get(i));
			}
			
			tree.inOrderTraversal();												// using inorder traversal to sort the scores
			
			File file3 = new File("Images/others/highscore.txt");					// creating new textFile
			FileWriter write2 = new FileWriter(file3);
			BufferedWriter out2 = new BufferedWriter(write2);
			
			for( int i = tree.intTree.size()-1; i >= tree.intTree.size()-6; i-- ){	// rewriting the scores in the textFile
				out2.write(tree.intTree.get(i) + "");
				out2.newLine();
			}
			out2.close();
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	private class MouseHandler implements MouseListener, MouseMotionListener{
		int dx = 0, dy = 0;
		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			//Used; to open the parachute
			int pointX = e.getX(), pointY = e.getY();
			
			for( int i = 0; i < manList.size() ; i++ )
				for( int j = 0; j < manList.get(i).size(); j++ ){
					if( !manList.get(i).get(j).clicked && pointX >= manList.get(i).get(j).getX() && pointX <= manList.get(i).get(j).getX() + manList.get(i).get(j).getImage().getWidth(null) && pointY >= manList.get(i).get(j).getY() && pointY <= manList.get(i).get(j).getY() + manList.get(i).get(j).getImage().getHeight(null) ){
						manList.get(i).get(j).setImage(manList.get(i).get(j).manChuteIcon);
						manList.get(i).get(j).setX(manList.get(i).get(j).getX() - 22);
						manList.get(i).get(j).setY(manList.get(i).get(j).getY() - 80);
						manList.get(i).get(j).dy = 0.6;
						manList.get(i).get(j).clicked = true;
					}
				}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			//Used; to initialize wind position
			initX = e.getX();
			initY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//used; to start wind blow // for the direction of the wind // identifying if wind blew parachute
			int temp = 0, moveX = 0;
			double moveY = 0;
			finalX = e.getX();
			finalY = e.getY();
			dx = finalX - initX;
			dy = finalY - initY;
			at = new AffineTransform();
			at.translate( initX, initY );
			windRect = new Rectangle( 0, 0, 100, 30);
			
			if( Math.abs( finalX - initX ) >= 100 ){
				if( dx > 0 ){
					temp = 1; rotation = 0; moveX = 1;
				}
				else if( dx < 0 ){
					temp = 2; rotation = 180; moveX = -1;
					at.rotate( Math.toRadians(rotation));
				}
			}
			
			if( Math.abs( finalY - initY ) >= 100 ){
				
				if( dy < 0 ){
					rotation = temp == 1 ? 315 : temp == 2 ? 45 : 270;
					moveY = -0.9;
					at.rotate( Math.toRadians(rotation));
				}
				else if( dy > 0 ){
					rotation = temp == 1 ? 45 : temp == 2 ? -45 : 90;
					moveY = 0.9;
					at.rotate( Math.toRadians(rotation));
				}
			}
			windRect = at.createTransformedShape(new Rectangle(windIcon.getImage().getHeight(null), windIcon.getImage().getHeight(null))).getBounds();
			for( int i = 0; i < manList.size(); i++ ){
				for( int j = 0; j < manList.get(i).size(); j++ ){
					if( manList.get(i).get(j).clicked && windRect.intersects(manList.get(i).get(j).getBounds())){
						manList.get(i).get(j).destinationY = moveY;
						manList.get(i).get(j).destinationX = moveX;
						manList.get(i).get(j).threadStart();
					}
				}
			}
			
			if( Math.abs( finalX - initX ) >= 100 || Math.abs( finalY - initY ) >= 100 )
				new Thread(new WindThread() ).start();
			
		}
	}
	
	public class WindThread implements Runnable{
		// thread to delay draw of the wind
		@Override
		public void run() {	
			drawWind = true;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			drawWind = false;

		}
		
	}
}
