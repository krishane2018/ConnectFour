/**
 * Used to store the recent move in a game of ConnectFour.
 * 
 * @author Krishan Easparan
 *
 */

public class ConnectMove {
	private int row;
	private int column;
	private ConnectFourEnum colour;

	/**
	 * Constructs a ConnectMove with the specified row, column and colour.
	 * 
	 * @param row    An integer containing the row of the most recent move.
	 * @param column An integer containing the column of the most recent move.
	 * @param colour An enum containing the colour of the most recent move.
	 */

	public ConnectMove(int row, int column, ConnectFourEnum colour) {
		this.row = row;
		this.column = column;
		this.colour = colour;
	}

	/**
	 * Returns the row of the ConnectMove.
	 * 
	 * @return An integer representing the row of the ConnectMove.
	 */

	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the ConnectMove.
	 * 
	 * @return An integer representing the column of the ConnectMove.
	 */

	public int getColumn() {
		return column;
	}

	/**
	 * Returns the colour the ConnectMove.
	 * 
	 * @return An enum representing the colour of the ConnectMove.
	 */

	public ConnectFourEnum getColour() {
		return colour;
	}

}
