package cli;

import maze.Logic;

public class Main {
	public static void main(String[] args) {
		int size;
		String playerInput;
		String[] message;
		Logic game;
		Cli cli = new Cli();
		while ((size = cli.setMazeSize()) != 0) {
			if (size == 10)
				game = new Logic();
			else {
				game = new Logic(size);
				game.setDragonStrategy(cli.dragonStrategy());
			}

			do {
				// Display Maze
				cli.displayMaze(game.getMaze());
				// Move hero
				playerInput = cli.getKey();
				message = game.playGame(playerInput);

				if (!message[0].equals("exit") && !message[0].equals("none"))
					cli.displayMaze(game.getMaze());

				for (int i = 0; i < message.length; i++) {
					if (message[i].charAt(0) == '!')
						cli.errorMessages(message[i]);
					else if (message[i].charAt(0) == '*')
						cli.gameMessages(message[i]);
				}
			} while (!message[0].equals("exit")
					&& !message[0].equals("* Hero won :)")
					&& !message[0].equals("! Hero died :("));

		}
	}
}
