package maze;

import cli.Cli;

public class Main {

	public static void main(String[] args) {
		int size;
		Logic game;
		Cli cli = new Cli();
		while(cli.mainMenu()){		
			size = cli.setMazeSize();
			game = new Logic(size);
			if (size != 10)
				game.setDragonStrategy(cli.dragonStrategy());
			game.startGame();			
		}
	}
}
