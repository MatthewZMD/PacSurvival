/* [Screens.java]
 * A program which stores all JPanels needed to make the game work
 * @author Jim
 */

//Import tools from java library
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Screens{

	//Declare Variables
	private static JButton startButton;
	private static JPanel contentPane;
	private static JFrame window;
	private static String playerName;
	private static JTextField name; 
	
	//Below starts the methods (screens)
	//Main method
	public static void main(String[] args){
		window = new JFrame("Survival");
		new Screens().begin();
	}
	
	/***************PART A: Methods of different window screens *********/
	
	/**begin()
	 * This method  is for the main intro screen
	 **/
	public static void begin(){
		
		//Set Default Font
		setUIFont(new javax.swing.plaf.FontUIResource("Courier",Font.CENTER_BASELINE,15));
		
		//Set up the frame work
		contentPane = new JPanel(new GridLayout(2,2));
		
		//Create items
		startButton = new JButton("Start");
		startButton.setFont(new Font("Courier", Font.CENTER_BASELINE, 25));
		startButton.addActionListener(new startListener());
		startButton.setBackground(Color.yellow);
		JLabel filler = new JLabel("                        ");
		filler.setFont(new Font(("Courier"), Font.BOLD, 80));
		JPanel startButtonPanel = new JPanel(new FlowLayout());
		startButtonPanel.add(filler);
		startButtonPanel.add(startButton);
		startButtonPanel.setBackground(Color.red);
		startButton.setBounds(1, 2, 20, 10);
		JPanel settingButtonPanel = new JPanel(new FlowLayout());
		JLabel instruction = new JLabel("THE BACKSTORY:");
		instruction.setFont(new Font("Courier", Font.BOLD, 16));
		settingButtonPanel.add(instruction);
		settingButtonPanel.add(new JLabel("Jan. 19th, 2038. 03:14:07"));
		settingButtonPanel.add(new JLabel("You are summoned to the world."));
		settingButtonPanel.add(new JLabel("Walkers are everywhere."));
		settingButtonPanel.add(new JLabel("Eat the Fruit of Strength"));
		settingButtonPanel.add(new JLabel("to kill the Walkers."));
		settingButtonPanel.add(new JLabel("Find an exit for us to"));
		settingButtonPanel.add(new JLabel("bring peace to humanity."));
		settingButtonPanel.add(new JLabel("We've great expectations for you."));
		settingButtonPanel.add(new JLabel("Good luck!"));
		settingButtonPanel.setBackground(Color.yellow);
		JLabel title = new JLabel("  SURVIVAL");
		title.setFont(new Font("Courier", Font.BOLD, 40));
		
		//Add items to JPanel
		contentPane.add(title);
		contentPane.add(startButtonPanel);
		contentPane.add(settingButtonPanel);
		contentPane.add(new JLabel(new ImageIcon("backGround.png")));
		
		//Final settings 
		ImageIcon icon = new ImageIcon("gameIcon.png");
		window.setIconImage(icon.getImage());
		window.setSize(600,600);
		window.setContentPane(contentPane);
		window.getContentPane().setBackground(Color.cyan);
	    window.setVisible(true);
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	}
	
	/**setup()
	 * This method  is for the player information setup screen
	 **/
	public static void setup(){
		
		//Set up the frame work
		window.remove(contentPane);
		contentPane = new JPanel();
		contentPane = new JPanel(new GridLayout(3, 1));
		
		//Create items
		startButton = new JButton("BEGIN");
		startButton.setBackground(Color.CYAN);
		startButton.setFont(new Font("Courier", Font.CENTER_BASELINE, 16));
		startButton.addActionListener(new gameListener());
		JLabel title = new JLabel("Insert your name to begin: ");
		title.setFont(new Font("Courier", Font.CENTER_BASELINE, 12));
		name = new JTextField(100);
		
		//Add items to JPanel
		contentPane.add(title);
		contentPane.add(name);
		contentPane.add(startButton);
		
		//Final settings 
		window.setContentPane(contentPane);
		window.setSize(300,200);
		window.getContentPane().setBackground(Color.cyan);
		window.setVisible(true);
	}
	
	/**gameScreen()
	 * This method  is for constructing the game playing screen
	 **/
	public static void gameScreen(){
		
		//Get the name of the player
		playerName = name.getText();
		//Temporarily open a new game window, run the game.
		window.setVisible(false);
		new Main();
		
	}
	
	/**editGameScreen()
	 * This method  is for reconstructing the game playing screen after the user lost
	 **/
	public static void endGameScreen(){

		//Reset the frame work
		window.remove(contentPane);
		contentPane = new JPanel(new BorderLayout());

		//Create items
		ImageIcon image = new ImageIcon("backGround.jpg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		JButton endButton = new JButton("Check your Score");
		endButton.addActionListener(new endListener());

		//Add items to JPanel

		JPanel endGamePanel = new JPanel(new GridLayout(1, 4));
		endGamePanel.add(new JLabel("Game Over"));
		endGamePanel.add(new JLabel("Player has killed this many walkers"));
		endGamePanel.add(new JLabel("Player has survived for this amount of time."));

		contentPane.add(endGamePanel, BorderLayout.CENTER);
		contentPane.add(endButton, BorderLayout.SOUTH);

		//Final settings
		window.setSize(600, 600);
		window.setContentPane(contentPane);
		window.getContentPane().setBackground(Color.cyan);
		window.setVisible(true);

	}
	
	/**endScreen()
	 * This method  is for displaying leaderboard + player score.
	 **/
	public static void endScreen(){
		
		//Set up the frame work
		window.remove(contentPane);
		contentPane = new JPanel(new BorderLayout());
	
		//Set Default Font
		setUIFont(new javax.swing.plaf.FontUIResource("Courier",Font.CENTER_BASELINE,16));
		
		//Create items
		Leaderboard test = new Leaderboard(playerName, 5);
		ArrayList<Player> players = test.sortAndGet();
		JButton returnButton = new JButton("Return to Start Menu");
		returnButton.setBackground(Color.cyan);
		returnButton.addActionListener(new returnListener());
		JLabel title = new JLabel("You have made a score of "+players.get(players.size()-1).getScore());
		title.setFont(new Font("Courier", Font.CENTER_BASELINE, 19));
		
		//Add items to sub jpanel
		JPanel board = new JPanel(new GridLayout(players.size()+1, 1));
		board.add(new JLabel("Leaderboard: "));
		for(int i = 0; i < players.size(); i++){
			board.add(new JLabel((i+1)+"."+players.get(i).getName()+": "+players.get(i).getScore()));
		}
		
		//Add items to JPanel
		contentPane.add(title, BorderLayout.NORTH);
		contentPane.add(board, BorderLayout.CENTER);
		contentPane.add(returnButton, BorderLayout.SOUTH);
		contentPane.add(new JLabel("    "), BorderLayout.WEST);
		
		//Final settings 
		window.setContentPane(contentPane);
		window.getContentPane().setBackground(Color.cyan);
	    window.setVisible(true);
		
	}
	
	/*******************PART B: ACTION LISTERNERS *************/
	
	/**
	 * startListener
	 * remakes the JFrame
	 */
	static class startListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setup();
	    }	
	}
	
	/**
	 * gameListener
	 * switches the JFrame to game frame in Main.java
	 */
	static class gameListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			gameScreen();
	    } 
	}
	
	/**
	 * endGameListener
	 * remakes the JFrame

	class endGameListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			endGameScreen();
		}
	}
	 */

	/**
	 * endListener
	 * remakes the JFrame
	 */
	static class endListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			endScreen();
		}
	}

	/**
	 * returnListener
	 * remakes the JFrame
	 */
	static class returnListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			begin();
	    } 
	}
	
	
	/******************PART C: MISCELLALIOUS METHODS**************/
	/**
	 * setUIFont
	 * set the font for java swing
	 */
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    }
}