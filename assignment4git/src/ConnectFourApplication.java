
/**
 * Constructs a GUI application to play a game of ConnectFour. 
 * @author Krishan Easparan
 */

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ConnectFourApplication extends Application implements Observer {

	public static final int NUM_COLUMNS = 8;
	public static final int NUM_ROWS = 8;
	public static final int NUM_TO_WIN = 4;
	public static final int BUTTON_SIZE = 20;
	private ConnectFourGame gameEngine;
	private ConnectButton[][] buttons;
	private Point savedMove;
	private TextField infoBar;
	private ConnectFourEnum initialTurn;
	private Button submitTurn;

	/**
	 * Used to execute GUI application of ConnectFour.
	 * 
	 * @param primaryStage Stage used to make GUI application.
	 */
	@Override
	public void start(Stage primaryStage) {
		// initializing variables
		savedMove = new Point();
		ConnectFourEnum[] firstTurn = { ConnectFourEnum.BLACK, ConnectFourEnum.RED };
		Random random = new Random();
		initialTurn = firstTurn[random.nextInt(2)];
		gameEngine = new ConnectFourGame(NUM_ROWS, NUM_COLUMNS, NUM_TO_WIN, initialTurn);
		gameEngine.addObserver(this);
		buttons = new ConnectButton[NUM_ROWS][NUM_COLUMNS];

		try {
			submitTurn = new Button("Take My Turn");

			submitTurn.setOnAction(new EventHandler<ActionEvent>() { // submit button handler
				public void handle(ActionEvent event) {
					try {
						gameEngine.takeTurn(savedMove.getRow(), savedMove.getColumn());
						System.out.println("Drop the Checker");
					} catch (IllegalArgumentException e) {
						System.out.println("Must place checker at the bottom or" + 
						" above filled square.");
					}
				}
			});

			infoBar = new TextField(gameEngine.getTurn() + " begins.");
			ConnectButton tempBttn;
			GridPane grid = new GridPane();

			// Creating ConnectButtons for grid
			int counter = NUM_ROWS - 1;
			ButtonHandler handler = new ButtonHandler(savedMove);
			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					tempBttn = new ConnectButton("EMPTY", i, j);
					tempBttn.setMinHeight(BUTTON_SIZE);
					tempBttn.setMaxWidth(Double.MAX_VALUE);
					tempBttn.setOnAction(handler);
					buttons[i][j] = tempBttn;
					grid.add(tempBttn, j, counter);
				}
				counter--;
			}

			// Setting up GUI
			BorderPane root = new BorderPane();

			root.setTop(infoBar);
			root.setCenter(grid);
			root.setBottom(submitTurn);

			Scene scene = new Scene(root, 510, 380);
			primaryStage.setScene(scene);
			primaryStage.show();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used to launch the GUI application of ConnectFour.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		launch(args);
	}

	@Override

	/**
	 * Used to update GUI based on the changes made in the game engine.
	 * 
	 * @param observedGameEngine Observable used to determine changes made in game
	 *                           engine.
	 * @param oMove              Object which contains information of the most
	 *                           recent move.
	 */
	public void update(Observable observedGameEngine, Object oMove) {
		String message = "";
		ConnectFourGame gameEngine = (ConnectFourGame) (observedGameEngine);
		ConnectMove move = (ConnectMove) (oMove);
		buttons[move.getRow()][move.getColumn()].setText(move.getColour().toString());
		buttons[move.getRow()][move.getColumn()].setDisable(true);

		// determines if game has ended

		if (gameEngine.getGameState() != ConnectFourEnum.IN_PROGRESS) {
			if (gameEngine.getGameState() == ConnectFourEnum.BLACK) {
				message = "Player Black wins!";
			} else if (gameEngine.getGameState() == ConnectFourEnum.RED) {
				message = "Player Red wins!";
			} else if (gameEngine.getGameState() == ConnectFourEnum.DRAW) {
				message = "Game has ended in a draw.";
			}
			System.out.println("Game is finished.");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Game End");
			alert.setHeaderText("Game Over");
			alert.setContentText(message);
			alert.showAndWait();

			if (initialTurn == ConnectFourEnum.BLACK) {
				initialTurn = ConnectFourEnum.RED;

			} else {
				initialTurn = ConnectFourEnum.BLACK;

			}

			// resets game
			gameEngine.reset(initialTurn);
			infoBar.setText(gameEngine.getTurn() + " begins.");

			for (int i = 0; i < NUM_ROWS; i++) {
				for (int j = 0; j < NUM_COLUMNS; j++) {
					buttons[i][j].setText("EMPTY");
					buttons[i][j].setDisable(false);
				}
			}

		}

		infoBar.setText("It's " + gameEngine.getTurn() + "'s turn");

	}
}

/**
 * Used to contain location of move in a game of ConnectFour.
 * 
 * @author Krishan Easparan
 *
 */

class Point {
	private int row;
	private int column;

	/**
	 * Constructs a point of the location (0,0).
	 */

	Point() {
		row = 0;
		column = 0;
	}

	/**
	 * Constructs a point of the specified location.
	 */

	Point(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row of the Point.
	 * 
	 * @return An integer representing the row of the Point.
	 */

	public int getRow() {
		return row;
	}

	/**
	 * Returns the column of the Point.
	 * 
	 * @return An integer representing the row of the Point.
	 */

	public int getColumn() {
		return column;
	}

	/**
	 * Sets the row of a Point.
	 * 
	 * @param row An integer representing the row to be set for the Point.
	 */

	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Sets the column of a Point.
	 * 
	 * @param column An integer representing the column to be set for the Point.
	 */

	public void setColumn(int column) {
		this.column = column;
	}
}

/**
 * Handles the event in which a grid button is clicked in the GUI application of
 * ConnectFour.
 * 
 * @author Krishan Easparan
 *
 */

class ButtonHandler implements EventHandler<ActionEvent> {
	private Point move;

	/**
	 * Constructs a ButtonHandler.
	 * 
	 * @param move A Point to represent the location of the button.
	 */
	ButtonHandler(Point move) {
		this.move = move;
	}

	/**
	 * Executes if the button has been clicked. Stores the location of the button.
	 */

	@Override
	public void handle(ActionEvent event) {
		ConnectButton source = (ConnectButton) (event.getSource());
		move.setRow(source.getRow());
		move.setColumn(source.getColumn());

		System.out.println(source);
	}

}