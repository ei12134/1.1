package gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

import java.util.ArrayList;

import logic.Piece;
import logic.PieceType;

public class MazeBuilder extends JPanel {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Menu menu;
	private int mazeSize;
	private ArrayList<ArrayList<Piece>> maze;

	public MazeBuilder(final Menu menu, int mazeSize) {
		this.menu = menu;
		this.frame = menu.getFrame();
		this.mazeSize = mazeSize;
		maze = new ArrayList<ArrayList<Piece>>();

		setLayout(new GridLayout(mazeSize, mazeSize));
		populateGameButtons();
	}

	public void populateGameButtons() {
		// The maze size is MxM, where M is an odd number
		for (int i = 0; i < mazeSize; i++) {
			ArrayList<Piece> row = new ArrayList<Piece>();
			for (int j = 0; j < mazeSize; j++)
				row.add(new Piece(j, i, PieceType.FREE.asString()));
			maze.add(row);
		}

		for (int i = 0; i < mazeSize; i++) {
			maze.get(0).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(mazeSize - 1).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(0).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(mazeSize - 1).setSymbol(PieceType.WALL.asString());
		}
	}

	public void startMazeBuilder() {
		for (int i = 0; i < maze.size(); i++) {
			for (int j = 0; j < maze.get(i).size(); j++) {
				JButton button = new JButton();
				Piece piece = new Piece(0, 0, "");
				ImageIcon icon;
				if (maze.get(j).get(i).getSymbol() == PieceType.FREE.asString()) {
					icon = new ImageIcon("src//png//wall_green.png");
					button.setIcon(icon);
					piece = new Piece(j, i, PieceType.FREE.asString());
					add(piece);
				} else if (maze.get(j).get(i).getSymbol() == PieceType.WALL
						.asString()) {
					icon = new ImageIcon("src//png//wall_red.png");
					button.setIcon(icon);
					piece = new Piece(j, i, PieceType.FREE.asString());
					add(piece);
				}

				piece.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseClicked(MouseEvent arg0) {
						Piece p = (Piece) arg0.getSource();
						
						showPopup(p);
					}
				});
			}
		}
	}
	
	
	public void showPopup(Piece p) {
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem item1 = new JMenuItem("Option1");
		popup.add(item1);
		//JOptionPane.showMessageDialog(null, "Tamanho da janela: " + getWidth() + ", PosX: " + p.getPosX() + ", Final: " + (getWidth() / 9) * p.getPosX());
		
		popup.show(p, 0, 0);
	}

	public void closeMenuPanel() {
		frame.remove(menu);
	}
}
