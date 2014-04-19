package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Play extends JPanel {
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private JFrame frame;
	private JButton standardMaze, randomMaze, customMaze, close;
	private GameUI maze;
	private Dimension dimension;
	private MazeBuilder mazeBuilder;
	private int dragonCounter, dragonStrategy, mazeSize;

	public Play(final Menu menu, Dimension dimension) {
		this.menu = menu;
		this.dimension = dimension;
		this.frame = menu.getFrame();
		this.dragonCounter = 1;
		this.dragonStrategy = 1;
		this.mazeSize = 13;
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		customMaze = new JButton("Customize maze");
		close = new JButton("Return to menu");

		// Button actions
		standardMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				closePlayPanel();
				startMaze(10, 1, 1);
			}
		});

		randomMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				closePlayPanel();
				startMaze(mazeSize, dragonCounter, dragonStrategy);
			}
		});

		customMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				closePlayPanel();
				startMazeBuilder(mazeSize, dragonCounter, dragonStrategy);
			}
		});

		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				closePlayPanel();
			}
		});
	}

	public Play(final Menu menu, Dimension dimension, boolean random) {
		this.menu = menu;
		this.dimension = dimension;
		this.frame = menu.getFrame();
		closePlayPanel();
	}

	public void setPlayPanel() {
		this.setSize(dimension);
		this.setLayout(new GridBagLayout());
		GridBagConstraints style = new GridBagConstraints();
		style.fill = GridBagConstraints.BOTH;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridheight = 3;
		style.gridwidth = 3;
		style.gridx = 1;
		style.insets = new Insets(32, 256, 32, 256);
		this.add(standardMaze, style);
		this.add(randomMaze, style);
		this.add(customMaze, style);
		this.add(close, style);
		this.setVisible(true);
	}

	public void startMaze(int boardSize, int dragonCounter, int dragonStrategy) {
		maze = new GameUI(boardSize, this, dragonCounter, dragonStrategy,
				dimension);
		frame.remove(menu);
		menu.showPanel(maze);
		maze.requestFocusInWindow();
	}

	public void startMazeBuilder(int boardSize, int dragonCounter,
			int dragonStrategy) {
		mazeBuilder = new MazeBuilder(this, boardSize, dragonCounter,
				dragonStrategy, dimension);
		frame.remove(menu);
		menu.showPanel(mazeBuilder);
		mazeBuilder.requestFocusInWindow();
		mazeBuilder.startMazeBuilder();
	}

	public void closePlayPanel() {
		this.setVisible(false);
		menu.remove(this);
		menu.showPanel(menu);
		menu.setVisible(true);
	}

	public void closeMazeUI() {
		maze.setVisible(false);
		frame.remove(maze);
		menu.showPanel(menu);
		frame.setVisible(true);
	}

	public void closeMazeBuilder() {
		mazeBuilder.setVisible(false);
		frame.remove(mazeBuilder);
		menu.showPanel(menu);
		frame.setVisible(true);
	}

	public void closeMenuPanel() {
		frame.remove(menu);
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
