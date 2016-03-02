package com.suresh;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */
//this class is responsible for drawsing snake panel
public class DrawSnakeGamePanel extends JPanel {
	//use the getGameStage from snake game and update this
	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	
	private Snake snake;
	private Kibble kibble;
	private Score score;
	Random rnd = new Random();
	private int rndNum = rnd.nextInt(4)+1;

	DrawSnakeGamePanel(Snake s, Kibble k, Score sc){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
		this.snake.setRandom(rndNum);
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

	//this is called when repaint method is called
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       //call paint component of super class

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
        case 1: {
        	displayInstructions(g);
        	break;
        } 
        case 2 : {
        	displayGame(g);
        	break;
        }
        case 3: {
        	displayGameOver(g);
        	break;
        }
        case 4: {
        	displayGameWon(g);
        	break;
        }
        }
    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		g.setColor(Color.RED);
		g.setFont(font);
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}
	private void displayGameOver(Graphics g) {

		Color originalColor = g.getColor();

		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		g.setColor(Color.RED);
		g.setFont(font);
		g.clearRect(0,0,500,500);
		g.drawString("GAME OVER", 150, 40);

		g.setColor(originalColor);
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 100, 60);
		
		g.drawString("HIGH SCORE = " + textHighScore, 100, 80);
		g.drawString(newHighScore, 100, 100);

		g.setColor(Color.RED);
		g.drawString("Press any key except the ones define below to begin!", 100, 120);
		g.drawString("Press w to turn on/off wrap walls, currently its " + (SnakeGame.getWarpWall() ? "on": "off"), 100, 140);
		g.drawString("Press m to turn on/off mazes, currently its " + (SnakeGame.getMaze() ? "on": "off"), 100, 160);
		g.drawString("Press i to increase, d to decrease snake speed (1-9)", 100, 180);
		g.drawString("Current Snake speed: " + Long.toString(SnakeGame.getSnakeSpeedFactor()), 100, 200);
		g.drawString("Press l/s to increase/decrease screen size", 100, 220);
		g.drawString("Press q to quit the game",100,240);
		g.setColor(originalColor);
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
		//draw maze if it is set to on
		if(SnakeGame.getMaze()){
			displayMaze(g);
		}
	}

	private void displayGameGrid(Graphics g) {

		//int maxX = SnakeGame.xPixelMaxDimension;
		//int maxY= SnakeGame.yPixelMaxDimension;
		//int squareSize = SnakeGame.squareSize;

		int maxX = SnakeGame.getxPixelMaxDimension();
		int maxY= SnakeGame.getyPixelMaxDimension();
		int squareSize = SnakeGame.getSquareSize();
		
		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.RED);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayKibble(Graphics g) {

		//Draw the kibble in green
		g.setColor(Color.GREEN);

		//int x = kibble.getKibbleX() * SnakeGame.squareSize;
		//int y = kibble.getKibbleY() * SnakeGame.squareSize;

		int x = kibble.getKibbleX() * SnakeGame.getSquareSize();
		int y = kibble.getKibbleY() * SnakeGame.getSquareSize();

		//g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
		g.fillRect(x+1, y+1, SnakeGame.getSquareSize()-2, SnakeGame.getSquareSize()-2);
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		//Draw head in grey
		g.setColor(Color.BLUE);
		Point head = coordinates.pop();
		//g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
		
		//Draw rest of snake in black
		g.setColor(Color.BLACK);
		for (Point p : coordinates) {
			//g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
		}

	}

	//display the maze
	private void displayMaze(Graphics g)
	{
		g.setColor(Color.BLACK);
		//draw maze lines on to the grid using randNum generates for this instance
		g.drawLine(SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*2);
		g.drawLine(SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*2, SnakeGame.getSquareSize()*rndNum);

		g.drawLine(SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*3, SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*4);
		g.drawLine(SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum*2, SnakeGame.getSquareSize()*rndNum*4);

		g.drawLine(SnakeGame.getSquareSize()*rndNum*3, SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum);
		g.drawLine(SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum, SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum*2);

		g.drawLine(SnakeGame.getSquareSize()*rndNum*3, SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum*4);
		g.drawLine(SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum*3, SnakeGame.getSquareSize()*rndNum*4, SnakeGame.getSquareSize()*rndNum*4);
	}

	private void displayInstructions(Graphics g) {
		Color originalColor = g.getColor();

		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		g.setColor(Color.RED);
		g.setFont(font);

        g.drawString("Press any key except the ones define below to begin!",100,100);
		g.drawString("Press w to turn on/off wrap walls, currently its " + (SnakeGame.getWarpWall() ? "on": "off"), 100, 120);
		g.drawString("Press m to turn on/off mazes, currently its " + (SnakeGame.getMaze() ? "on": "off"), 100, 140);
		g.drawString("Press i to increase, d to decrease snake speed (1-9)", 100, 160);
		g.drawString("Current Snake speed: " + Long.toString(SnakeGame.getSnakeSpeedFactor()), 100, 180);
		g.drawString("Press l/s to increase/decrease screen size", 100, 200);
	    g.drawString("Press q to quit the game",100,220);
		g.setColor(originalColor);
    	}
	
    
}

