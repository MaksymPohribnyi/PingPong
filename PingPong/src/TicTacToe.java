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

		// Applet - ы не фурычат в 17-ой джава, пишу через обычный фрейм
		Panel windowContent = new Panel();
		BorderLayout b1 = new BorderLayout();
		windowContent.setLayout(b1);

		// Создаем кнопку “New Game” и регистрируем в ней
		// слушатель действия
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

		// Создаем массив, чтобы хранить ссылки на 9 кнопок
		squares = new Button[3][3];

		// Создаем кнопки, сохраняем на них ссылки в массиве
		// регистрируем слушатели для каждой
		// красим в оранжевый цвет и добавялем на панель

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

		// Делаем размер окна достаточным, чтоб уместить все элементы
		frame.setSize(400, 400);
		// frame.pack();

		// Наконец, отображем окно
		frame.setVisible(true);
	}

	/**
	 * Этот метод будет обрабатывать все события
	 * 
	 * @param ActionEvent объект
	 */
	public void actionPerformed(ActionEvent e) {

		Button theButton = (Button) e.getSource();

		// Это кнопка New Game?
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

		// Игнорируем нажатие на клетки, в которых уже сделан ход
		if (!theButton.getLabel().equals("")) {
			return;
		}

		String winner = "";

		// Это одна из кнопок?
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

	} // конец метода actionPerfomed

	/**
	 * Этот метод вызывается после каждого хода, чтобы узнать, есть ли победитель.
	 * Он проверяет каждый ряд, колонку и диагональ, чтобы найти три клетки с
	 * одинаковыми надписями (не пустыми)
	 * 
	 * @return "X", "O", "T" – ничья, "" - еще нет победителя
	 */
	String lookForWinner() {

		String theWinner = "";
		emptySquaresLeft--;

		if (emptySquaresLeft == 0) {
			return "T"; // это ничья. T от английского слова tie
		}

		// Проверяем ряд 1 - элементы массива 0, 1, 2
		if (!squares[0][0].getLabel().equals("") && squares[0][0].getLabel().equals(squares[0][1].getLabel())
				&& squares[0][0].getLabel().equals(squares[0][2].getLabel())) {

			theWinner = squares[0][0].getLabel();
			hightLightWinner(0, 0, 0, 0, 1, 2);

			// Проверяем ряд 2 - элементы массива 3, 4, 5
		} else if (!squares[1][0].getLabel().equals("") && squares[1][0].getLabel().equals(squares[1][1].getLabel())
				&& squares[1][0].getLabel().equals(squares[1][2].getLabel())) {

			theWinner = squares[1][0].getLabel();
			hightLightWinner(1, 1, 1, 0, 1, 2);

			// Проверяем ряд 3 - элементы массива 6, 7, 8
		} else if (!squares[2][0].getLabel().equals("") && squares[2][0].getLabel().equals(squares[2][1].getLabel())
				&& squares[2][0].getLabel().equals(squares[2][2].getLabel())) {

			theWinner = squares[2][0].getLabel();
			hightLightWinner(2, 2, 2, 0, 1, 2);

			// Проверяем колонку 1 – элементы массива 0,3,6
		} else if (!squares[0][0].getLabel().equals("") && squares[0][0].getLabel().equals(squares[1][0].getLabel())
				&& squares[0][0].getLabel().equals(squares[2][0].getLabel())) {

			theWinner = squares[0][0].getLabel();
			hightLightWinner(0, 1, 2, 0, 0, 0);

			// Проверяем колонку 2 – элементы массива 1, 4, 7
		} else if (!squares[0][1].getLabel().equals("") && squares[0][1].getLabel().equals(squares[1][1].getLabel())
				&& squares[0][1].getLabel().equals(squares[2][1].getLabel())) {

			theWinner = squares[0][1].getLabel();
			hightLightWinner(0, 1, 2, 1, 1, 1);

			// Проверяем колонку 3 – элементы массива 2, 5, 8
		} else if (!squares[0][2].getLabel().equals("") && squares[0][2].getLabel().equals(squares[1][2].getLabel())
				&& squares[0][2].getLabel().equals(squares[2][2].getLabel())) {

			theWinner = squares[0][2].getLabel();
			hightLightWinner(0, 1, 2, 2, 2, 2);

			// Проверяем первую диагональ – элементы массива 0, 4, 8
		} else if (!squares[0][0].getLabel().equals("") && squares[0][0].getLabel().equals(squares[1][1].getLabel())
				&& squares[0][0].getLabel().equals(squares[2][2].getLabel())) {

			theWinner = squares[0][0].getLabel();
			hightLightWinner(0, 1, 2, 0, 1, 2);

			// Проверяем вторую диагональ – элементы массива 2, 4, 6
		} else if (!squares[0][2].getLabel().equals("") && squares[0][2].getLabel().equals(squares[1][1].getLabel())
				&& squares[0][2].getLabel().equals(squares[2][0].getLabel())) {

			theWinner = squares[0][2].getLabel();
			hightLightWinner(0, 1, 2, 2, 1, 0);
		}

		return theWinner;

	}

	/**
	 * Этот метод применяет набор правил, чтобы найти лучший компьютерный ход. Если
	 * хороший ход не найден, выбирается случайная клетка.
	 */
	void ComputerMove() {

		int selectedSquareI;
		int selectedSquareJ;

		UniversalForReturn returnValue;

		// Сначала компьютер пытается найти пустую клетку
		// рядом с двумя клетками с ноликами, чтобы выиграть
		returnValue = findEmptySquare("O");

		selectedSquareI = returnValue.value1;
		selectedSquareJ = returnValue.value2;

		// Если он не может найти два нолика, то хотя бы
		// попытается не дать оппоненту сделать ряд из 3-х
		// крестиков,поместив нолик рядом с двумя крестиками
		if (selectedSquareI == -1 && selectedSquareJ == -1) {
			returnValue = findEmptySquare("X");
			selectedSquareI = returnValue.value1;
			selectedSquareJ = returnValue.value2;
		}

		// если selectedSquare все еще равен -1, то
		// попытается занять центральную клетку
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
	 * Этот метод проверяет каждый ряд, колонку и диагональ чтобы узнать, есть ли в
	 * ней две клетки с одинаковыми надписями и пустой клеткой.
	 * 
	 * @param передается X – для пользователя и O – для компа
	 * @return количество свободных клеток, или -1, если не найдено две клетки с
	 *         одинаковыми надписями
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

		// Проверим, есть ли в ряду 1 две одинаковые клетки и
		// одна пустая.
		if (weight[0][0] + weight[0][1] + weight[0][2] == twoWeight) {
			if (weight[0][0] == 0) {
				return new UniversalForReturn(0, 0);
			} else if (weight[0][1] == 0) {
				return new UniversalForReturn(0, 1);
			} else if (weight[0][2] == 0) {
				return new UniversalForReturn(0, 2);
			}
		}
		// Проверим, есть ли в ряду 2 две одинаковые клетки и
		// одна пустая.
		if (weight[1][0] + weight[1][1] + weight[1][2] == twoWeight) {
			if (weight[1][0] == 0) {
				return new UniversalForReturn(1, 0);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[1][2] == 0) {
				return new UniversalForReturn(1, 2);
			}
		}

		// Проверим, есть ли в ряду 3 две одинаковые клетки и
		// одна пустая.
		if (weight[2][0] + weight[2][1] + weight[2][2] == twoWeight) {
			if (weight[2][0] == 0) {
				return new UniversalForReturn(2, 0);
			} else if (weight[2][1] == 0) {
				return new UniversalForReturn(2, 1);
			} else if (weight[2][2] == 0) {
				return new UniversalForReturn(2, 2);
			}
		}

		// Проверим, есть ли в колонке 1 две одинаковые клетки и
		// одна пустая.
		if (weight[0][0] + weight[1][0] + weight[2][0] == twoWeight) {
			if (weight[0][0] == 0) {
				return new UniversalForReturn(0, 0);
			} else if (weight[1][0] == 0) {
				return new UniversalForReturn(1, 0);
			} else if (weight[2][0] == 0) {
				return new UniversalForReturn(2, 0);
			}
		}

		// Проверим, есть ли в колонке 2 две одинаковые клетки и
		// одна пустая.
		if (weight[0][1] + weight[1][1] + weight[2][1] == twoWeight) {
			if (weight[0][1] == 0) {
				return new UniversalForReturn(0, 1);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[2][1] == 0) {
				return new UniversalForReturn(2, 1);
			}
		}

		// Проверим, есть ли в колонке 3 две одинаковые клетки и
		// одна пустая.
		if (weight[0][2] + weight[1][2] + weight[2][2] == twoWeight) {
			if (weight[0][2] == 0) {
				return new UniversalForReturn(0, 2);
			} else if (weight[1][2] == 0) {
				return new UniversalForReturn(1, 2);
			} else if (weight[2][2] == 0) {
				return new UniversalForReturn(2, 2);
			}
		}

		// Проверим, есть ли в диагонали 1 две одинаковые клетки и
		// одна пустая.
		if (weight[0][0] + weight[1][1] + weight[2][2] == twoWeight) {
			if (weight[0][0] == 0) {
				return new UniversalForReturn(0, 0);
			} else if (weight[1][1] == 0) {
				return new UniversalForReturn(1, 1);
			} else if (weight[2][2] == 0) {
				return new UniversalForReturn(2, 2);
			}
		}

		// Проверим, есть ли в диагонали 2 две одинаковые клетки и
		// одна пустая.
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
	 * Этот метод выбирает любую пустую клетку
	 * 
	 * @return случайно выбранный номер клетки
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
	 * Этот метод выделяет выигравшую линию.
	 * 
	 * @param первая, вторая и третья клетки для выделения
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
