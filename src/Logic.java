import java.util.ArrayList;
import java.util.Random;

public class Logic {

	protected Cli cli;
	protected Hero hero;
	protected Eagle eagle;
	protected Sword sword;
	protected ArrayList<ArrayList<Position>> maze;
	protected ArrayList<Dragon> dragons;
	protected ArrayList<String> nearDragon;
	protected ArrayList<String> badNearPiece;
	protected int size;
	protected int dragonMode;

	Logic() {
		cli = new Cli();
		maze = new ArrayList<ArrayList<Position>>();
		dragons = new ArrayList<Dragon>();
		size = cli.setMazeSize();
		dragonMode = cli.setDragonMode();

		nearDragon = new ArrayList<String>();
		nearDragon.add(Piece.dragonChar);
		nearDragon.add(Piece.guardingChar);
		nearDragon.add(Piece.asleepChar);

		badNearPiece = new ArrayList<String>();
		badNearPiece.add(Piece.wallChar);
		badNearPiece.add(Piece.dragonChar);
		badNearPiece.add(Piece.guardingChar);
		badNearPiece.add(Piece.asleepChar);
		badNearPiece.add(Piece.exitChar);

	};

	public void heroMove() {
		String message = null;
		String input;
		String nearPiece;
		boolean done = false;
		boolean move = false;
		cli.displayMaze(size, maze);

		while (!done) {
			input = cli.getKey();
			move = false;

			switch (input) {

			case "d":

				nearPiece = maze.get(hero.getPosition().getX() + 1)
						.get(hero.getPosition().getY()).getId();

				if (nearPiece == Piece.emptyChar)
					move = true;
				else if (nearPiece == Piece.swordChar) {
					hero.setPiece(Piece.armedChar);
					hero.setStatus(Status.armed);
					move = true;
				} else if (nearPiece == Piece.exitChar
						&& hero.getStatus() != Status.armed)
					message = "Sword missing!";
				else if (nearPiece == Piece.exitChar) {
					done = true;
					move = true;
					hero.setStatus(Status.cleared);
					message = "Level Cleared! Congratulations!";
				}
				if (move) {
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);
					hero.getPosition().setX(hero.getPosition().getX() + 1);
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(hero.getPiece());
				}
				break;

			case "a":

				nearPiece = maze.get(hero.getPosition().getX() - 1)
						.get(hero.getPosition().getY()).getId();

				if (nearPiece == Piece.emptyChar)
					move = true;
				else if (nearPiece == Piece.swordChar) {
					hero.setPiece(Piece.armedChar);
					hero.setStatus(Status.armed);
					move = true;
				} else if (nearPiece == Piece.exitChar
						&& hero.getStatus() != Status.armed)
					message = "Sword missing!";
				else if (nearPiece == Piece.exitChar) {
					done = true;
					move = true;
					hero.setStatus(Status.cleared);
					message = "Level Cleared! Congratulations!";
				}
				if (move) {
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);
					hero.getPosition().setX(hero.getPosition().getX() - 1);
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(hero.getPiece());
				}
				break;

			case "s":

				nearPiece = maze.get(hero.getPosition().getX())
						.get(hero.getPosition().getY() + 1).getId();

				if (nearPiece == Piece.emptyChar)
					move = true;
				else if (nearPiece == Piece.swordChar) {
					hero.setPiece(Piece.armedChar);
					hero.setStatus(Status.armed);
					move = true;
				} else if (nearPiece == Piece.exitChar
						&& hero.getStatus() != Status.armed)
					message = "Sword missing!";
				else if (nearPiece == Piece.exitChar) {
					done = true;
					move = true;
					hero.setStatus(Status.cleared);
					message = "Level Cleared! Congratulations!";
				}
				if (move) {
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);
					hero.getPosition().setY(hero.getPosition().getY() + 1);
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(hero.getPiece());
				}
				break;

			case "w":

				nearPiece = maze.get(hero.getPosition().getX())
						.get(hero.getPosition().getY() - 1).getId();

