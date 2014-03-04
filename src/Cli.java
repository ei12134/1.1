import java.util.ArrayList;
import java.util.Scanner;

public class Cli {

	public int setMazeSize() {

		Scanner scan = new Scanner(System.in);
		int size;
		// scan.close();

		do {
			System.out.print("Enter labyrinth size (0 standard maze)> ");
			while (!scan.hasNextInt()) {
				System.out.print("Enter labyrinth size (0 standard maze)> ");
				scan.next(); // this is important!
			}
			size = scan.nextInt();
		} while (size <= 0);

		if (size == 0)
			size = 10;

		return size;
	}

	public int setDragonMode() {

		boolean validInput = false;
		int mode = 0;

		while (!validInput) {

			Scanner scan = new Scanner(System.in);
			System.out
					.print("\nEnter dragon mode\n\t0 - Idle\n\t1 - Random move\n\t2 - Sleeping dragon\n> ");
			while (!scan.hasNextInt()) {
				System.out.print("\nInvalid input!\n");
				System.out
						.print("\nEnter dragon mode\n\t0 - Idle\n\t1 - Random move\n\t2 - Sleeping dragon\n> ");
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
		System.out.print("(w/a/s/d) to move (q) to abort> ");
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
