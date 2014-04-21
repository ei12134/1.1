package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This <code>Play</code> represents the panel where the player is able to
 * select which game mode he/she wants to play
 * 
 * @author André Pinheiro
 * @author José Peixoto
 * @author Paulo Faria
 */
public class Play extends JPanel {
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private JButton standardMaze, randomMaze, customMaze, close;
	private GameUI maze;
	private Dimension dimension;
	private MazeBuilder mazeBuilder;
	private int dragonCounter, dragonStrategy, mazeSize;

	public Play(final Menu menu) {
		this.menu = menu;
		this.dimension = menu.getDimension();
		this.dragonCounter = 1;
		this.dragonStrategy = 1;
		this.mazeSize = 13;
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		customMaze = new JButton("Customize maze");
		close = new JButton("Return to menu");
		setPlayPanel();

		// Create a standard maze(the maze size/dragon counter/dragon strategy
		// is already set)
		standardMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze(10, 1, 1);
			}
		});

		// Create a random maze(the player is able to customize the main
		// settings of the game)
		randomMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze(mazeSize, dragonCounter, dragonStrategy);
			}
		});

		// Asks the player to choose a random maze and initializes the puzzle
		customMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMazeBuilder(mazeSize, dragonCounter, dragonStrategy);
			}
		});

		// Closes the play panel
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				menu.closePanel(menu.getPlayPanel(), menu, "Maze Game");
			}
		});
	}

	public Play(final Menu menu, Dimension dimension, boolean random) {
		this.menu = menu;
		this.dimension = dimension;
	}

	// Set the JButton style and add it to the JPanel
	public void setPlayPanel() {
		setSize(dimension);
		setLayout(new GridBagLayout());
		GridBagConstraints style = new GridBagConstraints();
		style.fill = GridBagConstraints.BOTH;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridheight = 3;
		style.gridwidth = 3;
		style.gridx = 1;
		style.insets = new Insets(32, 256, 32, 256);
		add(standardMaze, style);
		add(randomMaze, style);
		add(customMaze, style);
		add(close, style);
		setVisible(true);
	}

	public void startMaze(int boardSize, int dragonCounter, int dragonStrategy) {
		maze = new GameUI(boardSize, menu, dragonCounter, dragonStrategy,
				dimension);
		menu.closePanel(this, maze, "Maze Game");
	}

	public void startMazeBuilder(int boardSize, int dragonCounter,
			int dragonStrategy) {
		mazeBuilder = new MazeBuilder(menu, boardSize, dimension);
		menu.closePanel(this, mazeBuilder, "Maze Builder");
		mazeBuilder.startMazeBuilder();
	}

	public int getDragonStrategy() {
		return dragonStrategy;
	}

	public int getDragonCounter() {
		return dragonCounter;
	}

	public int getMazeSize() {
		return mazeSize;
	}

	public void setDragonCounter(int dragonCounter) {
		this.dragonCounter = dragonCounter;
	}

	public void setDragonStrategy(int dragonStrategy) {
		this.dragonStrategy = dragonStrategy;
	}

	public void setMazeSize(int mazeSize) {
		this.mazeSize = mazeSize;
	}
}
