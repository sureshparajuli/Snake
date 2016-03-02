package com.suresh;

import java.util.TimerTask;
import java.util.Timer;

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	Score score;
	DrawSnakeGamePanel gamePanel;
	Timer timer;

	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel, Timer timer){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
		this.timer = timer;
	}

	@Override
	public void run() {
		// This method will be called every clock tick

		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				//
				snake.moveSnake();
				if (snake.didEatKibble(kibble) == true) {
					//tell kibble to update
					kibble.moveKibble(snake);
					Score.increaseScore();
				}
				break;
			}
			case SnakeGame.GAME_OVER: {
				this.cancel();		//Stop the Timer
				timer.purge();//remove all timertask from this timer
				timer.cancel();//stop this timer
				break;
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   //stop timer
				timer.purge();//remove all timertask from this timer
				timer.cancel();//stop this timer
				break;
			}


		}

		gamePanel.repaint();		//In every circumstance, must update screen

	}
}
