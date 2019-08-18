
/**
 * A Button with added attributes to be more effective in ConnectFourApplication.
 * @author Krishan Easparan
 */
import javafx.scene.control.Button;

public class ConnectButton extends Button {

	private int row;
	private int column;

	/**
	 * Constructs a ConnectButton with the specified label.
	 * 
	 * @param label  A String representing the label of the ConnectButton.
	 * @param row    An integer representing the row of the ConnectButton.
	 * @param column An integer representing the column of the ConnectButton.
	 */

	public ConnectButton(String label, int row, int column) {
		super(label);
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row of the ConnectButton.
	 * 
	 * @return An integer representing the row of the ConnectButton.
	 */

	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the ConnectButton.
	 * 
	 * @return An integer representing the column of the ConnectButton.
	 */

	public int getColumn() {
		return column;
	}

	/**
	 * Returns a string representation of the location of the ConnectButton.
	 * 
	 * @return A string containing the location of the ConnectButton.
	 */

	public String toString() {
		return ("(" + row + "," + column + ")");
	}

}
