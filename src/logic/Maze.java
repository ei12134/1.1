package logic;

import algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Random;



/**
 * Maze is the class used to store the maze and all its pieces.
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Maze {

	protected ArrayList<ArrayList<Piece>> maze;
	protected ArrayList<Dragon> dragons;
	protected Hero hero;
	protected Eagle eagle;
	protected Piece exit;
	protected Piece sword;

	
	
	/**
	 * Default constructor used to create a standard maze.
	 */
	public Maze() {
		maze = new ArrayList<ArrayList<Piece>>();
		hero = new Hero(1, 1);
		eagle = new Eagle(1, 1);
		dragons = new ArrayList<Dragon>();
		dragons.add(new Dragon(1, 6));
		startDefaultMaze();
	}

	/**
	 * Constructor of the random maze with custom maze size and dragon counter.
	 * 
	 * @param mazeSize
	 *            user defined maze size
	 * @param dragonCounter
	 *            user define dragon counter
	 */
	public Maze(int mazeSize, int dragonCounter) {
		maze = new ArrayList<ArrayList<Piece>>();
		hero = new Hero(0, 0);
		eagle = new Eagle(0, 0);
		dragons = new ArrayList<Dragon>();
		startRandomMaze(mazeSize, dragonCounter);
	}
	
	
	public Maze(ArrayList<ArrayList<Piece>> maze) {
		this.maze = maze;
		hero = new Hero(0, 0);
		eagle = new Eagle(0, 0);
		dragons = new ArrayList<Dragon>();
		
		
		setMazeData(maze, hero, eagle, dragons);
	}
	
	
	public void setMazeData(ArrayList<ArrayList<Piece>> maze, Hero hero, Eagle eagle, ArrayList<Dragon> dragons) {
		for(int i = 0; i < maze.size(); i++) {
			for(int j = 0; j < maze.get(i).size(); j++) {
				if(maze.get(i).get(j).getSymbol().equals(PieceType.HERO_UNARMED_EAGLE.asString())) {
					hero.setPosition(j, i);
					eagle.setPosition(j, i);
				} else if(maze.get(i).get(j).getSymbol().equals(PieceType.DRAGON.asString())) {
					dragons.add(new Dragon(j, i));
				}
			}
		}
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

		// Set dragon(s) position(s)
		addDragons(dragonCounter);

		// Set exit position
		for (int i = 0; i < maze.size(); i++)
			for (int j = 0; j < maze.get(i).size(); j++)
				if (maze.get(j).get(i).getSymbol() == PieceType.EXIT.asString()) {
					exit = new Piece(i, j, PieceType.EXIT.asString());
					break;
				}
	}

	/**
	 * Exchanges positions between the hero and a valid near piece.
	 * 
	 * @param direction
	 *            hero move direction
	 * @return message related to the hero move, null if none
	 */
	public String swapHero(int direction) {

		int nextX = hero.getPosX(), nextY = hero.getPosY();
		String message = null;

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

		// Check for sword in next position
		if (!hero.getArmed()) {
			if ((nextX == sword.getPosX()) && (nextY == sword.getPosY())) {
				if (eagle.getState().equals(State.EAGLE_GROUND)
						&& !eagle.getDead()) {
					eagle.setState(State.EAGLE_FOLLOWING);
					hero.setEagle(true);
				}
				hero.setArmed(true);
				message = "Hero is now armed";
			}
		}
		// Clear current position
		getMazePiece(hero.getPosX(), hero.getPosY()).setSymbol(
				PieceType.FREE.asString());
		// Set next position
		getMazePiece(nextX, nextY).setSymbol(hero.showHero());
		hero.setPosition(nextX, nextY);
		return message;
	}

	/**
	 * Searches for available position used to place new game components.
	 * 
	 * @return free available Piece of the maze
	 */
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

	/**
	 * Adds user defined number of dragons.
	 * 
	 * @param dragonCounter
	 *            number of dragons to add
	 */
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

	/**
	 * Gets 2 dimensional <code>ArrayList</code> containing all the maze Pieces.
	 * 
	 * @return maze
	 */
	public ArrayList<ArrayList<Piece>> getMaze() {
		return maze;
	}

	/**
	 * Gets exit X position.
	 * 
	 * @return exit X position
	 */
	public int getExitX() {
		return exit.getPosX();
	}

	/**
	 * Gets exit Y position.
	 * 
	 * @return exit Y position
	 */
	public int getExitY() {
		return exit.getPosY();
	}

	/**
	 * Gets maze symbol from given coordinates.
	 * 
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @return maze symbol in x,y position
	 */
	public String getMazePieceSymbol(int x, int y) {
		return maze.get(y).get(x).getSymbol();
	}

	/**
	 * Gets maze <code>Piece</code> from given coordinates.
	 * 
	 * @param x
	 *            Piece X position
	 * @param y
	 *            Piece Y position
	 * @return <code>Piece</code> from given coordinates
	 */
	public Piece getMazePiece(int x, int y) {
		return maze.get(y).get(x);
	}

	/**
	 * Gets sword <code>Piece</code>.
	 * 
	 * @return sword <code>Piece</code>
	 */
	public Piece getSword() {
		return sword;
	}

	/**
	 * Gets hero object.
	 * 
	 * @return hero from the maze
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Gets eagle object.
	 * 
	 * @return eagle from the maze
	 */
	public Eagle getEagle() {
		return eagle;
	}

	/**
	 * Gets <code>dragons ArrayList</code> with all existing dragons.
	 * 
	 * @return dragons from the maze
	 */
	public ArrayList<Dragon> getDragons() {
		return dragons;
	}

	/**
	 * Sets a new maze.
	 * 
	 * @param maze
	 *            new maze
	 */
	public void setMaze(ArrayList<ArrayList<Piece>> maze) {
		this.maze = maze;
	}

	/**
	 * Sets a new hero.
	 * 
	 * @param hero
	 *            new hero
	 */
	public void setHero(Hero hero) {
		this.hero = hero;
	}

	/**
	 * Sets a new dragons <code>ArrayList</code> container.
	 * 
	 * @param dragons
	 *            new dragons
	 */
	public void setDragons(ArrayList<Dragon> dragons) {
		this.dragons = dragons;
	}
}
