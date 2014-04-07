package gui;

import logic.Logic;
import logic.Piece;
import logic.PieceType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Logic logic;
	private Menu menu;
	private int boardSize, pixelsPerTile;
	private Image wall, path, sword, hero, hero_armed, dragon, exit;
	private Scanner scanner;

	public Maze(int boardSize, JFrame menu) {
		wall = new ImageIcon("src//png//wall.png").getImage();
		path = new ImageIcon("src//png/path.png").getImage();
		sword = new ImageIcon("src//png/sword.png").getImage();
		exit = new ImageIcon("src//png/exit.png").getImage();
		dragon = new ImageIcon("src//png/dragon.png").getImage();
		hero = new ImageIcon("src//png/hero.png").getImage();
		hero_armed = new ImageIcon("src//png/hero_armed.png").getImage();
		this.boardSize = boardSize;
		pixelsPerTile = 768 / boardSize;

		addKeyListener(this);

		if (boardSize == 10)
			logic = new Logic();
		else
			logic = new Logic(boardSize, 3, 2);
	}

	public void keyPressed(KeyEvent e) {
		// Invoked when a key has been pressed.
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			logic.playGame("w");
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			logic.playGame("s");
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			logic.playGame("a");
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			logic.playGame("d");
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

		}
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
					g.drawImage(hero, pixelsPerTile * y, pixelsPerTile
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

	public void openFile() {
		try {
			scanner = new Scanner(new File("src//png//Map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void readFile() {
	// HashMap<java.lang.Character, String> pieces = new
	// HashMap<java.lang.Character, String>();
	// pieces.put('X', PieceType.WALL.asString());
	// pieces.put('F', PieceType.FREE.asString());
	//
	// int i = 0;
	// while (scanner.hasNext()) {
	// ArrayList<Piece> row = new ArrayList<Piece>();
	// String s = scanner.next();
	// for (int j = 0; j < s.length(); j++)
	// row.add(new Piece(i, j, pieces.get(s.charAt(j))));
	// maze.add(row);
	// i++;
	// }
	// scanner.close();
	// }

	public void closeFile() {

	}
}
