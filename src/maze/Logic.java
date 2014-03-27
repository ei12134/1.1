package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cli.Cli;

public class Logic extends Cli {

	public Maze maze;
	public ArrayList<Dragon> dragons;
	public Hero hero;
	public Eagle eagle;
	public int dragonStrategy;
	private boolean finished;

	public Logic() {
		while (mainMenu()) {
			this.finished = false;
			int size = setMazeSize();
			if (size == 10) {
				hero = new Hero(1, 1);
				eagle = new Eagle(1, 1);
				dragons = new ArrayList<Dragon>();
				dragons.add(new Dragon(1, 6));
				maze = new Maze(hero, dragons, 10);
			} else {
				hero = new Hero(0, 0);
				eagle = new Eagle(0, 0);
				dragons = new ArrayList<Dragon>();
				maze = new Maze(hero, dragons, eagle, size, 7);
			}
			dragonStrategy = setDragonStrategy();
			startGame();
		}
	}

	public void startGame() {

		String userInput = null;
		int dragonState;
		Random random = new Random();

		do {
			displayMaze(maze.maze);
			// Move hero
			userInput = getKey();
			HashMap<Integer, Boolean> validMoves = maze.getValidMoves(hero);

			if (userInput.equals("q")) {
				finished = true;
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
						maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
								.setSymbol(dragon.showDragon());
					} else if (dragonStrategy != 0)
						moveDragon(dragon);
				}
			}
			if (!eagle.getDead())
				moveEagle();

