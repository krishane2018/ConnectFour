import java.util.Random;
import java.util.Scanner;

/**
 * Used to test game engine for the game ConnectFour.
 * 
 * @author Krishan Easparan
 *
 */

public class ConnectFourTestClient {

	/**
	 * Executes string represented game of ConnectFour.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		ConnectFourEnum[] firstTurn = { ConnectFourEnum.BLACK, ConnectFourEnum.RED };
		Random random = new Random();

		ConnectFourGame game = new ConnectFourGame(firstTurn[random.nextInt(2)]);

		Scanner scanner = new Scanner(System.in);

		do {
			try {
				System.out.println(game.toString()); // prints out grid
				System.out.println(game.getTurn() + ": Where do you want to mark?" + 
				" Enter row column");

				int row = scanner.nextInt();
				int column = scanner.nextInt();
				scanner.nextLine(); // takes input
				game.takeTurn(row, column); // takes turn
			} catch (IllegalArgumentException e) {
				System.out.println("Must place checker at the bottom or" +
				" above filled square in the grid.");
			}
			// code runs while gameState is IN-PROGRESS.
		} while (game.getGameState() == ConnectFourEnum.IN_PROGRESS);
		System.out.println(game.getGameState());
		scanner.close();
	}

}
