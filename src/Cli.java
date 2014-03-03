import java.util.ArrayList;
import java.util.Scanner;

public class Cli {

	public int setMazeSize() {
		System.out.print("Enter labyrinth size (0 standard maze)> ");
		Scanner scan = new Scanner(System.in);
		int size = scan.nextInt();
		// scan.close();
		return size;
	}

	public int setDragonMode() {

		boolean validInput = false;
		int mode = 0;

		while (!validInput) {
			System.out
					.print("\nEnter dragon mode\n0 - Idle\n1 - Random move\n2 - Sleeping dragon\n> ");
			Scanner scan = new Scanner(System.in);
			mode = scan.nextInt();
			switch (mode) {
			case 0:
				mode = 0;
				validInput = true;
			case 1:
				mode = 5;
				validInput = true;
			case 2:
				mode = 6;
				validInput = true;
			default:
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
					System.out.print(labyrinth.get(z).get(i).GetId()[0]);
				else
					System.out.println(labyrinth.get(z).get(i).GetId()[0]);
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

	public void gameMessages(String message) {
		if (message != null) {
			System.out.println(message);
			message = null;
		}
	}
}
