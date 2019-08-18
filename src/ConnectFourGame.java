
/**
 * Used to construct game engine for the game ConnectFour.
 * @author Krishan Easparan
 *
 */

import java.util.Arrays;
import java.util.Observable;

public class ConnectFourGame extends Observable {
	private int nColumns;
	private int nRows;
	private int numToWin;
	private int numMarks;
	private ConnectFourEnum[][] grid;
	private ConnectFourEnum gameState;
	private ConnectFourEnum turn;

	/**
	 * Constructs an 8 by 8 game of ConnectFour.
	 * 
	 * @param initialTurn An enum representing which player goes first.
	 */

	public ConnectFourGame(ConnectFourEnum initialTurn) {
		this(8, 8, 4, initialTurn);
	}

	/**
	 * Constructs a ConnectFour game with a specified number of rows, columns,
	 * checkers needed to win and specifies which player goes first.
	 * 
	 * @param nRows       An integer representing the number of rows in the grid.
	 * @param nColumns    An integer representing the number of columns in the grid.
	 * @param numToWin    An integer representing the numbers checkers in a row that
	 *                    is needed to win the game.
	 * @param initialTurn An enum representing which player goes first.
	 */

	public ConnectFourGame(int nRows, int nColumns, int numToWin, ConnectFourEnum initialTurn) {
		if (nRows < 1 || nColumns < 1 || numToWin < 1 || numToWin > nRows || numToWin > nColumns)
			throw new IllegalArgumentException("Invalid input."); // checks if parameters are valid

		this.nRows = nRows; // initialization of variables
		this.nColumns = nColumns;
		this.numToWin = numToWin;
		grid = new ConnectFourEnum[nRows][nColumns];
		reset(initialTurn);
	}

	/**
	 * Resets the ConnectFour game.
	 * 
	 * @param initialTurn An enum representing which player goes first in the newly
	 *                    reset game.
	 */

	public void reset(ConnectFourEnum initialTurn) {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], ConnectFourEnum.EMPTY);
		}
		numMarks = 0;
		turn = initialTurn;
		gameState = ConnectFourEnum.IN_PROGRESS;
	}

	/**
	 * Determine if a player has won the game or if the game has ended in a draw.
	 * 
	 * @param placedRow    An integer representing the row of the recently placed
	 *                     checker.
	 * @param placedColumn An integer representing the column of the recently placed
	 *                     checker.
	 * @return An enum representing whether a player has won the game, if the game
	 *         has ended in a draw or if the game is still in progress.
	 */

	private ConnectFourEnum findWinner(int placedRow, int placedColumn) {

		ConnectFourEnum placedChecker = grid[placedRow][placedColumn];
		int counter = 0;

		// checks if there is a row winner
		for (int i = placedColumn; i > placedColumn - numToWin; i--) {
			if (i < 0) {
				break;
			}
			for (int j = i; j < i + numToWin; j++) {
				try {
					if (grid[placedRow][j] != placedChecker) {
						break;
					} else {
						counter++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}

			if (counter == numToWin) {
				return placedChecker;
			} else {
				counter = 0;
			}

		}

		// checks if there is a column winner
		for (int i = placedRow; i > placedRow - numToWin; i--) {
			try {
				if (grid[i][placedColumn] != placedChecker) {
					break;
				} else {
					counter++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				break;
			}
		}

		if (counter == numToWin) {
			return placedChecker;
		} else if (numMarks == nRows * nColumns) { // checks if there is a draw
			return ConnectFourEnum.DRAW;
		}
		return this.gameState;

	}

	/**
	 * Takes the turn of the current player and places the checker in the specified
	 * spot. Returns enum representing the state of the game.
	 * 
	 * @param row    An integer representing the row the checker is to be placed.
	 * @param column An integer representing the column the checker is to be placed.
	 * @return An enum representing the state of the game.
	 */

	public ConnectFourEnum takeTurn(int row, int column) {
		ConnectFourEnum previousTurn = turn;
		if (row < 0 || row >= nRows || column < 0 || column >= nColumns) {
			throw new IllegalArgumentException("Invalid input.");

		}

		if (grid[row][column] != ConnectFourEnum.EMPTY) {

			throw new IllegalArgumentException("Spot has already been filled");

		}

		if (!(row == 0 || grid[row - 1][column] != ConnectFourEnum.EMPTY)) {
			throw new IllegalArgumentException("Invalid input.");

		}

		grid[row][column] = turn;

		numMarks++;

		gameState = findWinner(row, column);

		if (turn == ConnectFourEnum.BLACK) {
			turn = ConnectFourEnum.RED;
		} else {
			turn = ConnectFourEnum.BLACK;
		}

		// notifies observer that checker has been placed.
		this.setChanged();
		this.notifyObservers(new ConnectMove(row, column, previousTurn));

		return gameState;
	}

	/**
	 * Returns the game state of the game.
	 * 
	 * @return An enum representing the game state of the game.
	 */

	public ConnectFourEnum getGameState() {
		return gameState;
	}

	/**
	 * Returns which player's turn it currently is.
	 * 
	 * @return An enum representing the player who's turn it is.
	 */

	public ConnectFourEnum getTurn() {
		return turn;
	}

	/**
	 * Returns a string representation of the grid of the game.
	 * 
	 * @return A string containing the grid of the game.
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) { // iterates through grid and
				output += ((grid[i][j]).toString() + " | "); // outputs string representation of it.
			}
			output += "\n";
		}
		return output;
	}
}
