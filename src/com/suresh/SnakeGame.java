package com.suresh;

import java.util.Timer;
import java.awt.Dimension;
import javax.swing.*;


public class SnakeGame {

	public final static int xPixelMaxDimension = 501;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public final static int yPixelMaxDimension = 501;

	protected static int xCurrentPixelMaxDimension = xPixelMaxDimension;  //current pixel for letting user increase and decrease screen size
	protected static int yCurrentPixelMaxDimension = yPixelMaxDimension;

	public static int xSquares;
	public static int ySquares;

	//public final static int squareSize = xCurrentPixelMaxDimension;
	public static int squareSize = 50;

	protected static Snake snake;

	protected static Kibble kibble;

	protected static Score score;

	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as SnakeGame.GAME_OVER 

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value

	protected static long clockInterval = 500; //controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1 second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	protected static boolean warpWall = false;//define var to track warp wall on off
	protected static boolean maze = false;//define var to track maze on off
	protected static GameClock clockTick = null;//define GameClock here

	private static void createAndShowGUI() {
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		snakeFrame.setSize(xCurrentPixelMaxDimension, yCurrentPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		//add the snake panel to the JFrame
		snakeFrame.add(snakePanel);
		//add Game control to the key listener event handler
		snakePanel.addKeyListener(new GameControls(snake));
		//set game stage to before game
		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);
	}

	//initialize game which initializes game components
	//snake, kibble and score
	private static void initializeGame() {
		//set up score, snake and first kibble
		xSquares = xCurrentPixelMaxDimension / squareSize;//calculate x side of square
		ySquares = yCurrentPixelMaxDimension / squareSize;//calculate y side of square

		snake = new Snake(xSquares, ySquares, squareSize);//create new snake
		kibble = new Kibble(snake);//create kibble
		score = new Score();//create score object to store score

		gameStage = BEFORE_GAME;//set the game stage as the beginning
	}

	protected static void newGame() {//this is to start a new game called from game controller when key pressed event occurs
		Timer timer = new Timer();//start a new timer
		//create a game clock object which is subclass of timer task and as overridden run() method
		clockTick = new GameClock(snake, kibble, score, snakePanel, timer);
		//schedule the timer to tick at interval which also calls run() method
		//in GameClock that keeps the game going
		timer.scheduleAtFixedRate(clockTick, 0, clockInterval);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();//initialize the game create snake, kibble and score object
				createAndShowGUI();//run the method to start up the gui
			}
		});
	}


	//get the game stage if before game, during game, game over, game won
	public static int getGameStage() {
		return gameStage;
	}

	//return true if game ended, which can be two ways game over snake touch wall
	// , game won user wins the game
	public static boolean gameEnded() {
		if (gameStage == GAME_OVER || gameStage == GAME_WON) {
			return true;
		}
		return false;
	}

	//set the game stage value depending upon the game stage transition
	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;
	}

	//method to set wrap wall on and off
	public static void setToggleWarpWall() {
		if (SnakeGame.warpWall) {
			SnakeGame.warpWall = false;
		} else {
			SnakeGame.warpWall = true;
		}
	}

	//get the warp wall info
	public static boolean getWarpWall() {
		return SnakeGame.warpWall;
	}

	//method to set maze on and off
	public static void setToggleMaze() {
		if (SnakeGame.maze) {
			SnakeGame.maze = false;
		} else {
			SnakeGame.maze = true;
		}
	}

	//get the warp wall info
	public static boolean getMaze() {
		return SnakeGame.maze;
	}

	//set clock speed
	public static void setClockSpeed(long clockInterval) {
		SnakeGame.clockInterval = clockInterval;
	}

	//get the warp wall info
	public static long getClockSpeed() {
		return clockInterval;
	}

	//set screen size
	public static void setScreenSize(int xCurrentPixelMaxDimension, int yCurrentPixelMaxDimension) {
		SnakeGame.xCurrentPixelMaxDimension = xCurrentPixelMaxDimension;
		SnakeGame.yCurrentPixelMaxDimension = yCurrentPixelMaxDimension;

		SnakeGame.squareSize = xCurrentPixelMaxDimension/10;
		SnakeGame.xSquares = SnakeGame.xCurrentPixelMaxDimension / squareSize;
		SnakeGame.ySquares = SnakeGame.yCurrentPixelMaxDimension / squareSize;

		snakeFrame.setResizable(true);
		snakeFrame.setBounds(0, 0, xCurrentPixelMaxDimension, yCurrentPixelMaxDimension);
		snakeFrame.setVisible(true);
	}

	//get current x pixel and y pixel
	public static int getxPixelMaxDimension()
	{
		return SnakeGame.xCurrentPixelMaxDimension;
	}

	public static int getyPixelMaxDimension()
	{
		return SnakeGame.yCurrentPixelMaxDimension;
	}

	//return TimerTask
	public static GameClock getGameClock ()
	{
		return clockTick;
	}

	//get x squares
	public static int getXSquares()
	{
		return SnakeGame.xSquares;
	}
	//get y squares
	public static int getYSquares()
	{
		return SnakeGame.ySquares;
	}

	//get square size
	public static int getSquareSize()
	{
		return SnakeGame.squareSize;
	}
	//return snake speed factor
	public static int getSnakeSpeedFactor(){
		return 10 - (int)SnakeGame.getClockSpeed() / 100;
	}
}
