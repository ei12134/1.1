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

public class MazeUI extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Logic logic;
	private Menu menu;
	private int boardSize, pixelsPerTile;
	private Image wall, path, hero, hero_armed, dragon, exit, sword;
	private boolean playerCanMove;
	private HashMap<String, Integer> gameKeys;
	private GameKeyboard keyboardKeys;

	public MazeUI(int boardSize, Menu menu, int dragonCounter, int dragonStrategy) {
		playerCanMove = true;
		keyboardKeys = new GameKeyboard();
		keyboardKeys.initializeKeys();
		keyboardKeys.readKeys();
		gameKeys = new HashMap<String, Integer>();
		gameKeys = keyboardKeys.getKeys();

		wall = new ImageIcon("src//png//wall.png").getImage();
		path = new ImageIcon("src//png/path.png").getImage();
		sword = new ImageIcon("src//png/Gold-Sword-icon.png").getImage();
		exit = new ImageIcon("src//png/exit.png").getImage();
		dragon = new ImageIcon("src//png/dragon.png").getImage();
		hero = new ImageIcon("src//png/hero.png").getImage();
		hero_armed = new ImageIcon("src//png/hero_armed.png").getImage();
		this.boardSize = boardSize;
		pixelsPerTile = 768 / boardSize;

		this.menu = menu;
		addKeyListener(this);

		if (boardSize == 10)
			logic = new Logic();
		else
			logic = new Logic(boardSize, dragonCounter, dragonStrategy);
	}

	public void keyPressed(KeyEvent e) {
		String state[] = new String[4];
		// Invoked when a key has been pressed.
		if (playerCanMove) {
			if (e.getKeyCode() == gameKeys.get("up")) {
				state = logic.playGame("w");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("down")) {
				state = logic.playGame("s");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("right")) {
				state = logic.playGame("d");
				repaint();
			} else if (e.getKeyCode() == gameKeys.get("left")) {
				state = logic.playGame("a");
				repaint();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			menu.closeMazeUI();

		if (state[0] != null)
			analyzeState(state[0]);
	}

	public void keyReleased(KeyEvent e) {
		// Invoked when a key has been released.
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		int linePixel = 0;

		// Draw maze
		for (int x = 0; x < boardSize; x++, linePixel++) { // lines
			for (int y = 0; y < boardSize; y++) { // columns
				if (getPiece(y, x).getSymbol()
						.equals(PieceType.WALL.asString()))
					g.drawImage(wall, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.EXIT.asString()))
					g.drawImage(exit, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);
				else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO.asString()))
					g.drawImage(hero, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);

				else if (getPiece(y, x).getSymbol().equals(
						PieceType.HERO_ARMED.asString()))
					g.drawImage(hero_armed, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);

				else if (getPiece(y, x).getSymbol().equals(
						PieceType.SWORD.asString()))
					g.drawImage(sword, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);

				else if (getPiece(y, x).getSymbol().equals(
						PieceType.DRAGON.asString()))
					g.drawImage(dragon, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);
				else
					g.drawImage(path, pixelsPerTile * y, pixelsPerTile
							* linePixel, pixelsPerTile * y + pixelsPerTile,
							pixelsPerTile * linePixel + pixelsPerTile, 0, 0,
							512, 512, null);
			}
		}

	}

	public Piece getPiece(int x, int y) {
		return logic.getMaze().get(y).get(x);
	}

	public void analyzeState(String state) {
		if (state.equals(State.HERO_DEAD.toString())) {
			JOptionPane.showMessageDialog(null, "O herói morreu!");
			playerCanMove = false;
		}
	}
}
