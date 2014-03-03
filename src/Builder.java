import java.util.ArrayList;
import java.util.Random;

public class Builder extends Logic {

	public Builder() {
	};

	public void defaultMaze() {

		for (int i = 0; i < size; i++) {
			// Upper and lower maze border limits
			maze.get(i).get(0).SetId(Piece.wallChar);
			maze.get(i).get(size - 1).SetId(Piece.wallChar);

			// Left and right maze border limits
			maze.get(0).get(i).SetId(Piece.wallChar);
			maze.get(size - 1).get(i).SetId(Piece.wallChar);
		}

		maze.get(2).get(2).SetId(Piece.wallChar);
		maze.get(2).get(3).SetId(Piece.wallChar);
		maze.get(2).get(4).SetId(Piece.wallChar);
		maze.get(2).get(6).SetId(Piece.wallChar);
		maze.get(2).get(7).SetId(Piece.wallChar);
		maze.get(2).get(8).SetId(Piece.wallChar);

		maze.get(3).get(2).SetId(Piece.wallChar);
		maze.get(3).get(3).SetId(Piece.wallChar);
		maze.get(3).get(4).SetId(Piece.wallChar);
		maze.get(3).get(6).SetId(Piece.wallChar);
		maze.get(3).get(7).SetId(Piece.wallChar);
		maze.get(3).get(8).SetId(Piece.wallChar);

		maze.get(5).get(2).SetId(Piece.wallChar);
		maze.get(5).get(3).SetId(Piece.wallChar);
		maze.get(5).get(4).SetId(Piece.wallChar);
		maze.get(5).get(6).SetId(Piece.wallChar);
		maze.get(5).get(7).SetId(Piece.wallChar);

		maze.get(7).get(2).SetId(Piece.wallChar);
		maze.get(7).get(3).SetId(Piece.wallChar);
		maze.get(7).get(4).SetId(Piece.wallChar);
		maze.get(7).get(5).SetId(Piece.wallChar);
		maze.get(7).get(6).SetId(Piece.wallChar);
		maze.get(7).get(7).SetId(Piece.wallChar);

		// Hero position
		hero = new Hero(maze.get(1).get(1), Status.alive);
		maze.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY())
				.SetId(Piece.heroChar);

		// Dragon position
		dragon = new Dragon(maze.get(1).get(3), Status.alive, Piece.dragonChar);
		maze.get(dragon.GetPosition().GetX()).get(dragon.GetPosition().GetY())
				.SetId(Piece.dragonChar);

		// Exit position
		maze.get(9).get(5).SetId(Piece.exitChar);

