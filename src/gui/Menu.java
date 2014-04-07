package gui;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Maze;
import logic.Piece;

public class Menu {

	private JFrame menu;
	private JButton standardMaze;
	private JButton randomMaze;
	private JButton exit;
	private JButton settings;
	private JButton loadGame;
	private MazeUI maze;
	private Dimension dimension;

	public Menu() {
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		exit = new JButton("Exit");
		settings = new JButton("Settings");
		loadGame = new JButton("Load Game");

		dimension = new Dimension(768, 768);

		menu = new JFrame();

		menu.setLayout(new GridBagLayout());
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		showMenuFrame();

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
		
		
		loadGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logic.Maze m = new logic.Maze();
				GameIO io = new GameIO();
				ArrayList<ArrayList<Piece>> tmp = io.readFile("puzzle.lpoo");
				for (int i = 0; i < tmp.size(); i++) {
					ArrayList<Piece> linhamaze = tmp.get(i);
					for (int j = 0; j < tmp.get(i).size(); j++)
						System.out.print(linhamaze.get(j).getSymbol());
					System.out.println();
				}
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

	public void setMenuFrame(Container pane) {
		GridBagConstraints style = new GridBagConstraints();
		style.fill = GridBagConstraints.BOTH;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridheight = 3;
		style.gridwidth = 3;
		style.gridx = 0;
		style.insets = new Insets(32, 256, 32, 256);
		menu.add(standardMaze, style);
		menu.add(randomMaze, style);
		menu.add(loadGame, style);
		menu.add(settings, style);
		menu.add(exit, style);
	}

	private void showMenuFrame() {
		// Set up the content pane.
		setMenuFrame(menu.getContentPane());
		// Display the window.
		menu.pack();
		menu.setSize(dimension);
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);
	}

	public void closePanel() {
		menu.setVisible(false);
		menu.removeAll();
		menu = new JFrame();
		menu.setLayout(new GridBagLayout());
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showMenuFrame();
	}

	public void startMaze(int boardSize) {
		maze = new MazeUI(boardSize, this);
		menu.add(maze);
		menu.setTitle("Maze Game");
		menu.setResizable(false);
		menu.setContentPane(maze);
		maze.requestFocusInWindow();
	}
}