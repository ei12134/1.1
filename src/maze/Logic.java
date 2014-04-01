package maze;

import java.util.HashMap;
import java.util.Random;

import cli.Cli;

public class Logic extends Maze {

	private Cli cli;
	private int dragonStrategy;
	private boolean done;

	// Standard Maze
	public Logic(Cli cli) {
		super();
		this.cli = cli;
		this.done = false;
		dragonStrategy = 1;
		cli = new Cli();
	}

	// Random Maze
	public Logic(Cli cli, int mazeSize) {
		super(mazeSize, 5); // 5 -> dragonCounter
		this.cli = cli;
		this.done = false;
		dragonStrategy = 1;
		cli = new Cli();
	}

	public void startGame() {

		String userInput = null;
		int dragonState;
		Random random = new Random();

		do {
			// Display Maze
			cli.displayMaze(maze);
			// Move hero
			userInput = cli.getKey();
			HashMap<Integer, Boolean> validHeroMoves = getValidHeroMoves();

			if (userInput.equals("q")) {
				done = true;
				break;
			}
			if (!hero.getDead())
				moveHero(userInput, validHeroMoves);

			// Dragon(s) processing
			for (int i = 0; i < dragons.size(); i++) {
				Dragon dragon = dragons.get(i);
				dragonState = random.nextInt(4);

				if (dragonState == 0 && dragonStrategy == 2)
					dragon.setAsleep(true);
				else
					dragon.setAsleep(false);
				// Move dragon
				if (!dragon.getDead()) {
					if (dragon.getAsleep()) {
						getMazePiece(dragon.getPosX(), dragon.getPosY())
								.setSymbol(dragon.showDragon());
					} else if (dragonStrategy != 0)
						moveDragon(dragon);
				}
			}
			if (!eagle.getDead())
				moveEagle();

			// Check game status
			State state = checkGame();
			if (state.equals(State.HERO_WON)) {
				cli.displayMaze(maze);
				done = true;
				cli.gameMessages("* Hero won :)");
			} else if (state.equals(State.HERO_DEAD)) {
				cli.displayMaze(maze);
				done = true;
				cli.errorMessages("! Hero died :(");
			} else if (state.equals(State.DRAGON_DEAD)) {
				cli.displayMaze(maze);
				cli.gameMessages("* Hero killed a dragon!");
			}
		} while (!done);
	}

	/**
	 * Esta funcao vai receber como parametro uma nova posicao para movimentar o
	 * jogador e um HashMap que se certifica que a proxima posicao e
	 * possivel(nao e parede por exemplo) Se for valida entao movimenta, caso
	 * contrario mostra uma mensagem de erro
	 */
	public void moveHero(String userInput, HashMap<Integer, Boolean> moves) {

		// Release eagle
		if (userInput.equals("e")) {
			if (eagle.getDead())
				cli.errorMessages("! Eagle is dead");
			else if (hero.getArmed())
				cli.errorMessages("! Hero already armed");
			else if (!eagle.getState().equals(State.EAGLE_FOLLOWING))
				cli.errorMessages("! Eagle isn't following the hero");
			else {
				eagle.setState(State.EAGLE_PURSUING);
				hero.setEagle(false);
				eagle.setHeroX(hero.getPosX());
				eagle.setHeroY(hero.getPosY());
				cli.gameMessages("* Hero released the eagle");
			}
		}
		// Mover player Up
		if (userInput.equals("w")) {
			// Check if HashMap contains the key with value 0)
			if (moves.containsKey(Movement.MOVE_UP.getDirection())) {
				// Make current piece free
				swapHero(Movement.MOVE_UP.getDirection());
			} else
				cli.errorMessages("! Hero can't move up");
		} else if (userInput.equals("s")) {
			// Check if HashMap contains the key with value 1)
			if (moves.containsKey(Movement.MOVE_DOWN.getDirection())) {
				swapHero(Movement.MOVE_DOWN.getDirection());
			} else
				cli.errorMessages("! Hero can't move down!");
		} else if (userInput.equals("d")) {
			// Check if HashMap contains the key with value 2)
			if (moves.containsKey(Movement.MOVE_RIGHT.getDirection())) {
				swapHero(Movement.MOVE_RIGHT.getDirection());
			} else
				cli.errorMessages("! Hero can't move right!");
		} else if (userInput.equals("a")) {
			// Check if HashMap contains the key with value 3)
			if (moves.containsKey(Movement.MOVE_LEFT.getDirection())) {
				swapHero(Movement.MOVE_LEFT.getDirection());
			} else
				cli.errorMessages("! Hero can't move left!");
		}
	}

