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
	
	public boolean mainMenu() {
		int input;
		do {
			System.out
					.print("\n\tMaze Game\n\n * Type 0 to play\n * Type 1 to exit\n > ");
			while (!scanInt.hasNextInt()) {
				System.out
						.print("\n\tMaze Game\n\n * 0 to play\n * 1 to exit\n > ");
				scanInt.next();
			}
			input = scanInt.nextInt();
		} while (input < 0 || input > 1);

		if (input == 0)
			return true;
		else
			return false;
	}

	public int setMazeSize() {
		int size;
		do {
			System.out
					.print("\n\tEnter maze size\n\n * Type 0 to select standard maze\n * Odd (>= 7) maze sizes only\n > ");
			while (!scanInt.hasNextInt()) {
				System.out
						.print("\n\tEnter maze size\n\n * Type 0 to select standard maze\n * Odd (>= 7) maze sizes only\n > ");
				scanInt.next();
			}
			size = scanInt.nextInt();
			if (size == 0) {
				return 10;
			}
		} while (size <= 6 || size % 2 == 0);

		return size;
	}

	public int setDragonStrategy() {
		boolean validInput = false;
		int mode = 0;

		while (!validInput) {
			System.out
					.print("\n\tEnter dragon mode\n\n * 0 - Idle\n * 1 - Random move\n * 2 - Random move + sleeping\n > ");
			while (!scanInt.hasNextInt()) {
				System.out.print("\nInvalid input!\n");
				System.out
						.print("\n\tEnter dragon mode\n\n * 0 - Idle\n * 1 - Random move\n * 2 - Random move + sleeping\n > ");
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
				.print("\n(w/a/s/d) to move (e) to release eagle (q) to abort > ");
		String input = scanString.nextLine();
		return input;
	}

	public void anyKey() {
		gameMessages("Press any key to continue...");
		scanString.nextLine();
	}

	public void gameMessages(String message) {
		System.out.println(message);
	}

	public void errorMessages(String message) {
		System.err.println(message);
	}
}