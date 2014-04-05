package gui;

import logic.Logic;
import logic.Piece;
import logic.PieceType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Maze extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Logic logic;
	private int boardSize;
	private Image wall, path, hero;
	private Scanner scanner;

	public Maze(int boardSize) {
		wall = new ImageIcon("src//png//wall.png").getImage();
		path = new ImageIcon("src//png/path.png").getImage();
		hero = new ImageIcon("src//png/hero.png").getImage();

		this.boardSize = boardSize;
		if (boardSize == 10) {
			logic = new Logic();
		} else {
			logic = new Logic(boardSize, 3, 2);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		// Draw maze
		for (int x = 0; x < boardSize; x++) { // lines
			for (int y = 0; y < boardSize; y++) { // columns
				if (getPiece(x, y).getSymbol().equals(PieceType.WALL.asString())) {
					g.drawImage(wall, x * 32, y * 32, null); // 32 pixels
				} else if (getPiece(x, y).getSymbol().equals(logic.getHero()
						.showHero())) {
					g.drawImage(hero, x * 32, y * 32, null); // 32 pixels
				} else {
					g.drawImage(path, x * 32, y * 32, null); // 32 pixels
				}
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

//	public void readFile() {
//		HashMap<java.lang.Character, String> pieces = new HashMap<java.lang.Character, String>();
//		pieces.put('X', PieceType.WALL.asString());
//		pieces.put('F', PieceType.FREE.asString());
//
//		int i = 0;
//		while (scanner.hasNext()) {
//			ArrayList<Piece> row = new ArrayList<Piece>();
//			String s = scanner.next();
//			for (int j = 0; j < s.length(); j++)
//				row.add(new Piece(i, j, pieces.get(s.charAt(j))));
//			maze.add(row);
//			i++;
//		}
//		scanner.close();
//	}

	public void closeFile() {

	}
}