			// Check game status
			String state = checkGame();
			if (state.equals(GameState.HERO_WON.toString())) {
				finished = true;
				gameMessages("\n * Hero won :)\n");
			} else if (state.equals(GameState.HERO_DIED.toString())) {
				finished = true;
				errorMessages("\n * Hero died :(\n");
			}
		} while (!finished);
	}

	/**
	 * Esta funcao vai receber como parametro uma nova posicao para movimentar o
	 * jogador e um HashMap que se certifica que a proxima posicao e
	 * possivel(nao e parede por exemplo) Se for valida entao movimenta, caso
	 * contrario mostra uma mensagem de erro
	 */
	public void moveHero(String userInput, HashMap<Integer, Boolean> moves) {

		// Release eagle
		if (userInput.equals("e")&&(!hero.getArmed())) {
			if (!eagle.getReturning()) {
				eagle.setPursuit(true);
				hero.setEagle(false);
				eagle.setHeroX(hero.getPosX());
				eagle.setHeroY(hero.getPosY());
				gameMessages("\n * Hero released the eagle\n");
			}
		}
		// Mover player Up
		if (userInput.equals("w")) {
			// Check if HashMap contains the key with value 0)
			if (moves.containsKey(Movement.MOVE_UP.getDirection())) {
				// Make current piece free
				maze.swapPieces(Movement.MOVE_UP.getDirection(), hero);
			} else
				errorMessages("\n\n ! Hero can't move up\n");
		} else if (userInput.equals("s")) {
			// Check if HashMap contains the key with value 1)
			if (moves.containsKey(Movement.MOVE_DOWN.getDirection())) {
				maze.swapPieces(Movement.MOVE_DOWN.getDirection(), hero);
			} else
				errorMessages("\n\n ! Hero can't move down!\n");
		} else if (userInput.equals("d")) {
			// Check if HashMap contains the key with value 2)
			if (moves.containsKey(Movement.MOVE_RIGHT.getDirection())) {
				maze.swapPieces(Movement.MOVE_RIGHT.getDirection(), hero);
			} else
				errorMessages("\n\n ! Hero can't move right!\n");
		} else if (userInput.equals("a")) {
			// Check if HashMap contains the key with value 3)
			if (moves.containsKey(Movement.MOVE_LEFT.getDirection())) {
				maze.swapPieces(Movement.MOVE_LEFT.getDirection(), hero);
			} else
				errorMessages("\n\n ! Hero can't move left!\n");
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
			symbol = maze.getMazePieceSymbol(previousX, previousY - 1);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& maze.nearDragons(previousX, previousY - 1)) {
				nextX = dragon.getPosX();
				nextY = dragon.getPosY() - 1;
			}
		} else if (direction == Movement.MOVE_DOWN.getDirection()) {
			symbol = maze.getMazePieceSymbol(previousX, previousY + 1);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& maze.nearDragons(previousX, previousY + 1)) {
				nextX = dragon.getPosX();
				nextY = dragon.getPosY() + 1;
			}
		} else if (direction == Movement.MOVE_RIGHT.getDirection()) {
			symbol = maze.getMazePieceSymbol(previousX + 1, previousY);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& maze.nearDragons(previousX + 1, previousY)) {
				nextX = dragon.getPosX() + 1;
				nextY = dragon.getPosY();
			}
		} else if (direction == Movement.MOVE_LEFT.getDirection()) {
			symbol = maze.getMazePieceSymbol(previousX - 1, previousY);
			if ((!symbol.equals(PieceType.WALL.asString()))
					&& (!symbol.equals(hero.showHero()))
					&& (!symbol.equals(PieceType.EXIT.asString()))
					&& maze.nearDragons(previousX - 1, previousY)) {
				nextX = dragon.getPosX() - 1;
				nextY = dragon.getPosY();
			}
		}
		if (nextX != 0 && nextY != 0) {
			// Set left piece symbol
			maze.getMazePiece(previousX, previousY).setSymbol(
					PieceType.FREE.asString());
			// Set entry piece symbol
			maze.getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
			// Update dragon position
			dragon.setPosition(nextX, nextY);

			if (dragon.getAtSword()) {
				maze.getMazePiece(previousX, previousY).setSymbol(
						PieceType.SWORD.asString());
				dragon.setAtSword(false);
				maze.getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
			} else if ((maze.getSword().getPosX() == dragon.getPosX())
					&& (maze.getSword().getPosY() == dragon.getPosY()) && !eagle.getReturning()) {
				dragon.setAtSword(true);
				maze.getMazePiece(nextX, nextY).setSymbol(dragon.showDragon());
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
		if ((!eagle.getPursuit() && !eagle.getReturning())||(hero.getArmed())){
			eagle.setPosition(hero.getPosX(), hero.getPosY());
		}
		// Calculate distance between the eagle and the sword
		else if (eagle.getPursuit()) {
			if (!hero.getDead()) {
				int deltaX = maze.getSword().getPosX() - eagle.getPosX();
				int deltaY = maze.getSword().getPosY() - eagle.getPosY();
				// Descend at arrival to the sword position
				if (deltaX == 0 && deltaY == 0) {
					// Dragon guarding sword at arrival position
					if (maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
							.getSymbol().contains("F")) {
						eagle.setDead(true);
						errorMessages("\n ! Eagle died :(\n");
					}
					// Free sword
					else {
						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol("E G");
						eagle.setPursuit(false);
						eagle.setReturning(true);
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
					previousPiece = maze.getMazePiece(previousX, previousY)
							.getSymbol().substring(0, 2)
							+ " ";
					nextPiece = maze.getMazePiece(nextX, nextY).getSymbol()
							.substring(0, 2)
							+ eagle.showEagle();

					// Set or restore maze symbols
					maze.getMazePiece(previousX, previousY).setSymbol(
							previousPiece);
					maze.getMazePiece(nextX, nextY).setSymbol(nextPiece);

					// Update eagle position
					eagle.setPosition(nextX, nextY);
				}
			}
		}
		// Calculate distance between the eagle and the hero
		else if (eagle.getReturning()) {
			if (!hero.getDead()) {
				int deltaX = eagle.getHeroX() - eagle.getPosX();
				int deltaY = eagle.getHeroY() - eagle.getPosY();

				// Descend at arrival to the hero position
				if (deltaX == 0 && deltaY == 0) {
					if (eagle.getHeroX() != hero.getPosX()
							|| eagle.getHeroY() != hero.getPosY()) {
						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(PieceType.SWORD.asString());
						maze.getSword().setPosition(eagle.getHeroX(),
								eagle.getHeroY());
						eagle.setPosition(hero.getPosX(), hero.getPosY());
						hero.setEagle(true);
						eagle.setPursuit(false);
						eagle.setReturning(false);
						maze.getMazePiece(hero.getPosX(), hero.getPosY())
								.setSymbol(hero.showHero());
						errorMessages("\n\n ! Hero was not on the same spot\n ! Eagle returned to hero\n");
					} else {
						hero.setEagle(true);
						hero.setArmed(true);
						eagle.setPursuit(false);
						eagle.setReturning(false);
						gameMessages("\n\n * Eagle returned successfuly and hero is now armed\n");
						maze.getMazePiece(hero.getPosX(), hero.getPosY())
						.setSymbol(hero.showHero());
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
							+ maze.getMazePiece(previousX, previousY)
									.getSymbol().substring(1, 2) + " ";
					nextPiece = "E"
							+ maze.getMazePiece(nextX, nextY).getSymbol()
									.substring(1, 2) + eagle.showEagle();

					// Set or restore maze symbols
					maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
							.setSymbol(previousPiece);
					maze.getMazePiece(nextX, nextY).setSymbol(nextPiece);
					// Update eagle and sword positions
					eagle.setPosition(nextX, nextY);
					maze.getSword().setPosition(nextX, nextY);
				}
			}
		}
	}

	public String checkGame() {
		if ((hero.getPosX() == maze.getExitX())
				&& (hero.getPosY() == maze.getExitY()) && hero.getArmed()) {
			return GameState.HERO_WON.toString();
		}

		Dragon dragon = adjacentDragon();

		if (dragon != null) {
			if (!hero.getArmed()) {
				if (!dragon.getDead() && !dragon.getAsleep()) {
					// If hero is unnarmed the game ends
					return GameState.HERO_DIED.toString();
				}
			} else {
				// Armed hero
				if (!dragon.getDead() || dragon.getAsleep()) {
					if (dragons.size() > 1)
						gameMessages("\n * Hero killed a dragon\n");
					else
						gameMessages("\n * Hero killed the dragon\n");
					// Set dragon state as dead
					dragon.setDead(true);
					dragons.remove(dragon);
					maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
							.setSymbol(dragon.showDragon());
				}
			}
		}
		return GameState.CONTINUE_GAME.toString();
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
		return (dragon.getPosX() == maze.getSword().getPosX())
				&& (dragon.getPosY() == maze.getSword().getPosY());
	}
}
