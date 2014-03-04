import java.util.ArrayList;
import java.util.Random;

public class Logic {

	protected Cli cli;
	protected Hero hero;
	protected ArrayList<ArrayList<Position>> maze;
	protected ArrayList<Dragon> dragons;
	ArrayList<String[]> nearDragon;
	ArrayList<String[]> badNearPiece;
	protected int size;
	protected int dragonMode;

	Logic() {
		cli = new Cli();
		maze = new ArrayList<ArrayList<Position>>();
		dragons = new ArrayList<Dragon>();
		size = cli.setMazeSize();
		dragonMode = cli.setDragonMode();

		// Fill with spaces
		for (int i = 0; i < size; i++) {
			ArrayList<Position> mazeLine = new ArrayList<Position>();
			for (int z = 0; z < size; z++)
				mazeLine.add(new Position(i, z, Piece.emptyChar));
			maze.add(mazeLine);
		}

		nearDragon = new ArrayList<String[]>();
		nearDragon.add(Piece.dragonChar);
		nearDragon.add(Piece.guardingChar);
		nearDragon.add(Piece.asleepChar);
		
		badNearPiece = new ArrayList<String[]>();
		badNearPiece.add(Piece.wallChar);
		badNearPiece.add(Piece.dragonChar);
		badNearPiece.add(Piece.guardingChar);
		badNearPiece.add(Piece.asleepChar);

	};

