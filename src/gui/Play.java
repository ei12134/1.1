package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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

		// Button actions
		standardMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze(10, 1, 1);
			}
		});

		randomMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze(mazeSize, dragonCounter, dragonStrategy);
			}
		});

		customMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMazeBuilder(mazeSize, dragonCounter, dragonStrategy);
			}
		});

		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				menu.closePanel(menu.getPlayPanel(), menu);
			}
		});
	}

	public Play(final Menu menu, Dimension dimension, boolean random) {
		this.menu = menu;
		this.dimension = dimension;
	}

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
				dimension, menu.getKeyboardKeys());
		menu.closePanel(this, maze);
	}

	public void startMazeBuilder(int boardSize, int dragonCounter,
			int dragonStrategy) {
		mazeBuilder = new MazeBuilder(menu, boardSize, dimension);
		menu.closePanel(this, mazeBuilder);
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
