package maze;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Logic {

	/**
	 * Cada jogo inicialmente tera um maze, um hero e um dragon como principais
	 * elementos
	 */
	public Maze maze;
	public Hero hero;
	public Dragon dragon;
	private boolean finished;
	Random random = new Random();

	public Logic() {
		finished = false;
	}

	public void askMazeSize() {
		int size;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Puzzle normal/aleatorio? (0/1)");
		size = scanner.nextInt();
		while (size != 0 && size != 1) {
			System.out.println("Invalid number! Try again...");
			size = scanner.nextInt();
		}

		if (size == 0) {
			hero = new Hero(1, 1);
			dragon = new Dragon(1, 6);
			maze = new Maze(hero, dragon, 1);
		} else {
			// TODO: Melhorar esta funcao mais tarde. So funciona porque (0,0) e
			// sempre parede
			hero = new Hero(0, 0);
			dragon = new Dragon(0, 0);
			maze = new Maze(hero, dragon, 0);
		}

		startGame(size); // adicionei(Paulo) o parametro size para que o dragon
							// se comporte de maneira + simples no maze default
	}

	public void startGame(int size) {
		// Variavel que armazena a escolha do utilizador para movimentar o hero
		char userInput;
		// Variavel que define o estado do dragon
		int dragonState;

		// Utilizado para receber input do jogador
		Scanner scanner = new Scanner(System.in);
		Random r = new Random();
		/**
		 * Mostrar uma mensagem ao jogador no qual ele(a) e informado sobre o
		 * objetivo do jogo
		 */
		System.out.println("Encontrar a saida(S) para vencer o dragon!");
		maze.showMaze();
		while (!finished) {
			dragonState = r.nextInt(2);
			System.out.print("Escolher nova posicao do hero: ");
			userInput = scanner.next().charAt(0);

			HashMap<Integer, Boolean> validMoves = maze.getValidMoves(hero);

			if (dragonState == 0 || size == 0)
				dragon.setAsleep(false);
			else {
				dragon.setAsleep(true);
				System.out.println("Dragon is asleep");
			}
			// Move hero
			moveHero(userInput, validMoves);
			// Move dragon
			if (!dragon.getDead()) {
				if (dragon.getAsleep())
					maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
							.setSymbol(dragon.showDragon(hero));
				else
					moveDragon();
			}
			String state = checkGame();
			// E finalmente mostrar o maze
			maze.showMaze();

			if (state.equals(GameState.HERO_WON.toString())) {
				finished = true;

				// Escrever uma mensagem para informar que o jogador venceu o
				// jogo
				System.out.println("Venceste o jogo!!");
			} else if (state.equals(GameState.HERO_DIED.toString())) {
				finished = true;

				// Escrever uma mensagem para informar que o jogador morreu
				System.out.println("Foste morto pelo dragon!!!");
			}
		}
	}

	/**
	 * Esta funcao vai receber como parametro uma nova posicao para movimentar o
	 * jogador e um HashMap que se certifica que a proxima posicao e
	 * possivel(nao e parede por exemplo) Se for valida entao movimenta, caso
	 * contrario mostra uma mensagem de erro
	 */
	public void moveHero(int pos, HashMap<Integer, Boolean> moves) {
		// Mover jogador para cima
		if (pos == 'w') {
			// Verificar se o HashMap contem a key com o valor 0(cima)
			if (moves.containsKey(Movement.MOVE_UP.getDirecaoInt())) {
				// Alterar a peca atual onde o hero esta para uma peca livre
				maze.swapPieces(Movement.MOVE_UP.getDirecaoInt(), hero);
			} else
				System.err.println("Nao pode mover o hero para cima!");
		} else if (pos == 's') {
			// Verificar se o HashMap contem a key com o valor 1(baixo)
			if (moves.containsKey(Movement.MOVE_DOWN.getDirecaoInt())) {
				maze.swapPieces(Movement.MOVE_DOWN.getDirecaoInt(), hero);
			} else
				System.err.println("Nao pode mover o hero para baixo!");
		} else if (pos == 'a') {
			// Verificar se o HashMap contem a key com o valor 3(esquerda)
			if (moves.containsKey(Movement.MOVE_LEFT.getDirecaoInt())) {
				maze.swapPieces(Movement.MOVE_LEFT.getDirecaoInt(), hero);
			} else
				System.err.println("Nao pode mover o hero para a esquerda!");
		} else if (pos == 'd') {
			// Verificar se o HashMap contem a key com o valor 2(direita)
			if (moves.containsKey(Movement.MOVE_RIGHT.getDirecaoInt())) {
				maze.swapPieces(Movement.MOVE_RIGHT.getDirecaoInt(), hero);
			} else
				System.err.println("Nao pode mover o hero para a direita!");
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
	public void moveDragon( ) {
		int posX = dragon.getPosX();
		int posY = dragon.getPosY();
		int dir = random.nextInt(4);
		char symbol;
		if (dir == Movement.MOVE_UP.getDirecaoInt()) {
			symbol = maze.getMazePieceSymbol(posX, posY - 1);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX, posY - 1);
				checkDragonAtSword();

				maze.getMazePiece(posX, posY - 1).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (dir == Movement.MOVE_DOWN.getDirecaoInt()) {
			symbol = maze.getMazePieceSymbol(posX, posY + 1);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX, posY + 1);
				checkDragonAtSword();

				maze.getMazePiece(posX, posY + 1).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (dir == Movement.MOVE_RIGHT.getDirecaoInt()) {
			symbol = maze.getMazePieceSymbol(posX + 1, posY);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX + 1, posY);
				checkDragonAtSword();

				maze.getMazePiece(posX + 1, posY).setSymbol(
						dragon.showDragon(hero));
			}
		} else if (dir == Movement.MOVE_LEFT.getDirecaoInt()) {
			symbol = maze.getMazePieceSymbol(posX - 1, posY);
			if (symbol != PieceType.WALL.asChar() && symbol != hero.showHero()
					&& symbol != PieceType.EXIT.asChar()) {
				maze.getMazePiece(posX, posY)
						.setSymbol(PieceType.FREE.asChar());
				dragon.setPosition(posX - 1, posY);
				checkDragonAtSword();

				maze.getMazePiece(posX - 1, posY).setSymbol(
						dragon.showDragon(hero));
			}
		}
	}

	public void checkDragonAtSword() {
		if (dragonAtSword()) {
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

		if (adjacentDragon()) {
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
					System.out.println("O dragon esta morto!!");
					// Alterar o estado do dragon para morto
					dragon.setDead(true);
					maze.getMazePiece(dragon.getPosX(), dragon.getPosY())
							.setSymbol(dragon.showDragon(hero));
				}
			}
		}

		return GameState.CONTINUE_GAME.toString();
	}

	public boolean adjacentDragon() {
		// Estas variaveis foram declaradas para nao se estar constantemente a
		// chamar as funcoes que retornam as posicoes
		int heroX = hero.getPosX();
		int heroY = hero.getPosY();
		int dragonX = dragon.getPosX();
		int dragonY = dragon.getPosY();

		if (heroX + 1 == dragonX && heroY == dragonY)
			return true;
		else if (heroX - 1 == dragonX && heroY == dragonY)
			return true;
		else if (heroX == dragonX && heroY - 1 == dragonY)
			return true;
		else if (heroX == dragonX && heroY + 1 == dragonY)
			return true;
		else
			return false;
	}

	public boolean dragonAtSword() {
		return (dragon.getPosX() == maze.getSword().getPosX())
				&& (dragon.getPosY() == maze.getSword().getPosY());
	}
}
