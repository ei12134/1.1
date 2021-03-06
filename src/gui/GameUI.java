package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.*;

/**
 * Class <code>GameUI</code> represents the main class of the game It's where
 * all the elements of the game are drawn and where the player can watch the
 * visual effects of the game
 * 
 * @author Andr� Pinheiro
 * @author Jos� Peixoto
 * @author Paulo Faria
 */
public class GameUI extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Logic logic;
	private Menu menu;
	private Dimension dimension;
	private int boardSize, widthPixelsPerTile, heightPixelsPerTile;
	private Image wall, wall_red, wall_brown, wall_green, wall_black, path,
			hero_unarmed, hero_armed, hero_unarmed_eagle, hero_armed_eagle,
			dragon, dragon_guarding, dragon_asleep, dragon_guarding_asleep,
			exit, exit_symmetrical, sword, eagle, eagle_returning_sword;
	private boolean playerCanMove;
	private HashMap<String, Integer> gameKeys;
	private int wallConfig = 0;

	/**
	 * The main constructor of the this class. It loads all the data that is
	 * needed before start playing the game. Based on the desired size of the
	 * board different constructors will be called in the Logic class
	 * 
	 * @param boardSize
	 *            maze size integer
	 * @param menu
	 *            Menu panel class object
	 * @param dragonCounter
	 *            dragon number integer
	 * @param dragonStrategy
	 *            dragon game strategy mode integer
	 * @param dimension
	 *            dimensions of the window
	 */
	public GameUI(int boardSize, Menu menu, int dragonCounter,
			int dragonStrategy, Dimension dimension) {
		this.menu = menu;
		this.boardSize = boardSize;
		this.dimension = dimension;
		gameKeys = menu.getGameKeyboard().getSavedKeys();
		loadData();

		widthPixelsPerTile = dimension.width / boardSize;
		heightPixelsPerTile = dimension.height / boardSize;
		addKeyListener(this);

		if (boardSize == 10)
			logic = new Logic(); // Load the predefined puzzle
		else
			logic = new Logic(boardSize, dragonCounter, dragonStrategy); // Load
																			// a
																			// random
																			// maze
	}

	/**
	 * This is the constructor where the player will be able to play a custom
	 * game The elements of the maze are stored in the maze
	 * <code>ArrayList</code> Note that it doesn't contain the actual images as
	 * it'd be quite CPU overhead. Loading all the images before start playing
	 * it's a much better approach
	 * 
	 * @param menu
	 *            Menu panel class object
	 * @param dragonStrategy
	 *            dragon game strategy mode integer
	 * @param dimension
	 *            dimensions of the window
	 * @param maze
	 *            ArrayList with game pieces
	 * @param play
	 *            Play panel class object
	 */
	public GameUI(Menu menu, int dragonStrategy, Dimension dimension,
			ArrayList<ArrayList<Piece>> maze, Play play) {
		this.boardSize = maze.size();
		this.dimension = dimension;
		this.menu = menu;
		this.gameKeys = menu.getGameKeyboard().getSavedKeys();
		loadData();

		widthPixelsPerTile = dimension.width / boardSize;
		heightPixelsPerTile = dimension.height / boardSize;
		addKeyListener(this);
		logic = new Logic(maze, dragonStrategy);
	}

	// Load all the images so they'll be available during all the program
	// execution
	public void loadData() {
		playerCanMove = true;
		wall = new ImageIcon("src//png//wall_red.png").getImage();
		wall_red = new ImageIcon("src//png//wall_red.png").getImage();
		wall_green = new ImageIcon("src//png//wall_green.png").getImage();
		wall_brown = new ImageIcon("src//png//wall_brown.png").getImage();
		wall_black = new ImageIcon("src//png//wall_black.png").getImage();
		path = new ImageIcon("src//png/path.png").getImage();
		sword = new ImageIcon("src//png/sword.png").getImage();
		exit = new ImageIcon("src//png/exit.png").getImage();
		exit_symmetrical = new ImageIcon("src//png/exit_symmetrical.png")
				.getImage();
		dragon = new ImageIcon("src//png/dragon.png").getImage();
		dragon_asleep = new ImageIcon("src//png/dragon_asleep.png").getImage();
		dragon_guarding = new ImageIcon("src//png/dragon_guarding.png")
				.getImage();
		dragon_guarding_asleep = new ImageIcon(
				"src//png/dragon_guarding_asleep.png").getImage();
		hero_armed_eagle = new ImageIcon("src//png/hero_armed_eagle.png")
				.getImage();
		hero_unarmed_eagle = new ImageIcon("src//png/hero_unarmed_eagle.png")
				.getImage();
		hero_unarmed = new ImageIcon("src//png/hero_unarmed.png").getImage();
		hero_armed = new ImageIcon("src//png/hero_armed.png").getImage();
		eagle = new ImageIcon("src//png/eagle.png").getImage();
		eagle_returning_sword = new ImageIcon(
				"src//png/eagle_returning_sword.png").getImage();
	}

	// This function is called when the player presses a keyboard key
	public void keyPressed(KeyEvent e) {
		String state[] = new String[4];

		// Check if the player can move
		if (playerCanMove) {
			if (e.getKeyCode() == gameKeys.get("up")
					|| e.getKeyCode() == gameKeys.get("up_arrow")) {
				state = logic.playGame("w");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("down")
					|| e.getKeyCode() == gameKeys.get("down_arrow")) {
				state = logic.playGame("s");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("right")
					|| e.getKeyCode() == gameKeys.get("right_arrow")) {
				state = logic.playGame("d");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("left")
					|| e.getKeyCode() == gameKeys.get("left_arrow")) {
				state = logic.playGame("a");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("eagle")
					|| e.getKeyCode() == gameKeys.get("eagle_spacebar")) {
				state = logic.playGame("e");
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
				changeWallColor(true);
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				changeWallColor(false);
				repaint();
			}
		}

		// Exit the game
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Object[] options = { "Yes", "No" };
			int confirm = JOptionPane.showOptionDialog(null,
					"Are you sure you want to abort the game?", "Abort game",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);

			if (confirm == 0)
				menu.closePanel(this, menu, "Maze Game");
		}

		if (state[0] != null)
			analyzeGeneralState(state[0]);

		if (state[3] != null)
			analyzeEagleState(state[3]);
		else if (state[2] != null)
			analyzeEagleState(state[2]);
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		int linePixel = 0;

		g.drawImage(wall, 0, 0, dimension.width, dimension.height, 0, 0,
				dimension.width, dimension.height, null);

		// Draw the maze
		for (int x = 0; x < boardSize; x++, linePixel++) { // Lines
			for (int y = 0; y < boardSize; y++) { // Columns
				if (getPiece(y, x).getSymbol()
						.equals(PieceType.FREE.asString()))
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
				// Hero
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_UNARMED_EAGLE.asString())) {
					drawWithTransparency(g, hero_unarmed_eagle, y, linePixel);
				} // Hero unarmed
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_UNARMED.asString())) {
					drawWithTransparency(g, hero_unarmed, y, linePixel);
				} // Hero armed
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_ARMED.asString())) {
					drawWithTransparency(g, hero_armed, y, linePixel);
				} // Hero armed with eagle
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_ARMED_EAGLE.asString())) {
					drawWithTransparency(g, hero_armed_eagle, y, linePixel);
				} // Dragon
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON.asString())) {
					drawWithTransparency(g, dragon, y, linePixel);
				} // Dragon is sleeping
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON_ASLEEP.asString())) {
					drawWithTransparency(g, dragon_asleep, y, linePixel);
				} // Dragon is guarding
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON_GUARDING.asString())) {
					drawWithTransparency(g, dragon_guarding, y, linePixel);
				} // The dragon is guarding and sleeping
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON_GUARDING_ASLEEP.asString())) {
					drawWithTransparency(g, dragon_guarding_asleep, y,
							linePixel);
				} // Sword
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.SWORD.asString())) {
					drawWithTransparency(g, sword, y, linePixel);
				} // Exit
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.EXIT.asString())
						&& y == 0) {
					drawWithTransparency(g, exit, y, linePixel);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.EXIT.asString())
						&& y != 0) {
					drawWithTransparency(g, exit_symmetrical, y, linePixel);
				} // Eagle
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.PURSUING_EAGLE.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.PURSUING_DRAGON_EAGLE.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.PURSUING_DRAGON_ASLEEP_EAGLE
										.asString())) {
					drawWithTransparency(g, eagle, y, linePixel);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.PURSUING_WALL_EAGLE.asString())) {
					drawSimple(g, eagle, y, linePixel);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.RETURNING_WALL_EAGLE.asString())) {
					drawSimple(g, eagle_returning_sword, y, linePixel);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.GROUND_EAGLE.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.RETURNING_DRAGON_ASLEEP_EAGLE
										.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.RETURNING_DRAGON_EAGLE.asString())) {
					drawWithTransparency(g, eagle_returning_sword, y, linePixel);
				}
			}
		}
	}

	public void drawSimple(Graphics g, Image image, int y, int linePixel) {
		g.drawImage(image, widthPixelsPerTile * y, heightPixelsPerTile
				* linePixel, widthPixelsPerTile * y + widthPixelsPerTile,
				heightPixelsPerTile * linePixel + heightPixelsPerTile, 0, 0,
				720, 720, null);
	}

	public void drawWithTransparency(Graphics g, Image image, int y,
			int linePixel) {
		g.drawImage(path, widthPixelsPerTile * y, heightPixelsPerTile
				* linePixel, widthPixelsPerTile * y + widthPixelsPerTile,
				heightPixelsPerTile * linePixel + heightPixelsPerTile,
				widthPixelsPerTile * y, heightPixelsPerTile * linePixel,
				widthPixelsPerTile * y + widthPixelsPerTile,
				heightPixelsPerTile * linePixel + heightPixelsPerTile, null);
		g.drawImage(image, widthPixelsPerTile * y, heightPixelsPerTile
				* linePixel, widthPixelsPerTile * y + widthPixelsPerTile,
				heightPixelsPerTile * linePixel + heightPixelsPerTile, 0, 7,
				720, 720, null);
	}

	// Returns a Piece object of the maze
	public Piece getPiece(int x, int y) {
		return logic.getMaze().get(y).get(x);
	}

	public void changeWallColor(boolean next) {
		if (next) {
			if (wallConfig >= 3)
				wallConfig = 0;
			else
				wallConfig++;
		} else {
			if (wallConfig <= 0)
				wallConfig = 3;
			else
				wallConfig--;
		}
		switch (wallConfig) {
		case 0:
			wall = wall_red;
			break;
		case 1:
			wall = wall_green;
			break;
		case 2:
			wall = wall_brown;
			break;
		case 3:
			wall = wall_black;
			break;
		default:
			break;
		}
	}

	// Shows a dialog based on the game state
	public void analyzeGeneralState(String state) {
		if (state.equals(State.HERO_DEAD.toString())) {
			JOptionPane.showMessageDialog(null, "Hero died!");
			playerCanMove = false;
			menu.closePanel(this, menu, "Maze Game");
		} else if (state.equals(State.HERO_WON.toString())) {
			JOptionPane.showMessageDialog(null, "Hero won!");
			playerCanMove = false;
			menu.closePanel(this, menu, "Maze Game");
		}
	}

	public void analyzeEagleState(String state) {
		if (state
				.equals("Dragon killed the eagle!\n * Dragon is now guarding the sword")) {
			JOptionPane
					.showMessageDialog(null,
							"Dragon killed the eagle!\n * Dragon is now guarding the sword");
		}
	}

	public void analyzeDragonState(String state) {
		if (state
				.equals("Dragon killed the eagle!\n * Dragon is now guarding the sword")) {
			JOptionPane
					.showMessageDialog(null,
							"Dragon killed the eagle!\nDragon is now guarding the sword");
		}
	}
}
