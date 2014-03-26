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

			moveHero(userInput, validMoves);
//			moveEagle();

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
								.setSymbol(dragon.showDragon(hero));
					} else if (dragonStrategy != 0)
						moveDragon(dragon);
				}
			}
			// Check game status
			String state = checkGame();
			if (state.equals(GameState.HERO_WON.toString())) {
				finished = true;
				gameMessages("\nHero won :)");
			} else if (state.equals(GameState.HERO_DIED.toString())) {
				finished = true;
				gameMessages("\nHero died :(");
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
		// Mover jogador para cima
		if (userInput.equals("e")) {
			if (!eagle.getReturning()) {
				eagle.setPursuing(true);
				hero.setEagle(false);
				eagle.setHeroX(hero.getPosX());
				eagle.setHeroY(hero.getPosY());
			}
		}

		if (userInput.equals("w")) {
			// Verificar se o HashMap contem a key com o valor 0(cima)
			if (moves.containsKey(Movement.MOVE_UP.getDirection())) {
				// Alterar a peca atual onde o hero esta para uma peca livre
				maze.swapPieces(Movement.MOVE_UP.getDirection(), hero);
			} else
				errorMessages("\n\nHero can't move up!\n");
		} else if (userInput.equals("s")) {
			// Verificar se o HashMap contem a key com o valor 1(baixo)
			if (moves.containsKey(Movement.MOVE_DOWN.getDirection())) {
				maze.swapPieces(Movement.MOVE_DOWN.getDirection(), hero);
			} else
				errorMessages("\n\nHero can't move down!\n");
		} else if (userInput.equals("a")) {
			// Verificar se o HashMap contem a key com o valor 3(esquerda)
			if (moves.containsKey(Movement.MOVE_LEFT.getDirection())) {
				maze.swapPieces(Movement.MOVE_LEFT.getDirection(), hero);
			} else
				errorMessages("\n\nHero can't move left!\n");
		} else if (userInput.equals("d")) {
			// Verificar se o HashMap contem a key com o valor 2(direita)
			if (moves.containsKey(Movement.MOVE_RIGHT.getDirection())) {
				maze.swapPieces(Movement.MOVE_RIGHT.getDirection(), hero);
			} else
				errorMessages("\n\nHero can't move right!\n");
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
		int posX = dragon.getPosX();
		int posY = dragon.getPosY();
		int direction = random.nextInt(4);
		String symbol;
		if (direction == Movement.MOVE_UP.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX, posY - 1);
			if (symbol != PieceType.WALL.asString()
					&& symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asString()
					&& maze.nearDragons(posX, posY - 1)) {
				maze.getMazePiece(posX, posY).setSymbol(
						PieceType.FREE.asString());
				dragon.setPosition(posX, posY - 1);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX, posY - 1).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (direction == Movement.MOVE_DOWN.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX, posY + 1);
			if (symbol != PieceType.WALL.asString()
					&& symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asString()
					&& maze.nearDragons(posX, posY + 1)) {
				maze.getMazePiece(posX, posY).setSymbol(
						PieceType.FREE.asString());
				dragon.setPosition(posX, posY + 1);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX, posY + 1).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (direction == Movement.MOVE_RIGHT.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX + 1, posY);
			if (symbol != PieceType.WALL.asString()
					&& symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asString()
					&& maze.nearDragons(posX + 1, posY)) {
				maze.getMazePiece(posX, posY).setSymbol(
						PieceType.FREE.asString());
				dragon.setPosition(posX + 1, posY);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX + 1, posY).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (direction == Movement.MOVE_LEFT.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX - 1, posY);
			if (symbol != PieceType.WALL.asString()
					&& symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asString()
					&& maze.nearDragons(posX - 1, posY)) {
				maze.getMazePiece(posX, posY).setSymbol(
						PieceType.FREE.asString());
				dragon.setPosition(posX - 1, posY);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX - 1, posY).setSymbol(
						dragon.showDragon(hero));
			}
		}
	}

	public void moveEagle() {

		String previousPiece;
		String nextPiece;

		if (!eagle.getPursuing() && !eagle.getReturning()) {
			if (!hero.getDead())
				eagle.setPosition(hero.getPosX(), hero.getPosY());
		} else if (eagle.getPursuing()) {

			if (!hero.getDead()) {
				int deltaX = maze.getSword().getPosX() - eagle.getPosX();
				int deltaY = maze.getSword().getPosY() - eagle.getPosY();

				// Descend at arrival to the sword position
				if (deltaX == 0 && deltaY == 0) {
					// if (maze.get(sword.getPosition().getX())
					// .get(sword.getPosition().getY()).getId()
					// .substring(1, 2).equals("F")) {
					// eagle.setStatus(Status.dead);
					// } else {
					maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
							.setSymbol("E G");
					eagle.setPursuing(false);
					eagle.setReturning(true);

				}
				// Eagle movement in pursuit of the sword
				else if (Math.abs(deltaX) > Math.abs(deltaY)) {
					if (deltaX < 0) {
						previousPiece = maze
								.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.getSymbol().substring(0, 2)
								+ " ";

						nextPiece = maze
								.getMazePiece(eagle.getPosX() - 1,
										eagle.getPosY()).getSymbol()
								.substring(0, 2)
								+ eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX() - 1, eagle.getPosY());

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					} else {
						previousPiece = maze
								.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.getSymbol().substring(0, 2)
								+ " ";

						nextPiece = maze
								.getMazePiece(eagle.getPosX() + 1,
										eagle.getPosY()).getSymbol()
								.substring(0, 2)
								+ eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX() + 1, eagle.getPosY());

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					}
				} else {
					if (deltaY < 0) {

						previousPiece = maze
								.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.getSymbol().substring(0, 2)
								+ " ";

						nextPiece = maze
								.getMazePiece(eagle.getPosX(),
										eagle.getPosY() - 1).getSymbol()
								.substring(0, 2)
								+ eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX(), eagle.getPosY() - 1);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					} else {
						previousPiece = maze
								.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.getSymbol().substring(0, 2)
								+ " ";

						nextPiece = maze
								.getMazePiece(eagle.getPosX(),
										eagle.getPosY() + 1).getSymbol()
								.substring(0, 2)
								+ eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX(), eagle.getPosY() + 1);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					}
				}
			}
		} else if (eagle.getReturning()) {
			if (!hero.getDead()) {
				int deltaX = eagle.getHeroX() - eagle.getPosX();
				int deltaY = eagle.getHeroY() - eagle.getPosY();

				// Descend at arrival to the hero position
				if (deltaX == 0 && deltaY == 0) {
					if (eagle.getHeroX() != hero.getPosX()
							|| eagle.getHeroY() != hero.getPosY()) {
						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(" E ");
						eagle.setPosition(hero.getPosX(), hero.getPosY());
						hero.setEagle(true);
						eagle.setPursuing(false);
						eagle.setReturning(false);
					} else {
						hero.setEagle(true);
						hero.setArmed(true);
						eagle.setPursuing(false);
						eagle.setReturning(false);
					}
				}
				// Eagle movement in pursuit of the hero
				else if (Math.abs(deltaX) > Math.abs(deltaY)) {
					if (deltaX < 0) {
						previousPiece = " "
								+ maze.getMazePiece(eagle.getPosX(),
										eagle.getPosY()).getSymbol()
										.substring(1, 2) + " ";

						nextPiece = "E"
								+ maze.getMazePiece(eagle.getPosX() - 1,
										eagle.getPosY()).getSymbol()
										.substring(1, 2) + eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX() - 1, eagle.getPosY());

						maze.getSword().setPosition(eagle.getPosX() - 1,
								eagle.getPosY());

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					} else {
						previousPiece = " "
								+ maze.getMazePiece(eagle.getPosX(),
										eagle.getPosY()).getSymbol()
										.substring(1, 2) + " ";

						nextPiece = "E"
								+ maze.getMazePiece(eagle.getPosX() + 1,
										eagle.getPosY()).getSymbol()
										.substring(1, 2) + eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX() + 1, eagle.getPosY());

						maze.getSword().setPosition(eagle.getPosX() + 1,
								eagle.getPosY());

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					}
				}

				else {
					if (deltaY < 0) {
						previousPiece = " "
								+ maze.getMazePiece(eagle.getPosX(),
										eagle.getPosY()).getSymbol()
										.substring(1, 2) + " ";

						nextPiece = "E"
								+ maze.getMazePiece(eagle.getPosX(),
										eagle.getPosY() - 1).getSymbol()
										.substring(1, 2) + eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX(), eagle.getPosY() - 1);

						maze.getSword().setPosition(eagle.getPosX(),
								eagle.getPosY() - 1);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					} else {
						previousPiece = " "
								+ maze.getMazePiece(eagle.getPosX(),
										eagle.getPosY()).getSymbol()
										.substring(1, 2) + " ";

						nextPiece = "E"
								+ maze.getMazePiece(eagle.getPosX(),
										eagle.getPosY() + 1).getSymbol()
										.substring(1, 2) + eagle.showEagle();

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(previousPiece);

						eagle.setPosition(eagle.getPosX(), eagle.getPosY() + 1);

						maze.getSword().setPosition(eagle.getPosX(),
								eagle.getPosY() + 1);

						maze.getMazePiece(eagle.getPosX(), eagle.getPosY())
								.setSymbol(nextPiece);
					}
				}
			}
		}
	}

	public void checkDragonAtSword(Dragon dragon) {
		if (dragonAtSword(dragon)) {
			dragon.setAtSword(true);
		} else {
			if (!hero.getArmed())
				maze.getMazePiece(maze.getSword().getPosX(),
						maze.getSword().getPosY()).setSymbol(
						PieceType.SWORD.asString());
			dragon.setAtSword(false);
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
					System.out.println("\nHero killed a dragon");
					// Set dragon state as dead
					dragon.setDead(true);
					maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
							.setSymbol(dragon.showDragon(hero));
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
