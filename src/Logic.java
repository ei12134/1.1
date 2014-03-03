import java.util.ArrayList;
import java.util.Random;

public class Logic {

	protected Cli cli;
	protected Hero hero;
	protected Dragon dragon;
	protected ArrayList<ArrayList<Position>> maze;
	protected int size;
	protected int dragonMode; //1 5 6
	
	Logic() {	
		cli = new Cli();
		maze = new ArrayList<ArrayList<Position>>();
		size = cli.setMazeSize();
		dragonMode = cli.setDragonMode();

		// Fill with spaces
		for (int i = 0; i < size; i++) {
			ArrayList<Position> mazeLine = new ArrayList<Position>();
			for (int z = 0; z < size; z++)
				mazeLine.add(new Position(i, z, Piece.emptyChar));
			maze.add(mazeLine);
		}
	};

	public void heroMove() {
		String input = "x";
		String quit = "q";
		String message = null;
		String[] heroPiece = Piece.heroChar;
		
		cli.displayMaze(size, maze);
		
		while (!input.equals(quit)) {
			input = cli.getKey();
			String right = "d";
			
			if (input.equals(right)
					&& (maze.get(hero.GetPosition().GetX() + 1)
							.get(hero.GetPosition().GetY()).GetId() != Piece.wallChar)) {

				if ((maze.get(hero.GetPosition().GetX() + 1)
						.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.GetPosition().GetX() + 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (maze.get(hero.GetPosition().GetX() + 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar) {
						input = quit;
						hero.SetStatus(Status.cleared);
						message = "Level Cleared! Congratulations!";
					}
					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetX(hero.GetPosition().GetX() + 1);

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);

				}

			} else if (input.equals("a")
					&& (maze.get(hero.GetPosition().GetX() - 1)
							.get(hero.GetPosition().GetY()).GetId() != Piece.wallChar)) {
				if ((maze.get(hero.GetPosition().GetX() - 1)
						.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.GetPosition().GetX() - 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (maze.get(hero.GetPosition().GetX() - 1)
							.get(hero.GetPosition().GetY()).GetId() == Piece.exitChar) {
						hero.SetStatus(Status.cleared);
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetX(hero.GetPosition().GetX() - 1);

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);
				}
			} else if (input.equals("s")
					&& (maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() + 1).GetId() != Piece.wallChar)) {
				if ((maze.get(hero.GetPosition().GetX())
						.get(hero.GetPosition().GetY() + 1).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() + 1).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() + 1).GetId() == Piece.exitChar) {
						hero.SetStatus(Status.cleared);
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetY(hero.GetPosition().GetY() + 1);

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);
				}

			} else if (input.equals("w")
					&& (maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() - 1).GetId() != Piece.wallChar)) {
				if ((maze.get(hero.GetPosition().GetX())
						.get(hero.GetPosition().GetY() - 1).GetId() == Piece.exitChar)
						&& (hero.GetStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() - 1).GetId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.SetStatus(Status.armed);
					} else if (maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY() - 1).GetId() == Piece.exitChar) {
						hero.SetStatus(Status.cleared);
						input = quit;
						message = "Level Cleared! Congratulations!";
					}

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY())
							.SetId(Piece.emptyChar);

					hero.GetPosition().SetY(hero.GetPosition().GetY() - 1);

					maze.get(hero.GetPosition().GetX())
							.get(hero.GetPosition().GetY()).SetId(heroPiece);
				}
			} else if (input.equals("q"))
				message = "Game aborted.";

			// check for possible adjacent dragon
			if ((dragon.GetStatus() == Status.alive
					|| dragon.GetStatus() == Status.guarding || dragon
					.GetStatus() == Status.asleep)
					&& (hero.GetStatus() != Status.cleared)) {
				if ((maze.get(hero.GetPosition().GetX() + 1).get(
						hero.GetPosition().GetY()) == maze.get(
						dragon.GetPosition().GetX()).get(
						dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX() - 1).get(
								hero.GetPosition().GetY()) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX()).get(
								hero.GetPosition().GetY() + 1) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX()).get(
								hero.GetPosition().GetY() - 1) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX() - 1).get(
								hero.GetPosition().GetY() + 1) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX() + 1).get(
								hero.GetPosition().GetY() + 1) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX() + 1).get(
								hero.GetPosition().GetY() - 1) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))
						|| (maze.get(hero.GetPosition().GetX() - 1).get(
								hero.GetPosition().GetY() - 1) == maze.get(
								dragon.GetPosition().GetX()).get(
								dragon.GetPosition().GetY()))) {
					if (hero.GetStatus() == Status.armed) {
						dragon.SetStatus(Status.dead);
						maze.get(dragon.GetPosition().GetX())
								.get(dragon.GetPosition().GetY())
								.SetId(Piece.emptyChar);
						message = "Dragon slayed!";
					} else if (dragon.status != Status.asleep) {
						hero.SetStatus(Status.dead);
						maze.get(hero.GetPosition().GetX())
								.get(hero.GetPosition().GetY())
								.SetId(Piece.emptyChar);
						message = "Hero Died. Avoid the dragon if unnarmed.";
						input = quit;
					}
				}
			}			
			if (dragon.GetStatus() != Status.dead)
				dragonMove(dragonMode);
			
			// Prints the maze and game messages if needed
			cli.displayMaze(size, maze);
			cli.gameMessages(message);
			message = null;
		}
	}

	public void dragonMove(int strategy) {

		// Generate random move from 0 to 5
		Random r = new Random();
		int randomNum = r.nextInt(strategy);
		switch (randomNum) {
		case 0:
			break;
		case 1:
			if (maze.get(dragon.GetPosition().GetX() + 1)
					.get(dragon.GetPosition().GetY()).GetId() != Piece.wallChar) {

				if (dragon.GetStatus() == Status.asleep) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
				}

				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX() + 1)
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (maze.get(dragon.GetPosition().GetX() + 1)
						.get(dragon.GetPosition().GetY()).GetId() != Piece.swordChar) {
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetX(dragon.GetPosition().GetX() + 1);
			}
			break;
		case 2:
			if (maze.get(dragon.GetPosition().GetX() - 1)
					.get(dragon.GetPosition().GetY()).GetId() != Piece.wallChar) {

				if (dragon.GetStatus() == Status.asleep) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
				}

				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX() - 1)
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (maze.get(dragon.GetPosition().GetX() - 1)
						.get(dragon.GetPosition().GetY()).GetId() != Piece.swordChar) {
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetX(dragon.GetPosition().GetX() - 1);
			}
			break;
		case 3:
			if (maze.get(dragon.GetPosition().GetX())
					.get(dragon.GetPosition().GetY() + 1).GetId() != Piece.wallChar) {

				if (dragon.GetStatus() == Status.asleep) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
				}

				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY() + 1)
							.SetId(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (maze.get(dragon.GetPosition().GetX())
						.get(dragon.GetPosition().GetY() + 1).GetId() != Piece.swordChar) {
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetY(dragon.GetPosition().GetY() + 1);
			}
			break;
		case 4:
			if (maze.get(dragon.GetPosition().GetX())
					.get(dragon.GetPosition().GetY() - 1).GetId() != Piece.wallChar) {

				if (dragon.GetStatus() == Status.asleep) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
				}

				if (dragon.GetStatus() == Status.guarding) {
					dragon.SetStatus(Status.alive);
					dragon.SetPiece(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY() - 1)
							.SetId(Piece.dragonChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.swordChar);
				} else if (maze.get(dragon.GetPosition().GetX())
						.get(dragon.GetPosition().GetY() - 1).GetId() != Piece.swordChar) {
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				} else {
					dragon.SetStatus(Status.guarding);
					dragon.SetPiece(Piece.guardingChar);
					maze.get(dragon.GetPosition().GetX())
							.get(dragon.GetPosition().GetY())
							.SetId(Piece.emptyChar);
				}
				dragon.GetPosition().SetY(dragon.GetPosition().GetY() - 1);
			}
			break;

		case 5:
			dragon.SetStatus(Status.asleep);
			dragon.SetPiece(Piece.asleepChar);
			break;
		default:
			break;
		}
		maze.get(dragon.GetPosition().GetX()).get(dragon.GetPosition().GetY())
				.SetId(dragon.GetPiece());
	}
}