import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		boolean exit = false;
		// Start the game
		while (!exit)
		{
			Cli start = new Cli();
			exit = start.mainMenu();
			start = null;//start.generateMaze();
		}
	}
}