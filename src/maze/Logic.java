package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import cli.Cli;

public class Logic extends Cli {

	public Maze maze;
	public ArrayList<Dragon> dragons;
	public Hero hero;
	private boolean finished;

	public Logic() {

		while (mainMenu()) {
			this.finished = false;
			int size = setMazeSize();
			if (size == 10) {
				hero = new Hero(1, 1);
				dragons = new ArrayList<Dragon>();
				dragons.add(new Dragon(1, 6));
				maze = new Maze(hero, dragons, 10);
			} else {
				hero = new Hero(0, 0);
				dragons = new ArrayList<Dragon>();
				maze = new Maze(hero, dragons, size, 2);
			}
			startGame();
		}
	}

	public void startGame() {
		// User choice to move the hero
		String userInput;
		// Dragon state variable
		int dragonState;

		Random random = new Random();

		displayMaze(maze.maze);

		while (!finished) {

			// Move hero
			HashMap<Integer, Boolean> validMoves = maze.getValidMoves(hero);
			userInput = getKey();
			moveHero(userInput, validMoves);

			// Dragon(s) processing
			for (int i = 0; i < dragons.size(); i++) {

				Dragon dragon = dragons.get(i);
				dragonState = random.nextInt(2);

				if (dragonState == 0)
					dragon.setAsleep(false);
				else {
					dragon.setAsleep(true);
					gameMessages("Dragon is asleep");
				}

				if (userInput.equals("q")) {
					finished = true;
					break;
				}

				// Move dragon
				if (!dragon.getDead()) {
					if (dragon.getAsleep())
						maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
								.setSymbol(dragon.showDragon(hero));
					else
						moveDragon(dragon);
				}
			}

			// Check game status and finally display the maze
			String state = checkGame();
			displayMaze(maze.maze);

			if (state.equals(GameState.HERO_WON.toString())) {
				finished = true;

				// Escrever uma mensagem para informar que o jogador venceu o
				// jogo
				gameMessages("\nHero won :)");
			} else if (state.equals(GameState.HERO_DIED.toString())) {
				finished = true;

				// Escrever uma mensagem para informar que o jogador morreu
				gameMessages("\nHero died :(");
			}
		}
	}

	/**
	 * Esta funcao vai receber como parametro uma nova posicao para movimentar o
	 * jogador e um HashMap que se certifica que a proxima posicao e
	 * possivel(nao e parede por exemplo) Se for valida entao movimenta, caso
	 * contrario mostra uma mensagem de erro
	 */
	public void moveHero(String userInput, HashMap<Integer, Boolean> moves) {
		// Mover jogador para cima
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
		char symbol;
		if (direction == Movement.MOVE_UP.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX, posY - 1);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX, posY - 1);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX, posY - 1).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (direction == Movement.MOVE_DOWN.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX, posY + 1);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX, posY + 1);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX, posY + 1).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (direction == Movement.MOVE_RIGHT.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX + 1, posY);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX + 1, posY);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX + 1, posY).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (direction == Movement.MOVE_LEFT.getDirection()) {
			symbol = maze.getMazePieceSymbol(posX - 1, posY);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX - 1, posY);
				checkDragonAtSword(dragon);

				maze.getMazePiece(posX - 1, posY).setSymbol(
						dragon.showDragon(hero));
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
						PieceType.SWORD.asChar());
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
			/**
			 * Depois de movimentar o hero temos que verificar se a nova posicao
			 * e adjacente a posicao onde esta o dragon atualmente.
			 * 
			 * Se for e o hero nao tiver espada entao o hero morre. No caso de
			 * estar armado entao o dragon morre e desaparece
			 */
			if (!hero.getArmed()) {
				if (!dragon.getDead() && !dragon.getAsleep()) {
					// Se o hero nao estiver armado entao o jogo termina com a
					// morte do hero
					return GameState.HERO_DIED.toString();
				}
			} else {
				// Neste caso o hero esta armado
				if (!dragon.getDead() || dragon.getAsleep()) {
					System.out.println("\nHero killed a dragon");
					// Alterar o estado do dragon para morto
					dragon.setDead(true);
					maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
							.setSymbol(dragon.showDragon(hero));
				}
			}
		}

		return GameState.CONTINUE_GAME.toString();
	}

	public Dragon adjacentDragon() {
		// Estas variaveis foram declaradas para nao se estar constantemente a
		// chamar as funcoes que retornam as posicoes
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
