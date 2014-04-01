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
		super(mazeSize, 7); // 7 -> dragonCounter
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
			HashMap<Integer, Boolean> validMoves = getValidMoves(hero);

			if (userInput.equals("q")) {
				done = true;
				break;
			}
			if (!hero.getDead())
				moveHero(userInput, validMoves);

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
			String state = checkGame();
			if (state.equals(State.HERO_WON.toString())) {
				cli.displayMaze(maze);
				done = true;
				cli.gameMessages("* Hero won :)");
			} else if (state.equals(State.HERO_DEAD.toString())) {
				cli.displayMaze(maze);
				done = true;
				cli.errorMessages("! Hero died :(");
			} else if (state.equals(State.DRAGON_DEAD.toString())) {
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
		if (userInput.equals("e") && (!hero.getArmed())) {
			if (!eagle.getState().equals(State.EAGLE_RETURNING.toString())) {
				eagle.setState(State.EAGLE_PURSUING.toString());
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
				swapPieces(Movement.MOVE_UP.getDirection(), hero);
			} else
				cli.errorMessages("! Hero can't move up");
		} else if (userInput.equals("s")) {
			// Check if HashMap contains the key with value 1)
			if (moves.containsKey(Movement.MOVE_DOWN.getDirection())) {
				swapPieces(Movement.MOVE_DOWN.getDirection(), hero);
			} else
				cli.errorMessages("! Hero can't move down!");
		} else if (userInput.equals("d")) {
			// Check if HashMap contains the key with value 2)
			if (moves.containsKey(Movement.MOVE_RIGHT.getDirection())) {
				swapPieces(Movement.MOVE_RIGHT.getDirection(), hero);
			} else
				cli.errorMessages("! Hero can't move right!");
		} else if (userInput.equals("a")) {
			// Check if HashMap contains the key with value 3)
			if (moves.containsKey(Movement.MOVE_LEFT.getDirection())) {
				swapPieces(Movement.MOVE_LEFT.getDirection(), hero);
			} else
				cli.errorMessages("! Hero can't move left!");
		}
	}

	/**
	 * O movimento do dragon e feito em varios passos
	 * 
	 * Primeiro verificamos se a peca para onde queremos mover nao e parede nem
	 * e o dragon(para nao sobrepor as duas personagens) Se essa condicao for
	 * verdadeira entao atualizamos a posicao atual do dragon para livre e
	 * verificamos se esta nova peca corresponde a peca onde a espada esta. Se
	 * estiver entao alteramos uma propriedade do dragon(naEspada) e mostramos
	 * um 'F'. Se um dragon estava marcado com 'F' anteriormente e agora se move
	 * para uma peca diferente da peca da espada entao mostramos um 'E'na peca
	 * da espada e um 'D' na peca do dragon(estado normal)
	 */
	public void moveDragon(Dragon dragon) {

		Random random = new Random();
		int previousX = dragon.getPosX();
		int previousY = dragon.getPosY();
		int nextX = 0;
		int nextY = 0;
		int direction = random.nextInt(4);
		String symbol;
		if (direction == Movement.MOVE_UP.getDirection()) {
			symbol = getMazePieceSymbol(previousX, previousY - 1);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& nearDragons(previousX, previousY - 1)) {
				nextX = dragon.getPosX();
				nextY = dragon.getPosY() - 1;
			}
		} else if (direction == Movement.MOVE_DOWN.getDirection()) {
			symbol = getMazePieceSymbol(previousX, previousY + 1);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& nearDragons(previousX, previousY + 1)) {
				nextX = dragon.getPosX();
				nextY = dragon.getPosY() + 1;
			}
		} else if (direction == Movement.MOVE_RIGHT.getDirection()) {
			symbol = getMazePieceSymbol(previousX + 1, previousY);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& nearDragons(previousX + 1, previousY)) {
				nextX = dragon.getPosX() + 1;
				nextY = dragon.getPosY();
			}
		} else if (direction == Movement.MOVE_LEFT.getDirection()) {
			symbol = getMazePieceSymbol(previousX - 1, previousY);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& nearDragons(previousX - 1, previousY)) {
				nextX = dragon.getPosX() - 1;
				nextY = dragon.getPosY();
			}
		}
		if (nextX != 0 && nextY != 0) {
			// Set left piece symbol
			getMazePiece(previousX, previousY).setSymbol(
					PieceType.FREE.asString());
			// Set entry piece symbol
			getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
			// Update dragon position
			dragon.setPosition(nextX, nextY);

			if (dragon.getGuarding()) {
				getMazePiece(previousX, previousY).setSymbol(
						PieceType.SWORD.asString());
				dragon.setGuarding(false);
				getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
			} else if ((getSword().getPosX() == dragon.getPosX())
					&& (getSword().getPosY() == dragon.getPosY())
					&& (!eagle.getState().equals(State.EAGLE_RETURNING))
					&& !hero.getArmed()) {
				dragon.setGuarding(true);
				getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
			}
		}
	}

	public void moveEagle() {

		String previousPiece;
		String nextPiece;
		int previousX = eagle.getPosX();
		int previousY = eagle.getPosY();
		int nextX = 0, nextY = 0;

		// Update eagle position to follow hero
		if (eagle.getState().equals(State.EAGLE_FOLLOWING.toString())
				|| (hero.getArmed())) {
			eagle.setPosition(hero.getPosX(), hero.getPosY());
		}
		// Calculate distance between the eagle and the sword
		else if (eagle.getState().equals(State.EAGLE_PURSUING.toString())) {
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
						eagle.setState(State.EAGLE_RETURNING.toString());
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
		else if (eagle.getState().equals(State.EAGLE_RETURNING.toString())) {
			if (!hero.getDead()) {
				int deltaX = eagle.getHeroX() - eagle.getPosX();
				int deltaY = eagle.getHeroY() - eagle.getPosY();

				// Descend at arrival to the hero position
				if (deltaX == 0 && deltaY == 0) {
					if (eagle.getHeroX() != hero.getPosX()
							|| eagle.getHeroY() != hero.getPosY()) {
						getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(PieceType.SWORD.asString());
						getSword().setPosition(eagle.getHeroX(),
								eagle.getHeroY());
						eagle.setPosition(hero.getPosX(), hero.getPosY());
						hero.setEagle(true);
						eagle.setState(State.EAGLE_GROUND.toString());
						getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
								hero.showHero());
						cli.errorMessages("! Hero was not on the same spot - Eagle returned to hero");
					} else {
						hero.setEagle(true);
						hero.setArmed(true);
						eagle.setState(State.EAGLE_PURSUING.toString());
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

	public String checkGame() {
		if ((hero.getPosX() == getExitX()) && (hero.getPosY() == getExitY())
				&& hero.getArmed()) {
			return State.HERO_WON.toString();
		}
		// TODO get arraylist to check all dragons nearby available to kill
		Dragon dragon = adjacentDragon();

		if (dragon != null) {
			if (!hero.getArmed()) {
				if (!dragon.getDead() && !dragon.getAsleep()) {
					// If hero is unnarmed the game ends
					return State.HERO_DEAD.toString();
				}
			}
			// Armed hero
			else if (!dragon.getDead()) {
				// Set dragon state as dead
				dragon.setDead(true);
				getMazePiece(dragon.getPosX(), dragon.getPosY()).setSymbol(
						dragon.showDragon());
				return State.DRAGON_DEAD.toString();
			}
		}
		return State.GAME_CONTINUE.toString();
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
