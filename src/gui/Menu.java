package gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Dragon;
import logic.Logic;
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
	private Logic logic;
	private int dragonStrategy=0;
	
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
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				final JOptionPane optionPane = new JOptionPane("Settings");
				Object[] DragonStrats = { "Parado", "RandomMove",
						"Random move + sleeping" };
				String initialSelection = "Parado";
				Object selection = JOptionPane.showInputDialog(null,
						"What DragonStrategy do you want?", "Settings",
						JOptionPane.QUESTION_MESSAGE, null, DragonStrats,
						initialSelection);
				//System.out.println(selection);
				if(selection.equals("Parado")) {
					  // dragonStrategy
					dragonStrategy=0;
					logic = new Logic(11, 3, dragonStrategy);
				}
				else if(selection.equals("RandomMove")){
					dragonStrategy=1;
					logic = new Logic(11, 3, dragonStrategy);
				}
				else {
					dragonStrategy=2;
					logic = new Logic(11, 3, dragonStrategy);
				}
				
				JDialog d = new JDialog(menu, "Settings", true);
				d.setContentPane(optionPane);
				d.setSize(new Dimension(400, 300));
				d.setLocationRelativeTo(menu);
			
				//d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//CRASHA quando se clica na X para sair
				closePanel();
				
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
		maze.setVisible(false);
		
		menu.getContentPane().remove(maze);
		menu.pack();
		menu.setVisible(true);
		//menu.setContentPane(menu);
//		menu.removeAll();
//		menu = new JFrame();
//		menu.setLayout(new GridBagLayout());
//		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//showMenuFrame();
	}

	public void startMaze(int boardSize) {
		maze = new MazeUI(boardSize, this);
		menu.getContentPane().add(maze);
		menu.setTitle("Maze Game");
		menu.setResizable(false);
		menu.setContentPane(maze);
		maze.requestFocusInWindow();
	}

	// Acrescentar um botão para configurar opções do jogo, dando acesso a uma
	// janela de diálogo
	// modal (JDialog) onde é possível configurar o tamanho do labirinto, o
	// número de dragões, o
	// modo de movimentação do dragão (pode adormecer ou não) e as teclas de
	// comando a utilizar. À
	// excepção das teclas de comando, estas opções têm efeito na próxima vez
	// que for gerado um
	// // labirinto.

}