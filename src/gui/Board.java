package gui;

import maze.Maze;
import maze.Piece;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	public Timer temporizador;
	// int size = Cli.setMazeSize(); //Preciso do tamanho
	int size = 13;
	private Map m;

	public Board() {
		m = new Map();
		temporizador = new Timer(25, this);
		temporizador.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();

	}

	public void paint(Graphics g) {
		super.paint(g);
		// para testar com o
		// frame.add(new Board());
		// do mazebuilder:
		// g.setColor(Color.red);
		// g.fillRect(45, 60, 32, 32);

		
	//Maze.this.maze.size();
//			for (int i = 0; i < maze.size(); i++) {
//				ArrayList<Piece> linhamaze = maze.get(i);		
//				for (int j = 0; j < maze.get(i).size(); j++)
//					System.out.print(linhamaze.get(j).getSymbol());
//				System.out.println();
//			}

		// para pintar a maze:
		for (int y = 0; y < size; y++) { // linhas e colunas
			for (int x = 0; x < size; x++) {
				if (m.getMap(x, y).equals("X")) {
					g.drawImage(m.getWall(), x * 32, y * 32, null);// 32 pixels
				} else {
					g.drawImage(m.getFloor(), x * 32, y * 32, null);// 32 pixels
				}
			}
		}

	}

}
