package com.suresh;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{
	
	Snake snake;
	
	GameControls(Snake s){
		this.snake = s;
	}
	
	public void keyPressed(KeyEvent ev) {
		//keyPressed events are for catching events like function keys, enter, arrow keys
		//We want to listen for arrow keys to move snake
		//Has to id if user pressed arrow key, and if so, send info to Snake object

		//is game running? No? tell the game to draw grid, start, etc.
		
		//Get the component which generated this event
		//Hopefully, a DrawSnakeGamePanel object.
		//It would be a good idea to catch a ClassCastException here. 
		
		DrawSnakeGamePanel panel = (DrawSnakeGamePanel)ev.getComponent();

		//go in here only none of the key pressed
		if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME
				&& (ev.getKeyCode() != KeyEvent.VK_W
					&& ev.getKeyCode() != KeyEvent.VK_M
					&& ev.getKeyCode() != KeyEvent.VK_I
					&& ev.getKeyCode() != KeyEvent.VK_D
					&& ev.getKeyCode() != KeyEvent.VK_S
					&& ev.getKeyCode() != KeyEvent.VK_L)){
			//Start the game
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			SnakeGame.newGame();
			panel.repaint();
			return;
		}

		//go in here only none of the key pressed
		if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER
				&& (ev.getKeyCode() != KeyEvent.VK_W
				&& ev.getKeyCode() != KeyEvent.VK_M
				&& ev.getKeyCode() != KeyEvent.VK_I
				&& ev.getKeyCode() != KeyEvent.VK_D
				&& ev.getKeyCode() != KeyEvent.VK_S
				&& ev.getKeyCode() != KeyEvent.VK_L)){
			snake.reset();
			Score.resetScore();
			
			//Need to start the timer and start the game again
			SnakeGame.newGame();
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			panel.repaint();
			return;
		}

		
		if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
			//System.out.println("snake down");
			snake.snakeDown();
		}
		if (ev.getKeyCode() == KeyEvent.VK_UP) {
			//System.out.println("snake up");
			snake.snakeUp();
		}
		if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
			//System.out.println("snake left");
			snake.snakeLeft();
		}
		if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
			//System.out.println("snake right");
			snake.snakeRight();
		}

	}


	@Override
	public void keyReleased(KeyEvent ev) {
		//We don't care about keyReleased events, but are required to implement this method anyway.		
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		DrawSnakeGamePanel panel = (DrawSnakeGamePanel)ev.getComponent();

		//quit the program
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		if( keyPressed == q){
			System.exit(0);    //quit if user presses the q key.
		}

		//warp on and off toogle
		char w = 'w';
		if( keyPressed == w){
			SnakeGame.setToggleWarpWall();//set warp on/off
			panel.repaint();
		}
		//maze on or off toogle
		char m = 'm';
		if( keyPressed == m){
			SnakeGame.setToggleMaze();//set maze on off
			panel.repaint();
		}
		//decrease clock speed if d is pressed, which means longer time interval
		char d = 'd';
		if( keyPressed == d){
			long clockSpeed = SnakeGame.getClockSpeed();
			if(clockSpeed < 900)
			{
				SnakeGame.setClockSpeed(clockSpeed+100);//set clock timer change
				if(SnakeGame.getGameStage() == SnakeGame.DURING_GAME) {//restart timer if during the game
					SnakeGame.getGameClock().timer.purge();
					SnakeGame.getGameClock().timer.cancel();
					SnakeGame.getGameClock().cancel();
					SnakeGame.newGame();
				}
				panel.repaint();
			}
		}

		//increase clock speed if i is pressed, which means shorter time interval
		char i = 'i';
		if( keyPressed == i){
			long clockSpeed = SnakeGame.getClockSpeed();
			if(clockSpeed > 100)
			{
				SnakeGame.setClockSpeed(clockSpeed-100);
				if(SnakeGame.getGameStage() == SnakeGame.DURING_GAME) {//restart timer if during the game
					SnakeGame.getGameClock().timer.purge();
					SnakeGame.getGameClock().timer.cancel();
					SnakeGame.getGameClock().cancel();
					SnakeGame.newGame();
				}
				panel.repaint();
			}
		}

		//decrease screen size if s is pressed
		char s = 's';
		if( keyPressed == s){
			if(SnakeGame.getxPixelMaxDimension() > SnakeGame.xPixelMaxDimension
					&& SnakeGame.getyPixelMaxDimension() > SnakeGame.yPixelMaxDimension)
			{
				SnakeGame.setScreenSize(SnakeGame.getxPixelMaxDimension()-100, SnakeGame.getyPixelMaxDimension()-100);
				snake.setMaxX(SnakeGame.getXSquares());
				snake.setMaxY(SnakeGame.getYSquares());
				snake.setSquareSize(SnakeGame.getSquareSize());
				panel.repaint();
			}
		}

		//increase screen size if l is pressed
		char l = 'l';
		if( keyPressed == l){
			if(SnakeGame.getxPixelMaxDimension() < SnakeGame.xPixelMaxDimension+300
					&& SnakeGame.getyPixelMaxDimension() < SnakeGame.yPixelMaxDimension+300)
			{
				SnakeGame.setScreenSize(SnakeGame.getxPixelMaxDimension()+100, SnakeGame.getyPixelMaxDimension()+100);
				snake.setMaxX(SnakeGame.getXSquares());
				snake.setMaxY(SnakeGame.getYSquares());
				snake.setSquareSize(SnakeGame.getSquareSize());
				panel.repaint();
			}
		}
	}

}
