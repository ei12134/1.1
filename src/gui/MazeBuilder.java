package gui;

import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.util.ArrayList;

import logic.Piece;
import logic.PieceType;


public class MazeBuilder extends JPanel {

	private JFrame frame;
	private Menu menu;
	private int mazeSize;
	private ArrayList<ArrayList<Piece>> maze;
	private ArrayList<ArrayList<JButton>> buttons;
	
	
	public MazeBuilder(final Menu menu, int mazeSize) {
		this.menu = menu;
		this.frame = new JFrame();
		this.mazeSize = mazeSize;
		maze = new ArrayList<ArrayList<Piece>>();
		buttons = new ArrayList<ArrayList<JButton>>();
		
		this.frame.setLayout(new GridLayout(mazeSize, mazeSize));
		populateGameButtons();
	}
	
	
	public void populateGameButtons() {
		//The maze size is MxM, where M is an odd number
		for(int i = 0; i < mazeSize; i++) {
			ArrayList<Piece> row = new ArrayList<Piece>();
			for(int j = 0; j < mazeSize; j++)
				row.add(new Piece(j, i, PieceType.FREE.asString()));
			maze.add(row);
		}
		
		for(int i = 0; i < mazeSize; i++) {
			maze.get(0).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(mazeSize - 1).get(i).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(0).setSymbol(PieceType.WALL.asString());
			maze.get(i).get(mazeSize - 1).setSymbol(PieceType.WALL.asString());
		}
	}
	
	
	public void startMazeBuilder() {
		for(int i = 0; i < maze.size(); i++) {
			for(int j = 0; j < maze.get(i).size(); j++) {
				JButton button = new JButton();
				ImageIcon icon;
				if(maze.get(j).get(i).getSymbol() == PieceType.FREE.asString()) {
					icon = new ImageIcon("src//png//wall_green.png");
					button.setIcon(icon);
					frame.add(button);
				} else {
					icon = new ImageIcon("src//png//wall_red.png");
					button.setIcon(icon);
					frame.add(button);
				}
			}
		}
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void closeMenuPanel() {
		this.setVisible(false);
		menu.remove(this);
		menu.showPanel(menu);
		menu.setVisible(true);
	}
}
