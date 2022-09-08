package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Screens.*;

/**
 * ���� ����� - ���������� ������� ���� � ����������. ������������ �������� ����
 * � �������, ��������� �� ���������
 * 
 * @author hp pavilion
 *
 */

public class PingPongGameEngine implements Runnable, KeyListener, MouseMotionListener, GameConstants {

	PingPongGreenTable table;

	private volatile int kidRacket_Y = KID_RACKET_Y_START;
	private volatile int computerRacket_Y = COMPUTER_RACKET_Y_START;
	private int kidScore;
	private int compScore;
	private int ballX;
	private int ballY;
	private boolean movingLeft = true;
	private volatile boolean ballServed = false;

	// ��� ������������ ����� ���������� ������ ����� ������������ � ������� ���
	// ������ �������� �����
	private int ballYTopOrBottom;

	// �������� ������������� ������������ ���� � ��������
	private int verticalSlide;

	// �����������. �������� ������ �� ������ �����
	public PingPongGameEngine(PingPongGreenTable greenTable) {

		table = greenTable;

		Thread worker = new Thread(this);
		worker.start();

	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

		int mouse_Y = e.getY();

		// ���� ���� ��������� ���� ������� �������
		// � �� ������� �� ������� ����� �
		// ����������� �� �����, � ��������� ������ � �������� ����
		if (mouse_Y < kidRacket_Y && kidRacket_Y > TABLE_TOP) {
			kidRacket_Y -= RACKET_INCREMENT;
		} else if (kidRacket_Y < TABLE_BOTTOM) {
			kidRacket_Y += RACKET_INCREMENT;
		}

		// ���������� ����� ��������� �������
		table.setKidRacket_Y(kidRacket_Y);

	}

	// ������������ ������ ���������� KeyListener
	public void keyPressed(KeyEvent e) {

		char key = e.getKeyChar();

		if (key == 'n' || key == 'N' || key == '�' || key == '�') {
			enterNewGame();
		} else if ((key == 's' || key == 'S' || key == '�' || key == '�') && compScore != WINNING_SCORE
				&& kidScore != WINNING_SCORE) {
			kidServe();
		} else if (key == 'q' || key == 'Q') {
			endGame();
		}

	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void enterNewGame() {

		kidScore = 0;
		compScore = 0;

		table.setMesageText("Score Computer: 0    Player: 0");

		kidServe();

	}

	public void endGame() {

		System.exit(0);

	}

	// ������������ ����� run() �� ���������� Runnable
	public void run() {

		boolean canBounce = false;

		while (true) {

			if (ballServed) {

				// ���� ��� �������� �����
				if (movingLeft && ballX > BALL_MIN_X) {
					canBounce = (ballY >= computerRacket_Y && ballY < (computerRacket_Y + RACKET_LENGTH) ? true
							: false);

					if (ballY <= TABLE_TOP || ballY >= TABLE_BOTTOM_LINE) {
						ballYTopOrBottom = ballY;
					}

					if (ballYTopOrBottom <= TABLE_TOP) {
						verticalSlide = -1;
					} else if (ballYTopOrBottom >= TABLE_BOTTOM_LINE) {
						verticalSlide = 1;
					}

					ballX -= BALL_INCREMENT;
					ballY -= verticalSlide;
					table.setBallPosition(ballX, ballY);

					if (ballX <= COMPUTER_RACKET_X_START && canBounce) {
						movingLeft = false;
					}

				}

				// ���� ��� �������� ������
				if (!movingLeft && ballX <= BALL_MAX_X) {

					canBounce = (ballY >= kidRacket_Y && ballY < (kidRacket_Y + RACKET_LENGTH) ? true : false);

					if (ballY <= TABLE_TOP || ballY >= TABLE_BOTTOM_LINE) {
						ballYTopOrBottom = ballY;
					}

					if (ballYTopOrBottom <= TABLE_TOP) {
						verticalSlide = 1;
					} else if (ballYTopOrBottom >= TABLE_BOTTOM_LINE) {
						verticalSlide = -1;
					}

					ballX += BALL_INCREMENT;
					ballY += verticalSlide;
					table.setBallPosition(ballX, ballY);

					if (ballX >= KID_RACKET_X_START && canBounce) {

						movingLeft = true;

					}

				}

				// ����������� ������� ����� ����� ��� ����
				// ��� ������������ ����
				if (computerRacket_Y - RACKET_INCREMENT >= ballY && computerRacket_Y > TABLE_TOP) {
					computerRacket_Y -= RACKET_INCREMENT;
				} else if (computerRacket_Y + RACKET_INCREMENT <= ballY && computerRacket_Y < TABLE_BOTTOM) {
					computerRacket_Y += RACKET_INCREMENT;
				}
				table.setComputerRacket_Y(computerRacket_Y);

				try {

					Thread.sleep(SLEEP_TIME);

				} catch (InterruptedException e) {

					e.printStackTrace();

				}

				if (isBallOnTheTable()) {

					if (ballX > BALL_MAX_X) {

						compScore++;
						displayScore();

					} else if (ballX < BALL_MIN_X) {

						kidScore++;
						displayScore();

					}

				}
			}
		}

	}

	private void kidServe() {

		ballServed = true;
		ballX = KID_RACKET_X_START - 1;
		ballY = kidRacket_Y;

		if (ballY > TABLE_HEIGHT / 2) {

			verticalSlide = -1;

		} else {

			verticalSlide = 1;

		}

		table.setBallPosition(ballX, ballY);
		table.setKidRacket_Y(kidRacket_Y);

	}

	private void displayScore() {

		ballServed = false;

		if (compScore == WINNING_SCORE) {

			table.setMesageText("Computer won! " + compScore + " : " + kidScore);

		} else if (kidScore == WINNING_SCORE) {

			table.setMesageText("YOU won! " + kidScore + " : " + compScore);

		} else {

			table.setMesageText("Computer: " + compScore + " Player : " + kidScore);

		}

	}

	private boolean isBallOnTheTable() {

		if (ballY >= BALL_MIN_Y && ballY <= BALL_MAX_Y) {
			return true;
		} else {
			return false;
		}

	}

}
