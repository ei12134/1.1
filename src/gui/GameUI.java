package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.*;

public class GameUI extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Logic logic;
	private Play play;
	private Dimension dimension;
	private int boardSize, widthPixelsPerTile, heightPixelsPerTile;
	private Image wall, wall_red, wall_brown, wall_green, wall_black, path,
			hero_unarmed, hero_armed, hero_unarmed_eagle, hero_armed_eagle,
			dragon, dragon_guarding, dragon_asleep, dragon_guarding_asleep,
			exit, exit_symmetrical, sword, eagle, eagle_returning_sword;
	private boolean playerCanMove;
	private HashMap<String, Integer> gameKeys;
	private GameKeyboard keyboardKeys;
	private int wallConfig = 0;

	public GameUI(int boardSize, Play play, int dragonCounter,
			int dragonStrategy, Dimension dimension) {
		this.play = play;
		this.boardSize = boardSize;
		this.dimension = dimension;
		loadData();
		widthPixelsPerTile = dimension.width / boardSize;
		heightPixelsPerTile = dimension.height / boardSize;
		addKeyListener(this);

		if (boardSize == 10)
			logic = new Logic();
		else
			logic = new Logic(boardSize, dragonCounter, dragonStrategy);
	}
	
	
	public void loadData() {
		playerCanMove = true;
		keyboardKeys = new GameKeyboard();
		keyboardKeys.initializeKeys();
		keyboardKeys.readKeys();
		gameKeys = new HashMap<String, Integer>();
		gameKeys = keyboardKeys.getKeys();
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
	

	public void keyPressed(KeyEvent e) {
		String state[] = new String[4];
		// Invoked when a key has been pressed.
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Object[] options = { "Yes", "No" };
			int confirm = JOptionPane.showOptionDialog(null,
					"Are you sure you want to abort the game?", "Abort game",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);

			if (confirm == 0)
				play.closeMazeUI();
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		int linePixel = 0;

		g.drawImage(wall, 0, 0, dimension.width, dimension.height, 0, 0,
				dimension.width, dimension.height, null);

		// Draw maze
		for (int x = 0; x < boardSize; x++, linePixel++) { // lines
			for (int y = 0; y < boardSize; y++) { // columns
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
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(hero_unarmed_eagle, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_UNARMED.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(hero_unarmed, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_ARMED.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(hero_armed, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_ARMED_EAGLE.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(hero_armed_eagle, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				}
				// Dragon
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(dragon, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON_ASLEEP.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(dragon_asleep, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON_GUARDING.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(dragon_guarding, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON_GUARDING_ASLEEP.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(dragon_guarding_asleep, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.SWORD.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(sword, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				}
				// Exit
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.EXIT.asString())
						&& y == 0) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(exit, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.EXIT.asString())
						&& y != 0) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(exit_symmetrical, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				}
				// Eagle
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.PURSUING_EAGLE.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.PURSUING_DRAGON_EAGLE.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.PURSUING_DRAGON_ASLEEP_EAGLE
										.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(eagle, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.PURSUING_WALL_EAGLE.asString())) {
					g.drawImage(eagle, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.RETURNING_WALL_EAGLE.asString())) {
					g.drawImage(eagle_returning_sword, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				} else if (getPiece(y, x).getSymbol().equals(
						PieceType.GROUND_EAGLE.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.RETURNING_DRAGON_ASLEEP_EAGLE
										.asString())
						|| getPiece(y, x).getSymbol().equals(
								PieceType.RETURNING_DRAGON_EAGLE.asString())) {
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
					g.drawImage(eagle_returning_sword, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, 0, 0, 720, 720, null);
				}

				else if (!getPiece(y, x).getSymbol().equals(
						PieceType.WALL.asString()))
					g.drawImage(path, widthPixelsPerTile * y,
							heightPixelsPerTile * linePixel, widthPixelsPerTile
									* y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, widthPixelsPerTile
									* y, heightPixelsPerTile * linePixel,
							widthPixelsPerTile * y + widthPixelsPerTile,
							heightPixelsPerTile * linePixel
									+ heightPixelsPerTile, null);
			}
		}
	}

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

	public void analyzeGeneralState(String state) {
		if (state.equals(State.HERO_DEAD.toString())) {
			JOptionPane.showMessageDialog(null, "Hero died!");
			playerCanMove = false;
			play.closeMazeUI();
		} else if (state.equals(State.HERO_WON.toString())) {
			JOptionPane.showMessageDialog(null, "Hero won!");
			playerCanMove = false;
			play.closeMazeUI();
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
