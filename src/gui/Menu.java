package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Piece;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel menuPanel;
	private Settings settingsPanel;
	private JButton standardMaze;
	private JButton randomMaze;
	private JButton exit;
	private JButton settings;
	private JButton load;
	private MazeUI maze;
	private Dimension dimension;
	private int dragonCounter = 1, dragonStrategy = 1, mazeSize = 13;

	public Menu() {
		// Menu Buttons
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		exit = new JButton("Exit");
		settings = new JButton("Settings");
		load = new JButton("Load Game");
		dimension = new Dimension(768, 768);
		menuPanel = new JPanel();
		this.setTitle("Maze Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up menu and settings panels
		setMenuPanel();
		settingsPanel = new Settings(this, dimension);
		settingsPanel.setSettingsPanel();
		showPanel(menuPanel);

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
				System.exit(0);
			}
		});
	}

	public void setMenuPanel() {
		menuPanel.setSize(dimension);
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints style = new GridBagConstraints();
		style.fill = GridBagConstraints.BOTH;
		style.weightx = 0.5;
		style.weighty = 0.5;
		style.gridheight = 3;
		style.gridwidth = 3;
		style.gridx = 0;
		style.insets = new Insets(32, 256, 32, 256);
		menuPanel.add(standardMaze, style);
		menuPanel.add(randomMaze, style);
		menuPanel.add(load, style);
		menuPanel.add(settings, style);
		menuPanel.add(exit, style);
		menuPanel.setVisible(true);
	}

	public void showPanel(JPanel panel) {
		this.add(panel);
		this.setSize(dimension);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void closeMazeUI() {
		maze.setVisible(false);
		this.remove(maze);
		showPanel(menuPanel);
		this.setVisible(true);
	}

	public void startMaze(int boardSize, int dragonCounter, int dragonStrategy) {
		maze = new MazeUI(boardSize, this, dragonCounter, dragonStrategy);
		this.remove(menuPanel);
		showPanel(maze);
		maze.requestFocusInWindow();
	}

	public JPanel getMenuPanel() {
		return menuPanel;
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
	/*
	 * Acrescentar um botão para configurar opções do jogo, dando acesso a uma
	 * janela de diálogo modal (JDialog) onde é possível configurar o tamanho do
	 * labirinto, o número de dragões, o modo de movimentação do dragão (pode
	 * adormecer ou não) e as teclas de comando a utilizar. À excepção das
	 * teclas de comando, estas opções têm efeito na próxima vez que for gerado
	 * um labirinto.
	 */
}