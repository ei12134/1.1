import java.util.ArrayList;
import java.util.Random;

public class Builder extends Logic {

	public Builder() {
	};

	public void mazeSelector() {
		if (size == 10)
			defaultMaze();
		else
			generateMaze();

		heroMove();
	}

	public void defaultMaze() {

		// Fill with spaces
		for (int i = 0; i < size; i++) {
			ArrayList<Position> mazeLine = new ArrayList<Position>();
			for (int z = 0; z < size; z++)
				mazeLine.add(new Position(i, z, Piece.emptyChar));
			maze.add(mazeLine);
		}

		for (int i = 0; i < size; i++) {
			// Upper and lower maze border limits
			maze.get(i).get(0).setId(Piece.wallChar);
			maze.get(i).get(size - 1).setId(Piece.wallChar);

			// Left and right maze border limits
			maze.get(0).get(i).setId(Piece.wallChar);
			maze.get(size - 1).get(i).setId(Piece.wallChar);
		}

		maze.get(2).get(2).setId(Piece.wallChar);
		maze.get(2).get(3).setId(Piece.wallChar);
		maze.get(2).get(4).setId(Piece.wallChar);
		maze.get(2).get(6).setId(Piece.wallChar);
		maze.get(2).get(7).setId(Piece.wallChar);
		maze.get(2).get(8).setId(Piece.wallChar);

		maze.get(3).get(2).setId(Piece.wallChar);
		maze.get(3).get(3).setId(Piece.wallChar);
		maze.get(3).get(4).setId(Piece.wallChar);
		maze.get(3).get(6).setId(Piece.wallChar);
		maze.get(3).get(7).setId(Piece.wallChar);
		maze.get(3).get(8).setId(Piece.wallChar);

		maze.get(5).get(2).setId(Piece.wallChar);
		maze.get(5).get(3).setId(Piece.wallChar);
		maze.get(5).get(4).setId(Piece.wallChar);
		maze.get(5).get(6).setId(Piece.wallChar);
		maze.get(5).get(7).setId(Piece.wallChar);

		maze.get(7).get(2).setId(Piece.wallChar);
		maze.get(7).get(3).setId(Piece.wallChar);
		maze.get(7).get(4).setId(Piece.wallChar);
		maze.get(7).get(5).setId(Piece.wallChar);
		maze.get(7).get(6).setId(Piece.wallChar);
		maze.get(7).get(7).setId(Piece.wallChar);

		// Hero position
		hero = new Hero(maze.get(1).get(1), Status.alive, Piece.heroChar);

		// Eagle position
		eagle = new Eagle(new Position(hero.getPosition().getX(), hero
				.getPosition().getY(), " HG"), Status.alive, Piece.eagleChar);
		hero.setPiece(" HG");

		maze.get(hero.getPosition().getX()).get(hero.getPosition().getY())
				.setId(hero.getPiece());

		// Dragon position
		dragons.add(new Dragon(maze.get(1).get(3), Status.alive,
				Piece.dragonChar));
		maze.get(1).get(3).setId(Piece.dragonChar);

		// Exit position
		maze.get(9).get(5).setId(Piece.exitChar);

		// Sword position
		sword = new Sword(new Position(1, 8, Piece.swordChar));
		maze.get(sword.getPosition().getX()).get(sword.getPosition().getY())
				.setId(Piece.swordChar);
	}

	public void aditionalDragons(int dragonCounter) {

		for (int i = 0; i < dragonCounter; i++) {

			Position newDragonPosition = getAvailableMazePosition();

			if (newDragonPosition != null)
				dragons.add(new Dragon(newDragonPosition, Status.alive,
						Piece.dragonChar));
		}
		for (int i = 0; i < dragons.size(); i++) {
			maze.get(dragons.get(i).getPosition().getX())
					.get(dragons.get(i).getPosition().getY())
					.setId(Piece.dragonChar);
		}
	}

