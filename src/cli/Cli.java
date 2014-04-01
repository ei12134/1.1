package cli;

import java.util.ArrayList;
import java.util.Scanner;

import maze.Piece;

public class Cli {

	private Scanner scanInt, scanString;

	public Cli() {
		scanInt = new Scanner(System.in);
		scanString = new Scanner(System.in);
	}

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
				System.out.print("\n ! Exiting the game...\n");
				return 0;
			} else if (size == 1) {
				System.out.print("\n ! Starting default maze\n\n");
				return 10;
			}
		} while (size <= 6 || size % 2 == 0);

		System.out.print("\n ! Starting random maze\n");
		return size;
	}

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
				errorMessages("\nInvalid number!\n");
				break;
			}
		}
		System.out.println();
		return mode;
	}

	public void displayMaze(ArrayList<ArrayList<Piece>> maze) {
		for (int i = 0; i < maze.size(); i++) {
			ArrayList<Piece> linhamaze = maze.get(i);
			for (int j = 0; j < maze.get(i).size(); j++)
				System.out.print(linhamaze.get(j).getSymbol());
			System.out.println();
		}
	}

	public String getKey() {
		System.out
				.print("\n * (w/a/s/d) to move (e) to release eagle (q) to abort > ");
		String input = scanString.nextLine();
		System.out.println();
		return input;
	}

	public void anyKey() {
		gameMessages("Press any key to continue...");
		scanString.nextLine();
	}

	public void gameMessages(String message) {
		System.out.println("\n " + message + "\n");
	}

	public void errorMessages(String message) {
		System.err.println("\n\n " + message + "\n");
	}
}