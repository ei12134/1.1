package cli;

import java.util.ArrayList;
import java.util.Scanner;

import maze.Piece;

public class Cli {

	private Scanner scan;

	public Cli() {
		scan = new Scanner(System.in);
	}
	
	public boolean mainMenu() {
		int input;
		do {
			System.out
					.print("\n\tMaze Game\n\n * Type 0 to play\n * Type 1 to exit\n > ");
			while (!scan.hasNextInt()) {
				System.out
						.print("\n\tMaze Game\n\n * 0 to play\n * 1 to exit\n > ");
				scan.next();
			}
			input = scan.nextInt();
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
			while (!scan.hasNextInt()) {
				System.out
						.print("\n\tEnter maze size\n\n * Type 0 to select standard maze\n * Odd (>= 7) maze sizes only\n > ");
				scan.next();
			}
			size = scan.nextInt();
			if (size == 0) {
				return 10;
			}
		} while (size <= 6 || size % 2 == 0);

		return size;
	}

	public int setDragonMode() {

		boolean validInput = false;
		int mode = 0;

		while (!validInput) {
			System.out
					.print("\n\tEnter dragon mode\n\n * 0 - Idle\n * 1 - Random move\n * 2 - Sleeping dragon\n\n> ");
			while (!scan.hasNextInt()) {
				System.out.print("\nInvalid input!\n");
				System.out
						.print("\n\tEnter dragon mode\n\n * 0 - Idle\n * 1 - Random move\n * 2 - Sleeping dragon\n\n > ");
				scan.next(); // this is important!
			}
			mode = scan.nextInt();

			switch (mode) {
			case 0:
				mode = 1;
				validInput = true;
				break;
			case 1:
				mode = 5;
				validInput = true;
				break;
			case 2:
				mode = 6;
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
				System.out.print(linhamaze.get(j).getSymbol() + " ");

			System.out.println();
		}
	}

	public String getKey() {
		System.out
				.print("\n(w/a/s/d) to move (e) to release eagle (q) to abort > ");
		String input = scan.nextLine();
		return input;
	}

	public void anyKey() {
		gameMessages("Press any key to continue...");
		scan.nextLine();
	}

	public void gameMessages(String message) {
		System.out.println(message);
	}

	public void errorMessages(String message) {
		System.err.println(message);
	}
}