	public void moveDragon(Dragon dragon) {

		Random random = new Random();
		int previousX = dragon.getPosX();
		int previousY = dragon.getPosY();
		int nextX = 0, nextY = 0;
		int direction = random.nextInt(4);
		String nearSymbol;

		switch (direction) {
		case (0):
			// Move Up
			nextX = dragon.getPosX();
			nextY = dragon.getPosY() - 1;
			break;

		case (1):
			// Move Down
			nextX = dragon.getPosX();
			nextY = dragon.getPosY() + 1;
			break;

		case (2):
			// Move Right
			nextX = dragon.getPosX() + 1;
			nextY = dragon.getPosY();
			break;

		case (3):
			// Move Left
			nextX = dragon.getPosX() - 1;
			nextY = dragon.getPosY();
			break;

		default:
			break;
		}

		nearSymbol = getMazePieceSymbol(nextX, nextY);
		if ((nearSymbol.equals(PieceType.WALL.asString()))
				|| (nearSymbol.equals(hero.showHero()))
				|| (nearSymbol.equals(PieceType.EXIT.asString()))
				|| nearDragons(nextX, nextY)) {
			nextX = 0;
			nextY = 0;
		}

		if (nextX != 0 && nextY != 0) {
			// Clear left piece symbol
			getMazePiece(previousX, previousY).setSymbol(
					PieceType.FREE.asString());

			if (dragon.getGuarding()) {
				getMazePiece(previousX, previousY).setSymbol(
						PieceType.SWORD.asString());
				dragon.setGuarding(false);
			} else if ((getSword().getPosX() == nextX)
					&& (getSword().getPosY() == nextY) && !hero.getArmed()) {
				if (eagle.getState().equals(State.EAGLE_GROUND))
					eagle.setDead(true);

				dragon.setGuarding(true);
			}
			// Set entry piece symbol
			getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
			// Update dragon position
			dragon.setPosition(nextX, nextY);
		}
	}

