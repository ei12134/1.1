package maze;

import algorithms.Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Maze {

	public ArrayList<ArrayList<Piece>> maze;
	private Piece exit;
	private Piece sword;

	public Maze(Hero hero, ArrayList<Dragon> dragons, int mazeSize) {
		maze = new ArrayList<ArrayList<Piece>>();
		startDefaultMaze(hero, dragons);
	}

	public Maze(Hero hero, ArrayList<Dragon> dragons, int mazeSize,
			int dragonCounter) {
		maze = new ArrayList<ArrayList<Piece>>();
		startRandomMaze(hero, dragons, mazeSize, dragonCounter);
	}

	private void startDefaultMaze(Hero hero, ArrayList<Dragon> dragons) {
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
		maze.get(dragons.get(0).getPosY()).get(dragons.get(0).getPosX())
				.setSymbol(dragons.get(0).showDragon(hero));
	}

	private void startRandomMaze(Hero hero, ArrayList<Dragon> dragons,
			int mazeSize, int dragonCounter) {

		Algorithm algorithm = new Algorithm(mazeSize);
		maze = algorithm.createMaze();

		// Find the appropriate positions for game elements
		int posX, posY;

		// Random number between [1, maze size - 1]
		posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		while (maze.get(posY).get(posX).getSymbol() == PieceType.WALL.asChar()
				|| maze.get(posY).get(posX).getSymbol() == PieceType.EXIT
						.asChar()) {
			posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		}
	
		// Set dragon(s) position(s)
		addDragons(hero, dragons, dragonCounter);
		
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
	 * Checks each of the 4 positions around hero for availability and if adds
	 * it to HasMap validMoves
	 * 
	 * @param hero
	 *            An object of the class Hero used as a reference for available
	 *            positions
	 * @return HashMap with possible positions for hero to move into
	 */

	public HashMap<Integer, Boolean> getValidMoves(Hero hero) {
		HashMap<Integer, Boolean> validMoves = new HashMap<Integer, Boolean>();
		boolean heroArmado = hero.getArmed();

		// Check if hero can move up
		if (hero.getPosY() - 1 >= 0)
			if (maze.get(hero.getPosY() - 1).get(hero.getPosX()).getSymbol() != PieceType.WALL
					.asChar()) {
				// Confirms if hero is armed at exit
				if (!((maze.get(hero.getPosY() - 1).get(hero.getPosX())
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					validMoves.put(0, true);
			}

		// Check if hero can move down
		if (hero.getPosY() + 1 < maze.size())
			if (maze.get(hero.getPosY() + 1).get(hero.getPosX()).getSymbol() != PieceType.WALL
					.asChar()) {
				if (!((maze.get(hero.getPosY() + 1).get(hero.getPosX())
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					validMoves.put(1, true);
			}

		// Check if hero can move to the right
		if (hero.getPosX() + 1 < maze.size())
			if (maze.get(hero.getPosY()).get(hero.getPosX() + 1).getSymbol() != PieceType.WALL
					.asChar()) {
				if (!((maze.get(hero.getPosY()).get(hero.getPosX() + 1)
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					validMoves.put(2, true);
			}

		// Check if hero can move to the left
		if (hero.getPosX() - 1 >= 0)
			if (maze.get(hero.getPosY()).get(hero.getPosX() - 1).getSymbol() != PieceType.WALL
					.asChar()) {
				if (!((maze.get(hero.getPosY()).get(hero.getPosX() - 1)
						.getSymbol() == PieceType.EXIT.asChar()) && !heroArmado))
					validMoves.put(3, true);
			}
		return validMoves;
	}

	/**
	 * Esta funcao e utilizada para trocar duas Pieces. Alem de trocar duas
	 * Pieces ainda verifica se o hero esta armado ou nao atraves de uma funcao
	 * auxiliar Quando marcamos o simbolo no maze o programa verifica se o hero
	 * tem SWORD ou nao Se tiver entao mostra um 'A', caso contrario mostra um
	 * 'H'
	 */
	public void swapPieces(int direction, Hero hero) {
		if (direction == Movement.MOVE_UP.getDirection()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& ArmedNextPiece(Movement.MOVE_UP.getDirection(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX(), hero.getPosY() - 1).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX(), hero.getPosY() - 1);
		} else if (direction == Movement.MOVE_DOWN.getDirection()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& ArmedNextPiece(Movement.MOVE_DOWN.getDirection(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX(), hero.getPosY() + 1).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX(), hero.getPosY() + 1);
		} else if (direction == Movement.MOVE_RIGHT.getDirection()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& ArmedNextPiece(Movement.MOVE_RIGHT.getDirection(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX() + 1, hero.getPosY()).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX() + 1, hero.getPosY());
		} else if (direction == Movement.MOVE_LEFT.getDirection()) {
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asChar());

			if (!hero.getArmed()
					&& ArmedNextPiece(Movement.MOVE_LEFT.getDirection(), hero))
				hero.setArmed(true);
			getMazePiece(hero.getPosX() - 1, hero.getPosY()).setSymbol(
					hero.showHero());

			hero.setPosition(hero.getPosX() - 1, hero.getPosY());
		}
	}

	public boolean ArmedNextPiece(int pos, Hero hero) {
		int posX = hero.getPosX();
		int posY = hero.getPosY();
		if (pos == Movement.MOVE_UP.getDirection()) {
			if ((posX == sword.getPosX()) && (posY - 1 == sword.getPosY()))
				return true;
		} else if (pos == Movement.MOVE_DOWN.getDirection()) {
			if ((posX == sword.getPosX()) && (posY + 1 == sword.getPosY()))
				return true;
		} else if (pos == Movement.MOVE_RIGHT.getDirection()) {
			if ((posX + 1 == sword.getPosX()) && (posY == sword.getPosY()))
				return true;
		} else if ((pos == Movement.MOVE_LEFT.getDirection())) {
			if ((posX - 1 == sword.getPosX()) && (posY == sword.getPosY()))
				return true;
		}

		return false;
	}

	Piece getAvailablePosition() {
		int randomX = 0, randomY = 0;
		Random r = new Random();

		for (int i = 0; i < maze.size() * maze.size(); i++) {
			randomX = 1 + r.nextInt(maze.size() - 1);
			randomY = 1 + r.nextInt(maze.size() - 1);

			if (maze.get(randomX).get(randomY).getSymbol() == (PieceType.FREE
					.asChar()))
				return maze.get(randomX).get(randomY);
		}
		return null;
	}

	public void addDragons(Hero hero, ArrayList<Dragon> dragons,
			int dragonCounter) {
		for (int i = 0; i < dragonCounter; i++) {
			Piece newDragonPiece = getAvailablePosition();
			if (newDragonPiece != null)
				dragons.add(new Dragon(newDragonPiece.getPosX(), newDragonPiece
						.getPosY()));
		}

		for (int i = 0; i < dragons.size(); i++)
			maze.get(dragons.get(i).getPosY()).get(dragons.get(i).getPosX())
					.setSymbol(dragons.get(i).showDragon(hero));

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