	public void heroMove() {
		String message = null;
		String input;

		String[] heroPiece = Piece.heroChar;
		boolean done = false;
		cli.displayMaze(size, maze);

		while (!done) {
			input = cli.getKey();
			String right = "d";

			if (input.equals(right)
					&& (maze.get(hero.getPosition().getX() + 1)
							.get(hero.getPosition().getY()).getId() != Piece.wallChar)) {

				if ((maze.get(hero.getPosition().getX() + 1)
						.get(hero.getPosition().getY()).getId() == Piece.exitChar)
						&& (hero.getStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.getPosition().getX() + 1)
							.get(hero.getPosition().getY()).getId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.setStatus(Status.armed);
					} else if (maze.get(hero.getPosition().getX() + 1)
							.get(hero.getPosition().getY()).getId() == Piece.exitChar) {
						done = true;
						hero.setStatus(Status.cleared);
						message = "Level Cleared! Congratulations!";
					}
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);

					hero.getPosition().setX(hero.getPosition().getX() + 1);

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY()).setId(heroPiece);

				}

			} else if (input.equals("a")
					&& (maze.get(hero.getPosition().getX() - 1)
							.get(hero.getPosition().getY()).getId() != Piece.wallChar)) {
				if ((maze.get(hero.getPosition().getX() - 1)
						.get(hero.getPosition().getY()).getId() == Piece.exitChar)
						&& (hero.getStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.getPosition().getX() - 1)
							.get(hero.getPosition().getY()).getId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.setStatus(Status.armed);
					} else if (maze.get(hero.getPosition().getX() - 1)
							.get(hero.getPosition().getY()).getId() == Piece.exitChar) {
						hero.setStatus(Status.cleared);
						done = true;
						message = "Level Cleared! Congratulations!";
					}

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);

					hero.getPosition().setX(hero.getPosition().getX() - 1);

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY()).setId(heroPiece);
				}
			} else if (input.equals("s")
					&& (maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY() + 1).getId() != Piece.wallChar)) {
				if ((maze.get(hero.getPosition().getX())
						.get(hero.getPosition().getY() + 1).getId() == Piece.exitChar)
						&& (hero.getStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY() + 1).getId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.setStatus(Status.armed);
					} else if (maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY() + 1).getId() == Piece.exitChar) {
						hero.setStatus(Status.cleared);
						done = true;
						message = "Level Cleared! Congratulations!";
					}

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);

					hero.getPosition().setY(hero.getPosition().getY() + 1);

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY()).setId(heroPiece);
				}

			} else if (input.equals("w")
					&& (maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY() - 1).getId() != Piece.wallChar)) {
				if ((maze.get(hero.getPosition().getX())
						.get(hero.getPosition().getY() - 1).getId() == Piece.exitChar)
						&& (hero.getStatus() != Status.armed)) {
					message = "Sword missing!";
				} else {
					if (maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY() - 1).getId() == Piece.swordChar) {
						heroPiece = Piece.armedChar;
						hero.setStatus(Status.armed);
					} else if (maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY() - 1).getId() == Piece.exitChar) {
						hero.setStatus(Status.cleared);
						done = true;
						message = "Level Cleared! Congratulations!";
					}

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);

					hero.getPosition().setY(hero.getPosition().getY() - 1);

					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY()).setId(heroPiece);
				}
			} else if (input.equals("q")) {
				message = "Game aborted.";
				done = true;
			}

			for (int i = 0; i < dragons.size(); i++) {
				// Check for adjacent dragon
				Dragon dragon;
				dragon = dragons.get(i);

				if ((dragon.getStatus() == Status.alive
						|| dragon.getStatus() == Status.guarding || dragon
						.getStatus() == Status.asleep)
						&& (hero.getStatus() != Status.cleared)) {
					if ((nearPieces(maze.get(hero.getPosition().getX() + 1)
							.get(hero.getPosition().getY()), nearDragon))
							|| nearPieces(
									maze.get(hero.getPosition().getX() - 1)
											.get(hero.getPosition().getY()),
									nearDragon)
							|| nearPieces(maze.get(hero.getPosition().getX())
									.get(hero.getPosition().getY() + 1),
									nearDragon)
							|| nearPieces(maze.get(hero.getPosition().getX())
									.get(hero.getPosition().getY() - 1),
									nearDragon)
							|| nearPieces(
									maze.get(hero.getPosition().getX() - 1)
											.get(hero.getPosition().getY() + 1),
									nearDragon)
							|| nearPieces(
									maze.get(hero.getPosition().getX() + 1)
											.get(hero.getPosition().getY() + 1),
									nearDragon)
							|| nearPieces(
									maze.get(hero.getPosition().getX() + 1)
											.get(hero.getPosition().getY() - 1),
									nearDragon)
							|| nearPieces(
									maze.get(hero.getPosition().getX() - 1)
											.get(hero.getPosition().getY() - 1),
									nearDragon)) {
						if (hero.getStatus() == Status.armed) {
							dragon.setStatus(Status.dead);
							maze.get(dragon.getPosition().getX())
									.get(dragon.getPosition().getY())
									.setId(Piece.emptyChar);
							message = "Dragon slayed!";
							dragons.remove(i);
						} else if (dragon.status != Status.asleep) {
							hero.setStatus(Status.dead);
							maze.get(hero.getPosition().getX())
									.get(hero.getPosition().getY())
									.setId(Piece.emptyChar);
							message = "Hero Died. Avoid the dragon if unnarmed.";
							done = true;
						}
					}
				}
				if ((dragon.getStatus() != Status.dead) && (dragonMode != 1)) {
					dragonMove(dragon);
				}
			}
			// Prints the maze and game messages if needed
			cli.displayMaze(size, maze);
			if (message != null) {
				cli.gameMessages(message);
				cli.anyKey();
				message = null;
			}
		}
	}

	public void dragonMove(Dragon dragon) {

		// Generate random move from 0 to 5
		Random r = new Random();
		int randomNum = r.nextInt(dragonMode);
		switch (randomNum) {
		case 0:
			break;
		case 1:
			if (! nearPieces(maze.get(dragon.getPosition().getX() + 1)
					.get(dragon.getPosition().getY()), badNearPiece)) {
				if (dragon.getStatus() == Status.asleep) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
				}

				if (dragon.getStatus() == Status.guarding) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
					maze.get(dragon.getPosition().getX() + 1)
							.get(dragon.getPosition().getY())
							.setId(Piece.dragonChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.swordChar);
				} else if (maze.get(dragon.getPosition().getX() + 1)
						.get(dragon.getPosition().getY()).getId() != Piece.swordChar) {
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				} else {
					dragon.setStatus(Status.guarding);
					dragon.setPiece(Piece.guardingChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				}
				dragon.getPosition().setX(dragon.getPosition().getX() + 1);
			}
			break;
		case 2:
			if (! nearPieces(maze.get(dragon.getPosition().getX() - 1)
					.get(dragon.getPosition().getY()), badNearPiece)) {

				if (dragon.getStatus() == Status.asleep) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
				}

				if (dragon.getStatus() == Status.guarding) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
					maze.get(dragon.getPosition().getX() - 1)
							.get(dragon.getPosition().getY())
							.setId(Piece.dragonChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.swordChar);
				} else if (maze.get(dragon.getPosition().getX() - 1)
						.get(dragon.getPosition().getY()).getId() != Piece.swordChar) {
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				} else {
					dragon.setStatus(Status.guarding);
					dragon.setPiece(Piece.guardingChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				}
				dragon.getPosition().setX(dragon.getPosition().getX() - 1);
			}
			break;
		case 3:
			if (! nearPieces(maze.get(dragon.getPosition().getX())
					.get(dragon.getPosition().getY() + 1), badNearPiece)) {

				if (dragon.getStatus() == Status.asleep) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
				}

				if (dragon.getStatus() == Status.guarding) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY() + 1)
							.setId(Piece.dragonChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.swordChar);
				} else if (maze.get(dragon.getPosition().getX())
						.get(dragon.getPosition().getY() + 1).getId() != Piece.swordChar) {
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				} else {
					dragon.setStatus(Status.guarding);
					dragon.setPiece(Piece.guardingChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				}
				dragon.getPosition().setY(dragon.getPosition().getY() + 1);
			}
			break;
		case 4:
			if (! nearPieces(maze.get(dragon.getPosition().getX())
					.get(dragon.getPosition().getY() - 1), badNearPiece)) {

				if (dragon.getStatus() == Status.asleep) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
				}

				if (dragon.getStatus() == Status.guarding) {
					dragon.setStatus(Status.alive);
					dragon.setPiece(Piece.dragonChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY() - 1)
							.setId(Piece.dragonChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.swordChar);
				} else if (maze.get(dragon.getPosition().getX())
						.get(dragon.getPosition().getY() - 1).getId() != Piece.swordChar) {
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				} else {
					dragon.setStatus(Status.guarding);
					dragon.setPiece(Piece.guardingChar);
					maze.get(dragon.getPosition().getX())
							.get(dragon.getPosition().getY())
							.setId(Piece.emptyChar);
				}
				dragon.getPosition().setY(dragon.getPosition().getY() - 1);
			}
			break;

		case 5:
			dragon.setStatus(Status.asleep);
			dragon.setPiece(Piece.asleepChar);
			break;
		default:
			break;
		}
		maze.get(dragon.getPosition().getX()).get(dragon.getPosition().getY())
				.setId(dragon.getPiece());
	}

	boolean nearPieces(Position position, ArrayList<String[]> pieces) {

		for (int i = 0; i < pieces.size(); i++)
			if (position.getId().equals(pieces.get(i)))
				return true;

		return false;
	}

	Position getAvailableMazePosition() {
		int randomX = 0, randomY = 0;
		Random r = new Random();

		for (int i = 0; i < size * size; i++) {
			randomX = r.nextInt(size);
			randomY = r.nextInt(size);

			if (maze.get(randomX).get(randomY).getId() == Piece.emptyChar)
				return maze.get(randomX).get(randomY);
		}
		return null;
	}

	public void pauseGame(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			System.out.println("Exception caught on pauseGame");
		}
	}
}