	public void moveEagle() {

		String previousPiece;
		String nextPiece;
		int previousX = eagle.getPosX();
		int previousY = eagle.getPosY();
		int nextX = 0, nextY = 0;

		// Update eagle position to follow hero
		if (eagle.getState().equals(State.EAGLE_FOLLOWING) || (hero.getArmed())) {
			eagle.setPosition(hero.getPosX(), hero.getPosY());
		}
		// Calculate distance between the eagle and the sword
		else if (eagle.getState().equals(State.EAGLE_PURSUING)) {
			if (!hero.getDead()) {
				int deltaX = getSword().getPosX() - eagle.getPosX();
				int deltaY = getSword().getPosY() - eagle.getPosY();
				// Descend at arrival to the sword position
				if (deltaX == 0 && deltaY == 0) {
					// Dragon guarding sword at arrival position
					if (getMazePiece(eagle.getPosX(), eagle.getPosY())
							.getSymbol().contains("F")) {
						eagle.setDead(true);
						cli.errorMessages("! Eagle died :(");
					}
					// Free sword
					else {
						getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol("E G");
						eagle.setState(State.EAGLE_RETURNING);
					}
				}
				// Eagle movement in pursuit of the sword
				else {
					if (Math.abs(deltaX) > Math.abs(deltaY)) {
						if (deltaX < 0) {
							nextX = eagle.getPosX() - 1;
							nextY = eagle.getPosY();
						} else {
							nextX = eagle.getPosX() + 1;
							nextY = eagle.getPosY();
						}
					} else if (deltaY < 0) {
						nextX = eagle.getPosX();
						nextY = eagle.getPosY() - 1;
					} else {
						nextX = eagle.getPosX();
						nextY = eagle.getPosY() + 1;
					}
					// Generate custom maze symbols
					previousPiece = getMazePiece(previousX, previousY)
							.getSymbol().substring(0, 2) + " ";
					nextPiece = getMazePiece(nextX, nextY).getSymbol()
							.substring(0, 2) + eagle.showEagle();

					// Set or restore maze symbols
					getMazePiece(previousX, previousY).setSymbol(previousPiece);
					getMazePiece(nextX, nextY).setSymbol(nextPiece);

					// Update eagle position
					eagle.setPosition(nextX, nextY);
				}
			}
		}
		// Calculate distance between the eagle and the hero
		else if (eagle.getState().equals(State.EAGLE_RETURNING)) {
			if (!hero.getDead()) {
				int deltaX = eagle.getHeroX() - eagle.getPosX();
				int deltaY = eagle.getHeroY() - eagle.getPosY();

				// Descend at arrival to the hero position
				if (deltaX == 0 && deltaY == 0) {
					if (eagle.getHeroX() != hero.getPosX()
							|| eagle.getHeroY() != hero.getPosY()) {
						eagle.setState(State.EAGLE_GROUND);
						cli.errorMessages("! Hero was not on the same spot - Eagle is in the ground");
					} else {
						hero.setEagle(true);
						hero.setArmed(true);
						eagle.setState(State.EAGLE_PURSUING);
						cli.gameMessages("* Eagle returned successfuly and hero is now armed");
						getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
								hero.showHero());
					}
				}
				// Eagle movement in pursuit of the hero
				else {
					if (Math.abs(deltaX) > Math.abs(deltaY)) {

						if (deltaX < 0) {
							nextX = eagle.getPosX() - 1;
							nextY = eagle.getPosY();
						} else {
							nextX = eagle.getPosX() + 1;
							nextY = eagle.getPosY();
						}
					} else if (deltaY < 0) {
						nextX = eagle.getPosX();
						nextY = eagle.getPosY() - 1;
					} else {
						nextX = eagle.getPosX();
						nextY = eagle.getPosY() + 1;
					}
					// Generate custom maze symbols
					previousPiece = " "
							+ getMazePiece(previousX, previousY).getSymbol()
									.substring(1, 2) + " ";
					nextPiece = "E"
							+ getMazePiece(nextX, nextY).getSymbol().substring(
									1, 2) + eagle.showEagle();

					// Set or restore maze symbols
					getMazePiece(eagle.getPosX(), eagle.getPosY()).setSymbol(
							previousPiece);
					getMazePiece(nextX, nextY).setSymbol(nextPiece);
					// Update eagle and sword positions
					eagle.setPosition(nextX, nextY);
					getSword().setPosition(nextX, nextY);
				}
			}
		}
	}

	public State checkGame() {

		State state = State.GAME_CONTINUE;
		if ((hero.getPosX() == getExitX()) && (hero.getPosY() == getExitY())
				&& hero.getArmed()) {
			return State.HERO_WON;
		}
		// TODO get arraylist to check all dragons nearby available to kill
		Dragon dragon;
		for (int i = 0; i < dragons.size(); i++) {
			dragon = adjacentDragon();
			if (dragon != null) {
				if (!hero.getArmed()) {
					if (!dragon.getDead() && !dragon.getAsleep()) {
						// If hero is unnarmed the game ends
						return State.HERO_DEAD;
					}
				}
				// Armed hero
				else if (!dragon.getDead()) {
					// Set dragon state as dead
					dragon.setDead(true);
					getMazePiece(dragon.getPosX(), dragon.getPosY()).setSymbol(
							dragon.showDragon());
					dragons.remove(dragon);
					state = State.DRAGON_DEAD;
				}
			}
		}
		return state;
	}

	/**
	 * Checks each of the 4 positions around hero for availability and if adds
	 * it to HasMap validMoves
	 * 
	 * @return HashMap with possible positions for hero to move into
	 */
	public HashMap<Integer, Boolean> getValidHeroMoves() {
		HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
		boolean heroArmado = hero.getArmed();

		// Check if hero can move up
		if (hero.getPosY() - 1 >= 0)
			if (!(maze.get(hero.getPosY() - 1).get(hero.getPosX()).getSymbol()
					.equals(PieceType.WALL.asString()))) {
				// Confirms if hero is armed at exit
				if (!((maze.get(hero.getPosY() - 1).get(hero.getPosX())
						.getSymbol().equals(PieceType.EXIT.asString())) && !heroArmado))
					validMoves.put(0, true);
			}
		// Check if hero can move down
		if (hero.getPosY() + 1 < maze.size())
			if (!(maze.get(hero.getPosY() + 1).get(hero.getPosX()).getSymbol()
					.equals(PieceType.WALL.asString()))) {
				if (!((maze.get(hero.getPosY() + 1).get(hero.getPosX())
						.getSymbol().equals(PieceType.EXIT.asString())) && !heroArmado))
					validMoves.put(1, true);
			}
		// Check if hero can move to the right
		if (hero.getPosX() + 1 < maze.size())
			if (!(maze.get(hero.getPosY()).get(hero.getPosX() + 1).getSymbol()
					.equals(PieceType.WALL.asString()))) {
				if (!((maze.get(hero.getPosY()).get(hero.getPosX() + 1)
						.getSymbol().equals(PieceType.EXIT.asString())) && !heroArmado))
					validMoves.put(2, true);
			}
		// Check if hero can move to the left
		if (hero.getPosX() - 1 >= 0)
			if (!(maze.get(hero.getPosY()).get(hero.getPosX() - 1).getSymbol()
					.equals(PieceType.WALL.asString()))) {
				if (!((maze.get(hero.getPosY()).get(hero.getPosX() - 1)
						.getSymbol().equals(PieceType.EXIT.asString())) && !heroArmado))
					validMoves.put(3, true);
			}
		return validMoves;
	}

	public Dragon adjacentDragon() {
		// Generic position returning function
		int heroX = hero.getPosX();
		int heroY = hero.getPosY();

		for (int i = 0; i < dragons.size(); i++) {
			Dragon dragon = dragons.get(i);
			int dragonX = dragon.getPosX();
			int dragonY = dragon.getPosY();

			if (heroX + 1 == dragonX && heroY == dragonY)
				return dragon;
			else if (heroX - 1 == dragonX && heroY == dragonY)
				return dragon;
			else if (heroX == dragonX && heroY - 1 == dragonY)
				return dragon;
			else if (heroX == dragonX && heroY + 1 == dragonY)
				return dragon;
		}
		return null;
	}

	public boolean nearDragons(int posX, int posY) {
		if (maze.get(posY).get(posX).getSymbol().equals(" d ")
				|| maze.get(posY).get(posX).getSymbol().equals(" D ")
				|| maze.get(posY).get(posX).getSymbol().equals(" F "))
			return true;

		return false;
	}

	public boolean dragonAtSword(Dragon dragon) {
		return (dragon.getPosX() == getSword().getPosX())
				&& (dragon.getPosY() == getSword().getPosY());
	}

	public boolean getDone() {
		return done;
	}

	public void setDragonStrategy(int dragonStrategy) {
		this.dragonStrategy = dragonStrategy;
	}
}
