package cli;

import maze.Logic;

public class Main {
	public static void main(String[] args) {
		int size;
		Logic game;
		Cli cli = new Cli();
		while ((size = cli.setMazeSize()) != 0) {
			if (size == 10)
				game = new Logic(cli);
			else {
				game = new Logic(cli, size);
				game.setDragonStrategy(cli.dragonStrategy());
			}
			game.startGame();
		}
	}
}
