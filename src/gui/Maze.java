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
	private ArrayList<ArrayList<Piece>> maze;
	private int boardSize;
	private Image wall;
	private Image path;
	private Scanner scanner;

	public Maze(int boardSize) {
		wall = new ImageIcon("src//png//wall.png").getImage();
		path = new ImageIcon("src//png/path.png").getImage();

		this.boardSize = boardSize;
		if (boardSize == 10) {
			Logic logic = new Logic();
			this.maze = logic.getMaze();
		} else {
			Logic maze = new Logic(boardSize, 3, 2);
			this.maze = maze.getMaze();
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
				if (getPiece(x, y).getSymbol() == PieceType.WALL.asString()) {
					g.drawImage(getWall(), x * 32, y * 32, null); // 32 pixels
				} else {
					g.drawImage(getFloor(), x * 32, y * 32, null); // 32 pixels
				}
			}
		}

	}

	public Image getWall() {
		return wall;
	}

	public Image getFloor() {
		return path;
	}

	public Piece getPiece(int x, int y) {
		return maze.get(y).get(x);
	}

	public void openFile() {
		try {
			scanner = new Scanner(new File("src//png//Map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readFile() {
		HashMap<java.lang.Character, String> pieces = new HashMap<java.lang.Character, String>();
		pieces.put('X', PieceType.WALL.asString());
		pieces.put('F', PieceType.FREE.asString());

		int i = 0;
		while (scanner.hasNext()) {
			ArrayList<Piece> row = new ArrayList<Piece>();
			String s = scanner.next();
			for (int j = 0; j < s.length(); j++)
				row.add(new Piece(i, j, pieces.get(s.charAt(j))));
			maze.add(row);
			i++;
		}
		scanner.close();
	}

	public void closeFile() {

	}
}
