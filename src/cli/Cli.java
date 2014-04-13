package cli;

import java.util.ArrayList;
import java.util.Scanner;

import logic.Piece;

/**
 * Class <code>Cli</code> is the class used to make input/output to the console.
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Cli {

	private Scanner scanInt, scanString;

	public Cli() {
		scanInt = new Scanner(System.in);
		scanString = new Scanner(System.in);
	}

	/**
	 * Asks the user for a maze size.
	 * 
	 * @return odd size of the maze 10 if user chooses to play standard version
	 */
	public int setMazeSize() {
		int size;
		do {
			System.out
					.print("\n\tMaze Game\n\n > Enter maze size\n\n * Enter odd (>= 7) maze sizes only\n * Type 1 to select standard maze\n * Type 0 to exit\n\n > ");
			while (!scanInt.hasNextInt()) {
				System.out
						.print("\n\tMaze Game\n\n > Enter maze size\n\n * Enter odd (>= 7) maze sizes only\n * Type 1 to select standard maze\n * Type 0 to exit\n\n > ");
				scanInt.next();
			}
			size = scanInt.nextInt();
			if (size == 0) {
				showMessages("Exiting the game...");
				return 0;
			} else if (size == 1) {
				showMessages("Starting default maze");
				return 10;
			}
		} while (size <= 6 || size % 2 == 0);

		showMessages("Starting random maze");
		return size;
	}

	/**
	 * Asks the user for a dragon strategy.
	 * 
	 * @return dragon strategy integer
	 */
	public int dragonStrategy() {
		boolean validInput = false;
		int mode = 0;

		while (!validInput) {
			System.out
					.print("\n\tEnter dragon mode\n\n * 0 - Idle\n * 1 - Random move\n * 2 - Random move + sleeping\n\n > ");
			while (!scanInt.hasNextInt()) {
				System.out.print("\nInvalid input!\n");
				System.out
						.print("\n\tEnter dragon mode\n\n * 0 - Idle\n * 1 - Random move\n * 2 - Random move + sleeping\n\n > ");
				scanInt.next();
			}
			mode = scanInt.nextInt();

			switch (mode) {
			case 0:
				mode = 0;
				validInput = true;
				break;
			case 1:
				mode = 1;
				validInput = true;
				break;
			case 2:
				mode = 2;
				validInput = true;
				break;
			default:
				showMessages("! Invalid number");
				break;
			}
		}
		System.out.println();
		return mode;
	}

	/**
	 * Displays each <code>Piece</code> of the maze.
	 * 
	 * @param maze
	 *            of the superclass <code>Maze</code> to display in console
	 * 
	 */
	public void displayMaze(ArrayList<ArrayList<Piece>> maze) {
		System.out.println();
		for (int i = 0; i < maze.size(); i++) {
			ArrayList<Piece> linhamaze = maze.get(i);
			for (int j = 0; j < maze.get(i).size(); j++)
				System.out.print(linhamaze.get(j).getSymbol());
			System.out.println();
		}
	}

	/**
	 * Gets an input from the player used in computing the <code>Hero</code>
	 * move.
	 * 
	 * @return <code>String</code> input by the player
	 */
	public String getKey() {
		System.out
				.print("\n (w/a/s/d) to move (e) to release eagle (q) to abort > ");
		String input = scanString.nextLine();
		System.out.println();
		return input;
	}

	/**
	 * Formats and displays a <code>String</code> message from the game.
	 * 
	 * @param message
	 *            <code>String</code> to display in console
	 */
	public void showMessages(String message) {
		System.out.println("\n * " + message);
	}
}