import java.util.ArrayList;
import java.util.Scanner;

public class Cli {

	public boolean mainMenu() {

		Scanner scan = new Scanner(System.in);
		int input;
		// scan.close();

		do {
			System.out.print("\n\tMaze Game\n\n * Type 0 to play\n * Type 1 to exit\n > ");
			while (!scan.hasNextInt()) {
				System.out.print("\n\tMaze Game\n\n * 0 to play\n * 1 to exit\n > ");
				scan.next();
			}
			input = scan.nextInt();
		} while (input < 0 || input > 1 );
		
		if (input == 0)
		{
			Builder start = new Builder();
			start.mazeSelector();
			return false;
		}
		return true;
	}
	
	public int setMazeSize() {

		Scanner scan = new Scanner(System.in);
		int size;
		// scan.close();

		do {
			System.out.print("\n\tEnter maze size\n\n * Type 0 to select standard maze\n * Odd (>= 7) maze sizes only\n > ");
			while (!scan.hasNextInt()) {
				System.out.print("\n\tEnter maze size\n\n * Type 0 to select standard maze\n * Odd (>= 7) maze sizes only\n > ");
				scan.next();
			}
			size = scan.nextInt();
			if (size == 0)
				return 10;
		} while (size <= 6 || size % 2 == 0);

		return size;
	}

	public int setDragonMode() {

		boolean validInput = false;
		int mode = 0;

		while (!validInput) {

			Scanner scan = new Scanner(System.in);
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
				System.out.print("\nInvalid number!\n");
				break;
			}
		}
		// scan.close();
		return mode;
	}

	public void displayMaze(int size, ArrayList<ArrayList<Position>> labyrinth) {
		for (int i = 0; i < size; i++) {
			for (int z = 0; z < size; z++) {
				if ((z + 1) % size != 0)
					System.out.print(labyrinth.get(z).get(i).getId());
				else
					System.out.println(labyrinth.get(z).get(i).getId());
			}
		}
	}

	public String getKey() {
		System.out.print("(w/a/s/d) to move (e) to release eagle (q) to abort > ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		// scan.close();
		return input;
	}

	public void anyKey() {
		System.out.print("Press any key to continue...");
		Scanner anyKey = new Scanner(System.in);
		anyKey.nextLine();
	}

	public void gameMessages(String message) {
		if (message != null) {
			System.out.println(message);
			message = null;
		}
	}
}