	public void generateMaze() {

		// size must be odd with this method
		// visited = new boolean[size + 2][size + 2];

		// Fill with walls
		for (int i = 0; i < size; i++) {
			ArrayList<Position> mazeLine = new ArrayList<Position>();
			for (int z = 0; z < size; z++)
				mazeLine.add(new Position(i, z, Piece.wallChar));
			maze.add(mazeLine);
		}

		// start position
		Random ry = new Random();
		// int randomLine = ry.nextInt(size);//substituir por size later on
		// tem de ser odd
		Random rx = new Random();
		// int randomColum = rx.nextInt(size);//substituir por size later on,
		// tem de ser odd
		int randomLine = 5;
		int randomColum = 3;
		// while (randomLine%2 ==0){
		// randomLine = ry.nextInt(size);
		// }
		// while (randomColum%2 ==0){
		// randomColum = ry.nextInt(size);
		// }

		// initial point
		maze.get(randomLine).get(randomColum).setId(Piece.emptyChar);

		int numberiterations = 9250;
		do {
			if (randomLine + 2 < size) {
				if (maze.get(randomLine + 2).get(randomColum).getId() == Piece.wallChar) {
					maze.get(randomLine + 1).get(randomColum)
							.setId(Piece.emptyChar);
					maze.get(randomLine + 2).get(randomColum)
							.setId(Piece.emptyChar);
				}
			}
			if (randomLine - 2 > 0) {
				if (maze.get(randomLine - 2).get(randomColum).getId() == Piece.wallChar) {
					maze.get(randomLine - 1).get(randomColum)
							.setId(Piece.emptyChar);
					maze.get(randomLine - 2).get(randomColum)
							.setId(Piece.emptyChar);
				}
			}
			if (randomColum + 2 < size) {
				if (maze.get(randomLine).get(randomColum + 2).getId() == Piece.wallChar) {
					maze.get(randomLine).get(randomColum + 1)
							.setId(Piece.emptyChar);
					maze.get(randomLine).get(randomColum + 2)
							.setId(Piece.emptyChar);
				}
			}
			if (randomColum - 2 > 0) {
				if (maze.get(randomLine).get(randomColum - 2).getId() == Piece.wallChar) {
					maze.get(randomLine).get(randomColum - 1)
							.setId(Piece.emptyChar);
					maze.get(randomLine).get(randomColum - 2)
							.setId(Piece.emptyChar);
				}
			}
			Random r = new Random();
			int randomNum = r.nextInt(4);

			switch (randomNum) {
			case 0:
				if ((randomLine + 2 < size)
						&& (maze.get(randomLine + 1).get(randomColum).getId() == Piece.emptyChar)) {
					randomLine += 2;
					numberiterations--;
				}
				break;

			case 1:
				if ((randomLine - 2 > 0)
						&& (maze.get(randomLine - 1).get(randomColum).getId() == Piece.emptyChar)) {

					randomLine -= 2;
					numberiterations--;
				}
				break;
			case 2:
				if ((randomColum + 2 < size)
						&& (maze.get(randomLine).get(randomColum + 1).getId() == Piece.emptyChar)) {
					randomColum += 2;
					numberiterations--;
				}
				break;
			case 3:
				if ((randomColum - 2 > 0)
						&& (maze.get(randomLine)).get(randomColum - 1).getId() == Piece.emptyChar) {

					randomColum -= 2;
					numberiterations--;
				}
				break;
			default:
				break;
			}

		} while (numberiterations > 0);

		// Hero position
		hero = new Hero(getAvailableMazePosition(), Status.alive,
				Piece.heroChar);

		// Eagle position
		eagle = new Eagle(new Position(hero.getPosition().getX(), hero
				.getPosition().getY(), " HG"), Status.alive, Piece.eagleChar);
		hero.setPiece(" HG");

		maze.get(hero.getPosition().getX()).get(hero.getPosition().getY())
				.setId(hero.getPiece());

		// Dragon position
		aditionalDragons(2);

		// Exit position
		Position exitPosition = generateExit();
		maze.get(exitPosition.getX()).get(exitPosition.getY())
				.setId(Piece.exitChar);

		// Sword position
		sword = new Sword(getAvailableMazePosition());
		maze.get(sword.getPosition().getX()).get(sword.getPosition().getY())
				.setId(Piece.swordChar);
	}

	public Position generateExit() {

		Position exitPosition = null;
		boolean done = false;
		int randomWall;
		int randomExit;

		while (!done) {
			Random rWall = new Random();
			Random ePos = new Random();
			randomWall = rWall.nextInt(3);

			switch (randomWall) {

			case 0: // x = 0
				randomExit = ePos.nextInt(size);
				if (maze.get(1).get(randomExit).getId() == Piece.emptyChar) {
					exitPosition = maze.get(0).get(randomExit);
					done = true;
				}
				break;
			case 1: // x = size - 1
				randomExit = ePos.nextInt(size);
				if (maze.get(size - 2).get(randomExit).getId() == Piece.emptyChar) {
					exitPosition = maze.get(size - 1).get(randomExit);
					done = true;
				}
				break;

			case 2: // y = 0
				randomExit = ePos.nextInt(size);
				if (maze.get(randomExit).get(0).getId() == Piece.emptyChar) {
					exitPosition = maze.get(randomExit).get(0);
					done = true;
				}
				break;
			case 3: // y = size - 1
				randomExit = ePos.nextInt(size);
				if (maze.get(randomExit).get(size - 2).getId() == Piece.emptyChar) {
					exitPosition = maze.get(randomExit).get(size - 1);
					done = true;
				}
				break;
			default:
				break;
			}
		}
		return exitPosition;
	}
}