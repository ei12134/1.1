package gui;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

	private JFrame menu;
	private JButton standardMaze;
	private JButton randomMaze;
	private JButton exit;
	private Maze maze;

	public Menu() {
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		exit = new JButton("Exit");

		menu = new JFrame();
		menu.setSize(1024, 768);

		menu.setLayout(new FlowLayout());
		menu.add(standardMaze);
		menu.add(randomMaze);
		menu.add(exit);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(new Dimension(1024, 768));
		menu.setVisible(true);

		// Button action when clicked
		standardMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze(10);
			}
		});

		randomMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze(21);
			}
		});

		// Called when the Exit button is clicked
		// exitButton.setLocation(30, 30);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});
	}

	public void closePanel() {
		maze.setVisible(false);
		menu.remove(maze);
		menu.setVisible(true);
		menu.requestFocusInWindow();
	}

	public void startMaze(int boardSize) {
		maze = new Maze(boardSize, this);
		menu.add(maze);
		menu.setTitle("Maze Game");
		menu.setResizable(false);
		menu.setContentPane(maze);
		maze.requestFocusInWindow();
	}
}
