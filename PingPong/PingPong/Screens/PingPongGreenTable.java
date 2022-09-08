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
 * ���� ����� ������ ������� ���� ��� ���� ����� ���������� ���, ������� � ����
 * ����
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

	// �����������. ������� ���������� ������� ����.
	public PingPongGreenTable() {

		PingPongGameEngine gameEngine = new PingPongGameEngine(this);

		// ������������ �������� ���� ��� ������������ �������
		addMouseMotionListener(gameEngine);

		// ������������ ����� ���� ��� ����������� �� ���������
		addKeyListener(gameEngine);

	}

	public void addPanelToFrame(Container container) {

		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		container.add(this);

		label = new JLabel("Press N - to play new game, S to serve the ball or Q to quit.");

		label.setPreferredSize(new Dimension(TABLE_WIDTH, 10));
		container.add(label);

	}

	// ������������ ����. ���� ����� ���������� �����������
	// �������, ����� ����� �������� ����� ���
	// ���������� ����� repaint() �� PingPointGameEngine
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// ���������� ����
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, TABLE_WIDTH, TABLE_HEIGHT);

		// ���������� ������ �������
		g.setColor(Color.YELLOW);
		g.fillRect(KID_RACKET_X_START, kidRacket_Y, RACKET_WIDTH, RACKET_LENGTH);

		// ���������� ����� �������
		g.setColor(Color.BLUE);
		g.fillRect(COMPUTER_RACKET_X_START, computerRacket_Y, RACKET_WIDTH, RACKET_LENGTH);

		// ���������� ���
		g.setColor(Color.RED);
		g.fillOval(ballX, ballY, 10, 10);

		// ���������� ����� �����
		g.setColor(Color.YELLOW);
		g.drawRect(10, 10, 300, 200);
		g.drawLine(160, 10, 160, 210);

		// ���������� ����� �� ����,
		// ����� ���������� ���������� ��� �������� ������� �����
		requestFocus();

	}

	// ���������� ������� ��������� ������� �������
	public void setKidRacket_Y(int yCoordinate) {
		this.kidRacket_Y = yCoordinate;
		repaint();
	}

	// ���������� ������� ��������� ������� �����
	public void setComputerRacket_Y(int yCoordinate) {
		this.computerRacket_Y = yCoordinate;
		repaint();
	}

	// ���������� ������� ���������
	public void setMesageText(String text) {
		label.setText(text);
		repaint();
	}

	// ���������� ������� ����
	public void setBallPosition(int xPos, int yPos) {
		ballX = xPos;
		ballY = yPos;
		repaint();
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("Ping pong green table");

		// ���������, ��� ���� ����� ���� ������� �� ������� ��
		// ������� � ����
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		PingPongGreenTable table = new PingPongGreenTable();
		table.addPanelToFrame(f.getContentPane());

		f.setBounds(0, 0, TABLE_WIDTH + 35, TABLE_HEIGHT + 55);
		f.setVisible(true);

	}

}
