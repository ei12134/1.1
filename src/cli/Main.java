package cli;

import maze.Logic;

public class Main {
	public static void main(String[] args) {
		int size;
		Logic game;
		Cli cli = new Cli();
		while ((size = cli.setMazeSize()) != 0) {		
			game = new Logic(size);
			if (size != 10)
				game.setDragonStrategy(cli.dragonStrategy());
			game.startGame();
		}
	}
}
