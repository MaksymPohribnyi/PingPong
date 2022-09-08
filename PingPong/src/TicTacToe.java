import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class TicTacToe implements ActionListener {

	Button squares[][];
	Button newGameButton;
	Label score;
	Label scoreComp;
	Label scorePlayer;
	int emptySquaresLeft = 9;
	int counterCompWins = 0;
	int counterPlayWins = 0;

	public TicTacToe() {

		// Applet - � �� ������� � 17-�� �����, ���� ����� ������� �����
		Panel windowContent = new Panel();
		BorderLayout b1 = new BorderLayout();
		windowContent.setLayout(b1);

		// ������� ������ �New Game� � ������������ � ���
		// ��������� ��������
		newGameButton = new Button("New Game");
		newGameButton.setEnabled(false);
		newGameButton.addActionListener(this);
		newGameButton.setBackground(Color.green);

		Panel topPanel = new Panel();
		GridLayout gl = new GridLayout(3, 1);
		topPanel.setLayout(gl);
		topPanel.setBackground(Color.YELLOW);

		scoreComp = new Label("Computer wins : 0");
		scorePlayer = new Label("Player wins : 0");

		topPanel.add(scoreComp);
		topPanel.add(scorePlayer);
		topPanel.add(newGameButton);
		windowContent.add(topPanel, "North");

		Panel centerPanel = new Panel();
		centerPanel.setLayout(new GridLayout(3, 3));
		windowContent.add(centerPanel, "Center");

		score = new Label("Your turn");
		windowContent.add(score, "South");

		// ������� ������, ����� ������� ������ �� 9 ������
		squares = new Button[3][3];

		// ������� ������, ��������� �� ��� ������ � �������
		// ������������ ��������� ��� ������
		// ������ � ��������� ���� � ��������� �� ������

		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				squares[i][j] = new Button();
				squares[i][j].addActionListener(this);
				squares[i][j].setBackground(Color.ORANGE);
				centerPanel.add(squares[i][j]);
			}
		}

		JFrame frame = new JFrame("Tic tac toe");
		frame.setContentPane(windowContent);

		// ������ ������ ���� �����������, ���� �������� ��� ��������
		frame.setSize(400, 400);
		// frame.pack();

		// �������, ��������� ����
		frame.setVisible(true);
	}

	/**
	 * ���� ����� ����� ������������ ��� �������
	 * 
	 * @param ActionEvent ������
	 */
	public void actionPerformed(ActionEvent e) {

		Button theButton = (Button) e.getSource();

		// ��� ������ New Game?
		if (theButton == newGameButton) {

			for (int i = 0; i < squares.length; i++) {
				for (int j = 0; j < squares[i].length; j++) {
					squares[i][j].setEnabled(true);
					squares[i][j].setLabel("");
					squares[i][j].setBackground(Color.ORANGE);
				}
			}

			emptySquaresLeft = 9;
			score.setText("Your turn!");
			theButton.setEnabled(false);

			return;

		}

		// ���������� ������� �� ������, � ������� ��� ������ ���
		if (!theButton.getLabel().equals("")) {
			return;
		}

		String winner = "";

		// ��� ���� �� ������?
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {

				if (theButton == squares[i][j]) {

					squares[i][j].setLabel("X");
					winner = lookForWinner();

					if (!"".equals(winner)) {

						endTheGame();

					} else {

						ComputerMove();
						winner = lookForWinner();

						if (!"".equals(winner)) {

							endTheGame();

						}
					}

					break;
				}

			}
		}

		if (winner.equals("X")) {
			score.setText("You won!");
			counterPlayWins++;
			scorePlayer.setText("Player wins: " + counterPlayWins);
		} else if (winner.equals("O")) {
			score.setText("You lost!");
			counterCompWins++;
			scoreComp.setText("Computer wins: " + counterCompWins);
		} else if (winner.equals("T")) {
			score.setText("It`s a tie");
		}

	} // ����� ������ actionPerfomed

	/**
	 * ���� ����� ���������� ����� ������� ����, ����� ������, ���� �� ����������.
	 * �� ��������� ������ ���, ������� � ���������, ����� ����� ��� ������ �
	 * ����������� ��������� (�� �������)
	 * 
	 * @return "X", "O", "T" � �����, "" - ��� ��� ����������
	 */
	String lookForWinner() {

		String theWinner = "";
		emptySquaresLeft--;

		if (emptySquaresLeft == 0) {
			return "T"; // ��� �����. T �� ����������� ����� tie
		}

		// ��������� ��� 1 - �������� ������� 0, 1, 2
		if (!squares[0][0].getLabel().equals("") && squares[0][0].getLabel().equals(squares[0][1].getLabel())
				&& squares[0][0].getLabel().equals(squares[0][2].getLabel())) {

			theWinner = squares[0][0].getLabel();
			hightLightWinner(0, 0, 0, 0, 1, 2);

			// ��������� ��� 2 - �������� ������� 3, 4, 5
		} else if (!squares[1][0].getLabel().equals("") && squares[1][0].getLabel().equals(squares[1][1].getLabel())
				&& squares[1][0].getLabel().equals(squares[1][2].getLabel())) {

			theWinner = squares[1][0].getLabel();
			hightLightWinner(1, 1, 1, 0, 1, 2);

			// ��������� ��� 3 - �������� ������� 6, 7, 8
		} else if (!squares[2][0].getLabel().equals("") && squares[2][0].getLabel().equals(squares[2][1].getLabel())
				&& squares[2][0].getLabel().equals(squares[2][2].getLabel())) {

			theWinner = squares[2][0].getLabel();
			hightLightWinner(2, 2, 2, 0, 1, 2);

			// ��������� ������� 1 � �������� ������� 0,3,6
		} else if (!squares[0][0].getLabel().equals("") && squares[0][0].getLabel().equals(squares[1][0].getLabel())
				&& squares[0][0].getLabel().equals(squares[2][0].getLabel())) {

			theWinner = squares[0][0].getLabel();
			hightLightWinner(0, 1, 2, 0, 0, 0);

			// ��������� ������� 2 � �������� ������� 1, 4, 7
		} else if (!squares[0][1].getLabel().equals("") && squares[0][1].getLabel().equals(squares[1][1].getLabel())
				&& squares[0][1].getLabel().equals(squares[2][1].getLabel())) {

			theWinner = squares[0][1].getLabel();
			hightLightWinner(0, 1, 2, 1, 1, 1);

			// ��������� ������� 3 � �������� ������� 2, 5, 8
		} else if (!squares[0][2].getLabel().equals("") && squares[0][2].getLabel().equals(squares[1][2].getLabel())
				&& squares[0][2].getLabel().equals(squares[2][2].getLabel())) {

			theWinner = squares[0][2].getLabel();
			hightLightWinner(0, 1, 2, 2, 2, 2);

			// ��������� ������ ��������� � �������� ������� 0, 4, 8
		} else if (!squares[0][0].getLabel().equals("") && squares[0][0].getLabel().equals(squares[1][1].getLabel())
				&& squares[0][0].getLabel().equals(squares[2][2].getLabel())) {

			theWinner = squares[0][0].getLabel();
			hightLightWinner(0, 1, 2, 0, 1, 2);

			// ��������� ������ ��������� � �������� ������� 2, 4, 6
		} else if (!squares[0][2].getLabel().equals("") && squares[0][2].getLabel().equals(squares[1][1].getLabel())
				&& squares[0][2].getLabel().equals(squares[2][0].getLabel())) {

			theWinner = squares[0][2].getLabel();
			hightLightWinner(0, 1, 2, 2, 1, 0);
		}

		return theWinner;

	}

	/**
	 * ���� ����� ��������� ����� ������, ����� ����� ������ ������������ ���. ����
	 * ������� ��� �� ������, ���������� ��������� ������.
	 */
	void ComputerMove() {

		int selectedSquareI;
		int selectedSquareJ;

		UniversalForReturn returnValue;

		// ������� ��������� �������� ����� ������ ������
		// ����� � ����� �������� � ��������, ����� ��������
		returnValue = findEmptySquare("O");

		selectedSquareI = returnValue.value1;
		selectedSquareJ = returnValue.value2;

		// ���� �� �� ����� ����� ��� ������, �� ���� ��
		// ���������� �� ���� ��������� ������� ��� �� 3-�
		// ���������,�������� ����� ����� � ����� ����������
		if (selectedSquareI == -1 && selectedSquareJ == -1) {
			returnValue = findEmptySquare("X");
			selectedSquareI = returnValue.value1;
			selectedSquareJ = returnValue.value2;
		}

		// ���� selectedSquare ��� ��� ����� -1, ��
		// ���������� ������ ����������� ������
		if (selectedSquareI == -1 && selectedSquareJ == -1 && squares[1][1].getLabel().equals("")) {
			selectedSquareI = 1;
			selectedSquareJ = 1;
		}

		if (selectedSquareI == -1 && selectedSquareJ == -1) {
			returnValue = getRandomSquare();
			selectedSquareI = returnValue.value1;
			selectedSquareJ = returnValue.value2;
		}

		squares[selectedSquareI][selectedSquareJ].setLabel("O");

	}

	/**
	 * ���� ����� ��������� ������ ���, ������� � ��������� ����� ������, ���� �� �
	 * ��� ��� ������ � ����������� ��������� � ������ �������.
	 * 
	 * @param ���������� X � ��� ������������ � O � ��� �����
	 * @return ���������� ��������� ������, ��� -1, ���� �� ������� ��� ������ �
	 *         ����������� ���������
	 */
	UniversalForReturn findEmptySquare(String player) {

		int weight[][] = new int[3][3];

		for (int i = 0; i < weight.length; i++) {
			for (int j = 0; j < weight[i].length; j++) {
				if (squares[i][j].getLabel().equals("O")) {
					weight[i][j] = -1;
				} else if (squares[i][j].getLabel().equals("X")) {
					weight[i][j] = 1;
				} else
					weight[i][j] = 0;
			}
		}

		int twoWeight = player.equals("O") ? -2 : 2;

		// ��������, ���� �� � ���� 1 ��� ���������� ������ �
		// ���� ������.
		if (weight[0][0] + weight[0][1] + weight[0][2] == twoWeight) {
			if (weight[0][0] == 0) {
				return new UniversalForReturn(0, 0);
			} else if (weight[0][1] == 0) {
				return new UniversalForReturn(0, 1);
			} else if (weight[0][2] == 0) {
				return new UniversalForReturn(0, 2);
			}
		}
		// ��������, ���� �� � ���� 2 ��� ���������� ������ �
		// ���� ������.
		if (weight[1][0] + weight[1][1] + weight[1][2] == twoWeight) {
			if (weight[1][0] == 0) {
				return new UniversalForReturn(1, 0);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[1][2] == 0) {
				return new UniversalForReturn(1, 2);
			}
		}

		// ��������, ���� �� � ���� 3 ��� ���������� ������ �
		// ���� ������.
		if (weight[2][0] + weight[2][1] + weight[2][2] == twoWeight) {
			if (weight[2][0] == 0) {
				return new UniversalForReturn(2, 0);
			} else if (weight[2][1] == 0) {
				return new UniversalForReturn(2, 1);
			} else if (weight[2][2] == 0) {
				return new UniversalForReturn(2, 2);
			}
		}

		// ��������, ���� �� � ������� 1 ��� ���������� ������ �
		// ���� ������.
		if (weight[0][0] + weight[1][0] + weight[2][0] == twoWeight) {
			if (weight[0][0] == 0) {
				return new UniversalForReturn(0, 0);
			} else if (weight[1][0] == 0) {
				return new UniversalForReturn(1, 0);
			} else if (weight[2][0] == 0) {
				return new UniversalForReturn(2, 0);
			}
		}

		// ��������, ���� �� � ������� 2 ��� ���������� ������ �
		// ���� ������.
		if (weight[0][1] + weight[1][1] + weight[2][1] == twoWeight) {
			if (weight[0][1] == 0) {
				return new UniversalForReturn(0, 1);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[2][1] == 0) {
				return new UniversalForReturn(2, 1);
			}
		}

		// ��������, ���� �� � ������� 3 ��� ���������� ������ �
		// ���� ������.
		if (weight[0][2] + weight[1][2] + weight[2][2] == twoWeight) {
			if (weight[0][2] == 0) {
				return new UniversalForReturn(0, 2);
			} else if (weight[1][2] == 0) {
				return new UniversalForReturn(1, 2);
			} else if (weight[2][2] == 0) {
				return new UniversalForReturn(2, 2);
			}
		}

		// ��������, ���� �� � ��������� 1 ��� ���������� ������ �
		// ���� ������.
		if (weight[0][0] + weight[1][1] + weight[2][2] == twoWeight) {
			if (weight[0][0] == 0) {
				return new UniversalForReturn(0, 0);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[2][2] == 0) {
				return new UniversalForReturn(2, 2);
			}
		}

		// ��������, ���� �� � ��������� 2 ��� ���������� ������ �
		// ���� ������.
		if (weight[0][2] + weight[1][1] + weight[2][0] == twoWeight) {
			if (weight[0][2] == 0) {
				return new UniversalForReturn(0, 2);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[2][0] == 0) {
				return new UniversalForReturn(2, 0);
			}
		}

		return new UniversalForReturn(-1, -1);
	}

	/**
	 * ���� ����� �������� ����� ������ ������
	 * 
	 * @return �������� ��������� ����� ������
	 */

	UniversalForReturn getRandomSquare() {

		boolean gotEmptySquare = false;
		int selectedSquareI = -1;
		int selectedSquareJ = -1;

		do {
			selectedSquareI = (int) (Math.random() * 3);
			selectedSquareJ = (int) (Math.random() * 3);
			if (squares[selectedSquareI][selectedSquareJ].getLabel().equals("")) {
				gotEmptySquare = true;
			}
		} while (!gotEmptySquare);

		return new UniversalForReturn(selectedSquareI, selectedSquareJ);
	}

	/**
	 * ���� ����� �������� ���������� �����.
	 * 
	 * @param ������, ������ � ������ ������ ��� ���������
	 */
	void hightLightWinner(int iwin1, int iwin2, int iwin3, int jwin1, int jwin2, int jwin3) {
		squares[iwin1][jwin1].setBackground(Color.CYAN);
		squares[iwin2][jwin2].setBackground(Color.CYAN);
		squares[iwin3][jwin3].setBackground(Color.CYAN);
	}

	void endTheGame() {

		newGameButton.setEnabled(true);

		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				squares[i][j].setEnabled(false);
			}
		}

	}

	public static void main(String[] args) {

		TicTacToe newGame = new TicTacToe();

	}

}
