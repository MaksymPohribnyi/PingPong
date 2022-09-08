package Screens;

public interface GameConstants {

	public final int TABLE_WIDTH = 320;
	public final int TABLE_HEIGHT = 220;
	public final int TABLE_TOP = 12;
	public final int TABLE_BOTTOM = 180;

	public final int RACKET_WIDTH = 5;
	public final int RACKET_LENGTH = 30;

	public final int RACKET_INCREMENT = 3;

	public final int BALL_X_START = TABLE_WIDTH / 2;
	public final int BALL_Y_START = TABLE_HEIGHT / 2;
	public final int BALL_INCREMENT = 5;
	public final int BALL_MIN_X = 1 + BALL_INCREMENT;
	public final int BALL_MIN_Y = 1 + BALL_INCREMENT;
	public final int BALL_MAX_X = TABLE_WIDTH - BALL_INCREMENT;
	public final int BALL_MAX_Y = TABLE_HEIGHT - BALL_INCREMENT;

	public final int COMPUTER_RACKET_X_START = 15;
	public final int COMPUTER_RACKET_Y_START = 100;

	public final int KID_RACKET_X_START = 300;
	public final int KID_RACKET_Y_START = 100;

	// Время в миллисекундах 
	public final int SLEEP_TIME = 10;

	public final int TABLE_BOTTOM_LINE = TABLE_BOTTOM + RACKET_LENGTH - BALL_INCREMENT;
	
	public final int WINNING_SCORE = 21;
	
}