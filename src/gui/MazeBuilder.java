package gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	
	
	public MazeBuilder(final Menu menu, int mazeSize) {
		this.menu = menu;
		this.frame = new JFrame();
		this.mazeSize = mazeSize;
		maze = new ArrayList<ArrayList<Piece>>();
		
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
					Piece piece = new Piece(j, i, PieceType.FREE.asString(), button);
					frame.add(piece);
				} else if(maze.get(j).get(i).getSymbol() == PieceType.WALL.asString()){
					icon = new ImageIcon("src//png//wall_red.png");
					button.setIcon(icon);
					Piece piece = new Piece(j, i, PieceType.FREE.asString(), button);
					frame.add(piece);
				}
				
				button.addMouseListener(new MouseListener() {
					
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
						
					}
				});
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