		// Sword position
		maze.get(1).get(8).SetId(Piece.swordChar);
	}

	public void generateMaze() {
		// boolean[][] visited;

		maze = new ArrayList<ArrayList<Position>>();
		size = 11; // gotta be odd with this metod, later on will b an user
					// choise(aslong it is odd)
		// visited = new boolean[size + 2][size + 2];
		// // fill with spaces
		for (int i = 0; i < size; i++) {
			ArrayList<Position> mazeLine = new ArrayList<Position>();
			for (int z = 0; z < size; z++)
				mazeLine.add(new Position(i, z, Piece.wallChar));
			maze.add(mazeLine);
		}

		// start position
		Random ry = new Random();
		// int randomNumline = ry.nextInt(size);//substituir por size later on
		// tem de ser odd
		Random rx = new Random();
		// int randomNumcolum = rx.nextInt(size);//substituir por size later on,
		// tem de ser odd
		int randomNumline = 5;
		int randomNumcolum = 3;
		// while (randomNumline%2 ==0){
		// randomNumline = ry.nextInt(size);
		// }
		// while (randomNumcolum%2 ==0){
		// randomNumcolum = ry.nextInt(size);
		// }

		maze.get(randomNumline).get(randomNumcolum).SetId(Piece.emptyChar);
		// visited[randomNumline][randomNumcolum] = true; // marca o ponto
		// inicial
		// como visitado

		// boolean completed = false;
		// boolean completedr = false;
		// boolean completedl = false;
		// boolean completedu = false;
		// boolean comp,letedd = false;
		int numberiterations = 99250;
		do {
			Random r = new Random();
			int randomNum = r.nextInt(4);

			switch (randomNum) {
			case 0:
				if (randomNumline + 2 < size) {
					if ((randomNumline + 2 < size)
							&& (maze.get(randomNumline + 2).get(randomNumcolum)
									.GetId() == Piece.wallChar)) {
						maze.get(randomNumline + 1).get(randomNumcolum)
								.SetId(Piece.emptyChar);
						maze.get(randomNumline + 2).get(randomNumcolum)
								.SetId(Piece.emptyChar);
						numberiterations--;
					}

					if ((maze.get(randomNumline - 1).get(randomNumcolum)
							.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline + 1).get(randomNumcolum)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum + 1)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum - 1)
									.GetId() == Piece.emptyChar))

					{
						randomNumline += 2;
						numberiterations--;
					}

				}
				numberiterations--;
				break;

			case 1:
				if (randomNumline - 2 > 0) {
					if ((randomNumline - 2 > 0)
							&& (maze.get(randomNumline - 2).get(randomNumcolum)
									.GetId() == Piece.wallChar)) {
						maze.get(randomNumline - 1).get(randomNumcolum)
								.SetId(Piece.emptyChar);
						maze.get(randomNumline - 2).get(randomNumcolum)
								.SetId(Piece.emptyChar);

					}
					if ((maze.get(randomNumline - 1).get(randomNumcolum)
							.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline + 1).get(randomNumcolum)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum + 1)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum - 1)
									.GetId() == Piece.emptyChar)) {
						randomNumline -= 2;
						numberiterations--;
					}

				}
				numberiterations--;
				break;
			case 2:
				if (randomNumcolum + 2 < size) {
					if ((randomNumcolum + 2 < size)
							&& (maze.get(randomNumline).get(randomNumcolum + 2)
									.GetId() == Piece.wallChar)) {
						maze.get(randomNumline).get(randomNumcolum + 1)
								.SetId(Piece.emptyChar);
						maze.get(randomNumline).get(randomNumcolum + 2)
								.SetId(Piece.emptyChar);
					}
					if ((maze.get(randomNumline - 1).get(randomNumcolum)
							.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline + 1).get(randomNumcolum)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum + 1)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum - 1)
									.GetId() == Piece.emptyChar)) {
						randomNumcolum += 2;
						numberiterations--;
					}

				}
				numberiterations--;
				break;
			case 3:
				if (randomNumcolum - 2 > 0) {
					if ((randomNumcolum - 2 > 0)
							&& (maze.get(randomNumline))
									.get(randomNumcolum - 2).GetId() == Piece.wallChar) {
						maze.get(randomNumline).get(randomNumcolum - 1)
								.SetId(Piece.emptyChar);
						maze.get(randomNumline).get(randomNumcolum - 2)
								.SetId(Piece.emptyChar);
					}
					if ((maze.get(randomNumline - 1).get(randomNumcolum)
							.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline + 1).get(randomNumcolum)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum + 1)
									.GetId() == Piece.emptyChar)
							&& (maze.get(randomNumline).get(randomNumcolum - 1)
									.GetId() == Piece.emptyChar)) {
						randomNumcolum -= 2;
						numberiterations--;
					}
				}
				numberiterations--;
				break;
			default:
				break;
			}

		} while (numberiterations > 0);
		// for (int i = 0; i < size; i++) {
		// // upper and lower maze border limits
		// maze.get(i).get(0).SetId(Piece.wallChar);
		// maze.get(i).get(size - 1).SetId(Piece.wallChar);
		//
		// // left and right maze border limits
		// maze.get(0).get(i).SetId(Piece.wallChar);
		// maze.get(size - 1).get(i).SetId(Piece.wallChar);
		// }
		// //
		// maze.get(2).get(2).SetId(Piece.wallChar);
		// maze.get(2).get(3).SetId(Piece.wallChar);
		// maze.get(2).get(4).SetId(Piece.wallChar);
		// maze.get(2).get(6).SetId(Piece.wallChar);
		// maze.get(2).get(7).SetId(Piece.wallChar);
		// maze.get(2).get(8).SetId(Piece.wallChar);
		// //
		// maze.get(3).get(2).SetId(Piece.wallChar);
		// maze.get(3).get(3).SetId(Piece.wallChar);
		// maze.get(3).get(4).SetId(Piece.wallChar);
		// maze.get(3).get(6).SetId(Piece.wallChar);
		// maze.get(3).get(7).SetId(Piece.wallChar);
		// maze.get(3).get(8).SetId(Piece.wallChar);
		// //
		// maze.get(5).get(2).SetId(Piece.wallChar);
		// maze.get(5).get(3).SetId(Piece.wallChar);
		// maze.get(5).get(4).SetId(Piece.wallChar);
		// maze.get(5).get(6).SetId(Piece.wallChar);
		// maze.get(5).get(7).SetId(Piece.wallChar);
		// //
		// maze.get(7).get(2).SetId(Piece.wallChar);
		// maze.get(7).get(3).SetId(Piece.wallChar);
		// maze.get(7).get(4).SetId(Piece.wallChar);
		// maze.get(7).get(5).SetId(Piece.wallChar);
		// maze.get(7).get(6).SetId(Piece.wallChar);
		// maze.get(7).get(7).SetId(Piece.wallChar);

		// // hero position
		// hero = new Hero(maze.get(1).get(1), Status.alive);
		// maze.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY())
		// .SetId(Piece.heroChar);

		// maze.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.emptyChar);
		// hero.SetPosition(maze.get(1).get(2));
		// maze.get(hero.GetPosition().GetX()).get(hero.GetPosition().GetY()).SetId(Piece.heroChar);

		// dragon position
		// dragon = new Dragon(maze.get(1).get(3), Status.alive,
		// Piece.dragonChar);
		// maze.get(dragon.GetPosition().GetX())
		// .get(dragon.GetPosition().GetY()).SetId(Piece.dragonChar);

		// exit position
		// maze.get(9).get(5).SetId(Piece.exitChar);

		// sword position
		// maze.get(1).get(8).SetId(Piece.swordChar);
		cli.displayMaze(size, maze);
	}
}