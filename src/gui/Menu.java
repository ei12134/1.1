package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import logic.Piece;

public class Menu extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Settings settingsPanel;
	private JButton standardMaze;
	private JButton randomMaze;
	private JButton exit;
	private JButton settings;
	private JButton load;
	private GameUI maze;
	private Dimension dimension;
	private int dragonCounter = 1, dragonStrategy = 1, mazeSize = 13;

	public Menu(JFrame frame) {
		// Menu Buttons
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		exit = new JButton("Exit");
		settings = new JButton("Settings");
		load = new JButton("Load Game");
		dimension = new Dimension(720, 720);
		this.frame = frame;

		addKeyListener(this);

		// Set up menu and settings panels
		setMenuPanel();
		settingsPanel = new Settings(this, dimension);
		settingsPanel.setSettingsPanel();
		showPanel(this);

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

		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * Acho que teremos de criar um novo construtor em MazeUI e
				 * passaremos este arraylist. Só não sei como vamos inferir o
				 * dragonStrategy sem guardar essa informação ao fazer save -
				 * dragonCounter parece facil
				 */
				// logic.Maze m = new logic.Maze();
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

		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				settingsPanel.closeMenuPanel();
				settingsPanel.setSettingsPanel();
				showPanel(settingsPanel);
			}
		});
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {

				Object[] options = { "Yes", "No" };
				int confirm = JOptionPane
						.showOptionDialog(null,
								"Are you sure you want to quit?", "Exit game",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[1]);

				if (confirm == 0)
					System.exit(0);
			}
		});
	}

	public void setMenuPanel() {
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
		add(load, style);
		add(settings, style);
		add(exit, style);
		setVisible(true);
	}

	public void showPanel(JPanel panel) {
		frame.add(panel);
		frame.setSize(dimension);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.requestFocusInWindow();
	}

	public void closeMazeUI() {
		maze.setVisible(false);
		frame.remove(maze);
		showPanel(this);
		frame.setVisible(true);
	}

	public void startMaze(int boardSize, int dragonCounter, int dragonStrategy) {
		maze = new GameUI(boardSize, this, dragonCounter, dragonStrategy,
				dimension);
		frame.remove(this);
		showPanel(maze);
		maze.requestFocusInWindow();
	}

	public JFrame getFrame() {
		return frame;
	}

	public int getDragonStrategy() {
		return dragonStrategy;
	}

	public int getDragonCounter() {
		return dragonCounter;
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

	public int getMazeSize() {
		return mazeSize;
	}

	public Settings getSettingsPanel() {
		return settingsPanel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Object[] options = { "Yes", "No" };
			int confirm = JOptionPane.showOptionDialog(null,
					"Are you sure you want to quit?", "Exit game",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);

			if (confirm == 0)
				System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}