				if (nearPiece == Piece.emptyChar)
					move = true;
				else if (nearPiece == Piece.swordChar) {
					hero.setPiece(Piece.armedChar);
					hero.setStatus(Status.armed);
					move = true;
				} else if (nearPiece == Piece.exitChar
						&& hero.getStatus() != Status.armed)
					message = "Sword missing!";
				else if (nearPiece == Piece.exitChar) {
					done = true;
					move = true;
					hero.setStatus(Status.cleared);
					message = "Level Cleared! Congratulations!";
				}
				if (move) {
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(Piece.emptyChar);
					hero.getPosition().setY(hero.getPosition().getY() - 1);
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(hero.getPiece());
				}
				break;

			case "q":
				cli.gameMessages("Game aborted.");
				done = true;
				break;

			case "e":

				if (eagle.getStatus() == Status.alive) {
					eagle.setStatus(Status.pursuing);
					eagle.setHeroPosition(new Position(hero.getPosition()
							.getX(), hero.getPosition().getY(), " H "));
					hero.setPiece(Piece.heroChar);
					maze.get(hero.getPosition().getX())
							.get(hero.getPosition().getY())
							.setId(hero.getPiece());
				}
				break;

			default:
				break;
			}

			if (done)
				break;

			// Check for adjacent dragon
			for (int i = 0; i < dragons.size(); i++) {
				Dragon dragon = dragons.get(i);

				if ((dragon.getStatus() != Status.dead)
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
			if (eagle.getStatus() != Status.dead)
				eagleMove();

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
			if (!nearPieces(
					maze.get(dragon.getPosition().getX() + 1).get(
							dragon.getPosition().getY()), badNearPiece)) {
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
			if (!nearPieces(
					maze.get(dragon.getPosition().getX() - 1).get(
							dragon.getPosition().getY()), badNearPiece)) {

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
			if (!nearPieces(
					maze.get(dragon.getPosition().getX()).get(
							dragon.getPosition().getY() + 1), badNearPiece)) {

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
			if (!nearPieces(
					maze.get(dragon.getPosition().getX()).get(
							dragon.getPosition().getY() - 1), badNearPiece)) {

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

	public void eagleMove() {

		String previousPiece;
		String nextPiece;

		switch (eagle.status) {

		case Status.alive:
			if (hero.status != Status.dead) {
				eagle.getPosition().setX(hero.getPosition().getX());
				eagle.getPosition().setY(hero.getPosition().getY());
				String customPiece = hero.piece.substring(0, 2)
						+ Piece.eagleChar;
				hero.setPiece(customPiece);
				maze.get(hero.getPosition().getX())
						.get(hero.getPosition().getY()).setId(hero.getPiece());
			}
			break;
		case Status.pursuing:
			if (hero.status != Status.dead) {
				int deltaX = sword.getPosition().getX()
						- eagle.getPosition().getX();
				int deltaY = sword.getPosition().getY()
						- eagle.getPosition().getY();

				// Descend at arrival to the sword position
				if (deltaX == 0 && deltaY == 0) {
					// if (maze.get(sword.getPosition().getX())
					// .get(sword.getPosition().getY()).getId().substring(0, 2)
					// != " E") {
					// eagle.setStatus(Status.dead);
					// } else {~
					maze.get(eagle.getPosition().getX())
							.get(eagle.getPosition().getY()).setId("E G");
					eagle.setStatus(Status.returning);

					// }
				} // Eagle movement in pursuit of the sword
				else if (Math.abs(deltaX) > Math.abs(deltaY)) {
					if (deltaX < 0) {
						previousPiece = maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY()).getId()
								.substring(0, 2)
								+ " ";

						nextPiece = maze.get(eagle.getPosition().getX() - 1)
								.get(eagle.getPosition().getY()).getId()
								.substring(0, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);

						eagle.getPosition()
								.setX(eagle.getPosition().getX() - 1);

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					} else {
						previousPiece = maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY()).getId()
								.substring(0, 2)
								+ " ";

						nextPiece = maze.get(eagle.getPosition().getX() + 1)
								.get(eagle.getPosition().getY()).getId()
								.substring(0, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);
						eagle.getPosition()
								.setX(eagle.getPosition().getX() + 1);
						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					}
				} else {
					if (deltaY < 0) {

						previousPiece = maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY()).getId()
								.substring(0, 2)
								+ " ";
						nextPiece = maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY() - 1).getId()
								.substring(0, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);

						eagle.getPosition()
								.setY(eagle.getPosition().getY() - 1);

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					} else {
						previousPiece = maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY()).getId()
								.substring(0, 2)
								+ " ";
						nextPiece = maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY() + 1).getId()
								.substring(0, 2)
								+ Piece.eagleChar;
						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);
						eagle.getPosition()
								.setY(eagle.getPosition().getY() + 1);
						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					}
				}
			}
			break;
		case Status.returning:
			if (hero.status != Status.dead) {
				int deltaX = eagle.getHeroPosition().getX()
						- eagle.getPosition().getX();
				int deltaY = eagle.getHeroPosition().getY()
						- eagle.getPosition().getY();

				// Descend at arrival to the hero position
				if (deltaX == 0 && deltaY == 0) {
					if (eagle.getHeroPosition().getX() != hero.getPosition()
							.getX()
							|| eagle.getHeroPosition().getY() != hero
									.getPosition().getY()) {
						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(Piece.swordChar);
						hero.setPiece(" HG");
					} else {
						hero.setPiece(" AG");
						hero.setStatus(Status.armed);
					}

					eagle.setStatus(Status.alive);

				} // Eagle movement in pursuit of the hero
				else if (Math.abs(deltaX) > Math.abs(deltaY)) {

					if (deltaX < 0) {

						previousPiece = " "
								+ maze.get(eagle.getPosition().getX())
										.get(eagle.getPosition().getY())
										.getId().substring(1, 2) + " ";

						nextPiece = "E"
								+ maze.get(eagle.getPosition().getX() - 1)
										.get(eagle.getPosition().getY())
										.getId().substring(1, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);

						eagle.getPosition()
								.setX(eagle.getPosition().getX() - 1);
						sword.getPosition()
								.setX(sword.getPosition().getX() - 1);

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					} else {
						previousPiece = " "
								+ maze.get(eagle.getPosition().getX())
										.get(eagle.getPosition().getY())
										.getId().substring(1, 2) + " ";

						nextPiece = "E"
								+ maze.get(eagle.getPosition().getX() + 1)
										.get(eagle.getPosition().getY())
										.getId().substring(1, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);

						eagle.getPosition()
								.setX(eagle.getPosition().getX() + 1);
						sword.getPosition()
								.setX(sword.getPosition().getX() + 1);

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					}

				} else {

					if (deltaY < 0) {

						previousPiece = " "
								+ maze.get(eagle.getPosition().getX())
										.get(eagle.getPosition().getY())
										.getId().substring(1, 2) + " ";
						nextPiece = "E"
								+ maze.get(eagle.getPosition().getX())
										.get(eagle.getPosition().getY() - 1)
										.getId().substring(1, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);

						eagle.getPosition()
								.setY(eagle.getPosition().getY() - 1);
						sword.getPosition()
								.setY(sword.getPosition().getY() - 1);

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					} else {
						previousPiece = " "
								+ maze.get(eagle.getPosition().getX())
										.get(eagle.getPosition().getY())
										.getId().substring(1, 2) + " ";
						nextPiece = "E"
								+ maze.get(eagle.getPosition().getX())
										.get(eagle.getPosition().getY() + 1)
										.getId().substring(1, 2)
								+ Piece.eagleChar;

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(previousPiece);

						eagle.getPosition()
								.setY(eagle.getPosition().getY() + 1);
						sword.getPosition()
								.setY(sword.getPosition().getY() + 1);

						maze.get(eagle.getPosition().getX())
								.get(eagle.getPosition().getY())
								.setId(nextPiece);
					}
				}
			}
			break;

		default:
			break;
		}
	}

	boolean nearPieces(Position position, ArrayList<String> pieces) {

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