package gui;

import logic.PieceType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener {
	private int boardSize;
	private Map map;

	public Board(int boardSize) {
        this.boardSize = boardSize;
		map = new Map(this.boardSize, true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}


	public void paint(Graphics g) {
		super.paint(g);

		// para pintar a maze
		for (int x = 0; x < boardSize; x++) { // linhas e colunas
			for (int y = 0; y < boardSize; y++) {
				if (map.getMap(x, y).getSymbol() == PieceType.WALL.asString()) {
					g.drawImage(map.getWall(), x * 32, y * 32, null); // 32 pixels
				} else {
					g.drawImage(map.getFloor(), x * 32, y * 32, null); // 32 pixels
				}
			}
		}

	}

}
