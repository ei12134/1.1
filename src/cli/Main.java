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
			else
				game = new Logic(size, 5, cli.dragonStrategy());

			while (true) {
				// Display Maze
				cli.displayMaze(game.getMaze());
				// Ask the player for a move
				playerInput = cli.getKey();
				message = game.playGame(playerInput);

				if (message[0] != null) {
					if (message[0].equals("Abort")) {
						cli.showMessages("Game aborted!");
						break;
					} else {
						cli.displayMaze(game.getMaze());
						if (message[0].equals("Hero won :)")
								|| message[0].equals("Hero died!")) {
							cli.showMessages(message[0]);
							break;
						} else
							cli.showMessages(message[0]);
					}
				}
				for (int i = 1; i < message.length; i++) {
					if (message[i] != null)
						cli.showMessages(message[i]);
				}
			}
		}
	}
}
