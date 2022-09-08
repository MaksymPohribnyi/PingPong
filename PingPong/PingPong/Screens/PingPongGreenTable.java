package Screens;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Engine.PingPongGameEngine;

/**
 * Этот класс рисует зеленый стол для пинг понга отображает шар, ракетки и счет
 * игры
 *
 */

public class PingPongGreenTable extends JPanel implements GameConstants {

	JLabel label;

	public int computerRacket_Y = COMPUTER_RACKET_Y_START;
	private int kidRacket_Y = KID_RACKET_Y_START;
	private int ballX = BALL_X_START;
	private int ballY = BALL_Y_START;

	Dimension prefferedSize = new Dimension(TABLE_WIDTH, TABLE_HEIGHT);

	public Dimension getPrefferedSize() {

		return prefferedSize;

	}

	// Конструктор. Создает обработчик событий мыши.
	public PingPongGreenTable() {

		PingPongGameEngine gameEngine = new PingPongGameEngine(this);

		// Обрабатывает движения мыши для передвижения ракеток
		addMouseMotionListener(gameEngine);

		// Обрабатывает клики мыши для отображения ее координат
		addKeyListener(gameEngine);

	}

	public void addPanelToFrame(Container container) {

		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		container.add(this);

		label = new JLabel("Press N - to play new game, S to serve the ball or Q to quit.");

		label.setPreferredSize(new Dimension(TABLE_WIDTH, 10));
		container.add(label);

	}

	// Перерисовать окно. Этот метод вызывается виртуальной
	// машиной, когда нужно обновить экран или
	// вызывается метод repaint() из PingPointGameEngine
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Нарисовать стол
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, TABLE_WIDTH, TABLE_HEIGHT);

		// Нарисовать правую ракетку
		g.setColor(Color.YELLOW);
		g.fillRect(KID_RACKET_X_START, kidRacket_Y, RACKET_WIDTH, RACKET_LENGTH);

		// Нарисовать левую ракетку
		g.setColor(Color.BLUE);
		g.fillRect(COMPUTER_RACKET_X_START, computerRacket_Y, RACKET_WIDTH, RACKET_LENGTH);

		// Нарисовать мяч
		g.setColor(Color.RED);
		g.fillOval(ballX, ballY, 10, 10);

		// Нарисовать белые линии
		g.setColor(Color.YELLOW);
		g.drawRect(10, 10, 300, 200);
		g.drawLine(160, 10, 160, 210);

		// Установить фокус на стол,
		// чтобы обработчик клавиатуры мог посылать команды столу
		requestFocus();

	}

	// Установить текущее положение ракетки ребенка
	public void setKidRacket_Y(int yCoordinate) {
		this.kidRacket_Y = yCoordinate;
		repaint();
	}

	// Установить текущее положение ракетки компа
	public void setComputerRacket_Y(int yCoordinate) {
		this.computerRacket_Y = yCoordinate;
		repaint();
	}

	// Установить игровое сообщение
	public void setMesageText(String text) {
		label.setText(text);
		repaint();
	}

	// Установить позицию мяча
	public void setBallPosition(int xPos, int yPos) {
		ballX = xPos;
		ballY = yPos;
		repaint();
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("Ping pong green table");

		// Убедиться, что окно может быть закрыто по нажатию на
		// крестик в углу
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		PingPongGreenTable table = new PingPongGreenTable();
		table.addPanelToFrame(f.getContentPane());

		f.setBounds(0, 0, TABLE_WIDTH + 35, TABLE_HEIGHT + 55);
		f.setVisible(true);

	}

}
