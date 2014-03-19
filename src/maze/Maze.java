package maze;

import algorithms.Algorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class Maze {

	public ArrayList<ArrayList<Piece>> maze;

	private Piece exit;
	private Piece sword;

	public Maze(Hero hero, Dragon dragon, int size) {
		maze = new ArrayList<ArrayList<Piece>>();
		if (size == 10)
			startDefaultMaze(hero, dragon);
		else
			startRandomMaze(hero, dragon, size);
	}

	private void startDefaultMaze(Hero hero, Dragon dragao) {
		exit = new Piece(9, 5, PieceType.EXIT.asChar());
		sword = new Piece(1, 8, PieceType.SWORD.asChar());

		for (int i = 0; i < 10; i++) {
			ArrayList<Piece> linha = new ArrayList<Piece>();
			for (int j = 0; j < 10; j++) {
				linha.add(new Piece(i, j, PieceType.WALL.asChar()));
			}

			maze.add(linha);
		}

		for (int i = 2; i < 9; i++)
			maze.get(1).get(i).setSymbol(PieceType.FREE.asChar());
		for (int i = 2; i < 9; i++)
			maze.get(i).get(1).setSymbol(PieceType.FREE.asChar());

		for (int i = 4; i < 9; i += 2) {
			for (int j = 1; j < 9; j++)
				maze.get(j).get(i).setSymbol(PieceType.FREE.asChar());
		}

		for (int i = 1; i < 9; i++)
			if (i != 7)
				maze.get(5).get(i).setSymbol(PieceType.FREE.asChar());

		for (int i = 1; i < 9; i++)
			if (i != 2 && i != 3)
				maze.get(8).get(i).setSymbol(PieceType.FREE.asChar());

		maze.get(hero.getPosY()).get(hero.getPosX()).setSymbol(hero.showHero());
		maze.get(exit.getPosY()).get(exit.getPosX())
				.setSymbol(PieceType.EXIT.asChar());
		maze.get(sword.getPosY()).get(sword.getPosX())
				.setSymbol(PieceType.SWORD.asChar());
		maze.get(dragao.getPosY()).get(dragao.getPosX())
				.setSymbol(dragao.showDragon(hero));
	}

	private void startRandomMaze(Hero hero, Dragon dragon, int size) {

		Algorithm algorithm = new Algorithm(size);
		maze = algorithm.createMaze();

		/**
		 * Aqui ja temos o tabuleio gerado, agora temos que encontrar as
		 * posicoes corretas para posicionar os varios elementos do jogo
		 */
		int posX, posY;
		// Gerar um numero aleatorio entre [1, tamanho do maze - 1]
		posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		while (maze.get(posY).get(posX).getSymbol() == PieceType.WALL.asChar()
				|| maze.get(posY).get(posX).getSymbol() == PieceType.EXIT
						.asChar()) {
			posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		}
		// Set hero position
		hero.setPosition(posX, posY);
		maze.get(hero.getPosY()).get(hero.getPosX()).setSymbol(hero.showHero());

		posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		while (maze.get(posY).get(posX).getSymbol() == PieceType.WALL.asChar()
				|| maze.get(posY).get(posX).getSymbol() == PieceType.EXIT
						.asChar()
				|| maze.get(posY).get(posX).getSymbol() == hero.showHero()) {
			posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		}
		// Set dragon position
		dragon.setPosition(posX, posY);
		maze.get(dragon.getPosY()).get(dragon.getPosX())
				.setSymbol(dragon.showDragon(hero));

		posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		while (maze.get(posY).get(posX).getSymbol() == PieceType.WALL.asChar()
				|| maze.get(posY).get(posX).getSymbol() == PieceType.EXIT
						.asChar()
				|| maze.get(posY).get(posX).getSymbol() == hero.showHero()
				|| maze.get(posY).get(posX).getSymbol() == dragon
						.showDragon(hero)) {
			posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		}
		// Set sword position
		sword = new Piece(posX, posY, PieceType.SWORD.asChar());
		maze.get(sword.getPosY()).get(sword.getPosX())
				.setSymbol(PieceType.SWORD.asChar());

		// Set exit position
		for (int i = 0; i < maze.size(); i++)
			for (int j = 0; j < maze.get(i).size(); j++)
				if (maze.get(j).get(i).getSymbol() == PieceType.EXIT.asChar()) {
					exit = new Piece(i, j, PieceType.EXIT.asChar());
					break;
				}
	}

	/**
	 * Esta funcao verifica as 4 posicoes em redor do hero. Para cada uma delas
	 * e testado se essa posicao esta FREE. Caso esteja entao e adicionada a um
	 * HashMap
	 * 
	 * @param hero
	 *            Um objeto da classe Heroi utilizado para verificar todas as
	 *            posicoes que o jogador pode tomar
	 * @return Um HashMap constituido pelas posicoes que o hero pode tomar no
	 *         proximo passo
	 */

	public HashMap<Integer, Boolean> getValidMoves(Hero hero) {
		HashMap<Integer, Boolean> movimentosPossiveis = new HashMap<Integer, Boolean>();
		boolean heroArmado = hero.getArmed();

		// Testar se o hero se pode mover para cima
		if (hero.getPosY() - 1 >= 0)
			if (maze.get(hero.getPosY() - 1).get(hero.getPosX()).getSymbol() != PieceType.WALL
					.asChar()) {
				// Esta condicao assegura que o movimento para a Piece EXIT so
				// sera possivel se o hero estiver armado
				if (!((maze.get(hero.getPosY() - 1).get(hero.getPosX())
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					movimentosPossiveis.put(0, true);
			}

		// Testar se o hero se pode mover para baixo
		if (hero.getPosY() + 1 < maze.size())
			if (maze.get(hero.getPosY() + 1).get(hero.getPosX()).getSymbol() != PieceType.WALL
					.asChar()) {
				if (!((maze.get(hero.getPosY() + 1).get(hero.getPosX())
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					movimentosPossiveis.put(1, true);
			}

		// Testar se o hero se pode mover para a direita
		if (hero.getPosX() + 1 < maze.size())
			if (maze.get(hero.getPosY()).get(hero.getPosX() + 1).getSymbol() != PieceType.WALL
					.asChar()) {
				if (!((maze.get(hero.getPosY()).get(hero.getPosX() + 1)
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					movimentosPossiveis.put(2, true);
			}

		// Testar se o hero se pode mover para a esquerda
		if (hero.getPosX() - 1 >= 0)
			if (maze.get(hero.getPosY()).get(hero.getPosX() - 1).getSymbol() != PieceType.WALL
					.asChar()) {
				if (!((maze.get(hero.getPosY()).get(hero.getPosX() - 1)
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					movimentosPossiveis.put(3, true);
			}

		return movimentosPossiveis;
	}

	/**
	 * Esta funcao e utilizada para trocar duas Pieces. Alem de trocar duas
	 * Pieces ainda verifica se o hero esta armado ou nao atraves de uma funcao
	 * auxiliar Quando marcamos o simbolo no maze o programa verifica se o hero
	 * tem SWORD ou nao Se tiver entao mostra um 'A', caso contrario mostra um
	 * 'H'
	 */
	public void swapPieces(int direcao, Hero hero) {
		if (direcao == Movement.MOVE_UP.getDirecaoInt()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& PieceHeroiArmadoProxima(
							Movement.MOVE_UP.getDirecaoInt(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX(), hero.getPosY() - 1).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX(), hero.getPosY() - 1);
		} else if (direcao == Movement.MOVE_DOWN.getDirecaoInt()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& PieceHeroiArmadoProxima(
							Movement.MOVE_DOWN.getDirecaoInt(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX(), hero.getPosY() + 1).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX(), hero.getPosY() + 1);
		} else if (direcao == Movement.MOVE_RIGHT.getDirecaoInt()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& PieceHeroiArmadoProxima(
							Movement.MOVE_RIGHT.getDirecaoInt(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX() + 1, hero.getPosY()).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX() + 1, hero.getPosY());
		} else if (direcao == Movement.MOVE_LEFT.getDirecaoInt()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& PieceHeroiArmadoProxima(
							Movement.MOVE_LEFT.getDirecaoInt(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX() - 1, hero.getPosY()).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX() - 1, hero.getPosY());
		}
	}

	public boolean PieceHeroiArmadoProxima(int pos, Hero hero) {
		int posX = hero.getPosX();
		int posY = hero.getPosY();
		if (pos == Movement.MOVE_UP.getDirecaoInt()) {
			if ((posX == sword.getPosX()) && (posY - 1 == sword.getPosY()))
				return true;
		} else if (pos == Movement.MOVE_DOWN.getDirecaoInt()) {
			if ((posX == sword.getPosX()) && (posY + 1 == sword.getPosY()))
				return true;
		} else if (pos == Movement.MOVE_RIGHT.getDirecaoInt()) {
			if ((posX + 1 == sword.getPosX()) && (posY == sword.getPosY()))
				return true;
		} else if ((pos == Movement.MOVE_LEFT.getDirecaoInt())) {
			if ((posX - 1 == sword.getPosX()) && (posY == sword.getPosY()))
				return true;
		}

		return false;
	}

	public int getExitX() {
		return exit.getPosX();
	}

	public int getExitY() {
		return exit.getPosY();
	}

	public char getMazePieceSymbol(int x, int y) {
		return maze.get(y).get(x).getSymbol();
	}

	public Piece getMazePiece(int x, int y) {
		return maze.get(y).get(x);
	}

	public Piece getSword() {
		return sword;
	}
}
