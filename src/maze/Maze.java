package maze;

import algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Random;

public class Maze {

	protected ArrayList<ArrayList<Piece>> maze;
	protected ArrayList<Dragon> dragons;
	protected Hero hero;
	protected Eagle eagle;
	protected Piece exit;
	protected Piece sword;

	public Maze() {
		maze = new ArrayList<ArrayList<Piece>>();
		hero = new Hero(1, 1);
		eagle = new Eagle(1, 1);
		dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 6));
		startDefaultMaze();
	}

	public Maze(int mazeSize, int dragonCounter) {
		maze = new ArrayList<ArrayList<Piece>>();
		hero = new Hero(0, 0);
		eagle = new Eagle(0, 0);
		dragons = new ArrayList<Dragon>();
		startRandomMaze(mazeSize, dragonCounter);
	}

	private void startDefaultMaze() {
		exit = new Piece(9, 5, PieceType.EXIT.asString());
		sword = new Piece(1, 8, PieceType.SWORD.asString());

		for (int i = 0; i < 10; i++) {
			ArrayList<Piece> linha = new ArrayList<Piece>();
			for (int j = 0; j < 10; j++)
				linha.add(new Piece(i, j, PieceType.WALL.asString()));

			maze.add(linha);
		}

		for (int i = 2; i < 9; i++)
			maze.get(1).get(i).setSymbol(PieceType.FREE.asString());
		for (int i = 2; i < 9; i++)
			maze.get(i).get(1).setSymbol(PieceType.FREE.asString());

		for (int i = 4; i < 9; i += 2) {
			for (int j = 1; j < 9; j++)
				maze.get(j).get(i).setSymbol(PieceType.FREE.asString());
		}

		for (int i = 1; i < 9; i++)
			if (i != 7)
				maze.get(5).get(i).setSymbol(PieceType.FREE.asString());

		for (int i = 1; i < 9; i++)
			if (i != 2 && i != 3)
				maze.get(8).get(i).setSymbol(PieceType.FREE.asString());

		maze.get(hero.getPosY()).get(hero.getPosX()).setSymbol(hero.showHero());
		maze.get(exit.getPosY()).get(exit.getPosX())
				.setSymbol(PieceType.EXIT.asString());
		maze.get(sword.getPosY()).get(sword.getPosX())
				.setSymbol(PieceType.SWORD.asString());
		maze.get(dragons.get(0).getPosY()).get(dragons.get(0).getPosX())
				.setSymbol(dragons.get(0).showDragon());
	}

	private void startRandomMaze(int mazeSize, int dragonCounter) {

		Algorithm algorithm = new Algorithm(mazeSize);
		maze = algorithm.createMaze();

		// Find the appropriate positions for game elements
		int posX, posY;

		// Random number between [1, maze size - 1]
		posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		while (maze.get(posY).get(posX).getSymbol() == PieceType.WALL
				.asString()
				|| maze.get(posY).get(posX).getSymbol() == PieceType.EXIT
						.asString()) {
			posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		}

		// Set dragon(s) position(s)
		addDragons(dragonCounter);

		// Set hero position
		hero.setPosition(posX, posY);
		maze.get(hero.getPosY()).get(hero.getPosX()).setSymbol(hero.showHero());

		posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		while (maze.get(posY).get(posX).getSymbol() == PieceType.WALL
				.asString()
				|| maze.get(posY).get(posX).getSymbol() == PieceType.EXIT
						.asString()
				|| maze.get(posY).get(posX).getSymbol() == hero.showHero()) {
			posX = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
			posY = 1 + (int) (Math.random() * ((maze.size() - 1 - 1) + 1));
		}

		// Set eagle position
		eagle.setPosition(hero.getPosX(), hero.getPosY());

		// Set sword position
		sword = new Piece(posX, posY, PieceType.SWORD.asString());
		maze.get(sword.getPosY()).get(sword.getPosX())
				.setSymbol(PieceType.SWORD.asString());

		// Set exit position
		for (int i = 0; i < maze.size(); i++)
			for (int j = 0; j < maze.get(i).size(); j++)
				if (maze.get(j).get(i).getSymbol() == PieceType.EXIT.asString()) {
					exit = new Piece(i, j, PieceType.EXIT.asString());
					break;
				}
	}

	/**
	 * Swaps hero position with a given valid direction
	 */
	public void swapHero(int direction) {

		int nextX = 0;
		int nextY = 0;

		switch (direction) {

		case (0):
			// Move Up
			nextX = hero.getPosX();
			nextY = hero.getPosY() - 1;
			break;

		case (1):
			// Move Down
			nextX = hero.getPosX();
			nextY = hero.getPosY() + 1;
			break;

		case (2):
			// Move Right
			nextX = hero.getPosX() + 1;
			nextY = hero.getPosY();
			break;

		case (3):
			// Move Left
			nextX = hero.getPosX() - 1;
			nextY = hero.getPosY();
			break;
		default:
			break;
		}

		if (nextX != 0 && nextY != 0) {
			// Check for sword in next position
			if (!hero.getArmed()) {
				if ((nextX == sword.getPosX()) && (nextY == sword.getPosY())) {
					if (eagle.getState().equals(State.EAGLE_GROUND)) {
						eagle.setState(State.EAGLE_FOLLOWING);
						hero.setEagle(true);
					}
					hero.setArmed(true);
				}
			}
			// Clear current position
			getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
					PieceType.FREE.asString());
			// Set next position
			getMazePiece(nextX, nextY).setSymbol(hero.showHero());
			hero.setPosition(nextX, nextY);
		}
	}

	public boolean ArmedNextPiece(int direction) {
		int posX = hero.getPosX();
		int posY = hero.getPosY();
		if (direction == Movement.MOVE_UP.getDirection()) {
			if ((posX == sword.getPosX()) && (posY - 1 == sword.getPosY()))
				return true;
		} else if (direction == Movement.MOVE_DOWN.getDirection()) {
			if ((posX == sword.getPosX()) && (posY + 1 == sword.getPosY()))
				return true;
		} else if (direction == Movement.MOVE_RIGHT.getDirection()) {
			if ((posX + 1 == sword.getPosX()) && (posY == sword.getPosY()))
				return true;
		} else if ((direction == Movement.MOVE_LEFT.getDirection())) {
			if ((posX - 1 == sword.getPosX()) && (posY == sword.getPosY()))
				return true;
		}

		return false;
	}

	public Piece getAvailablePosition() {
		int randomX = 0, randomY = 0;
		Random r = new Random();

		for (int i = 0; i < maze.size() * maze.size(); i++) {
			randomX = 1 + r.nextInt(maze.size() - 1);
			randomY = 1 + r.nextInt(maze.size() - 1);

			if (maze.get(randomX).get(randomY).getSymbol() == (PieceType.FREE
					.asString()))
				return maze.get(randomX).get(randomY);
		}
		return null;
	}

	public void addDragons(int dragonCounter) {
		for (int i = 0; i < dragonCounter; i++) {
			Piece newDragonPiece = getAvailablePosition();
			if (newDragonPiece != null) {
				dragons.add(new Dragon(newDragonPiece.getPosX(), newDragonPiece
						.getPosY()));
				maze.get(dragons.get(i).getPosY())
						.get(dragons.get(i).getPosX())
						.setSymbol(dragons.get(i).showDragon());
			}
		}
	}

	public int getExitX() {
		return exit.getPosX();
	}

	public int getExitY() {
		return exit.getPosY();
	}

	public String getMazePieceSymbol(int x, int y) {
		return maze.get(y).get(x).getSymbol();
	}

	public Piece getMazePiece(int x, int y) {
		return maze.get(y).get(x);
	}

	public Piece getSword() {
		return sword;
	}

	public ArrayList<ArrayList<Piece>> getMaze() {
		return maze;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setDragons(ArrayList<Dragon> dragons) {
		this.dragons = dragons;
	}
}
