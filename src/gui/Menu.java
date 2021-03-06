package gui;

import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import logic.Piece;

public class Menu extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Settings settingsPanel;
	private Play playPanel;
	private JButton play, exit, settings, load;
	private Dimension dimension;
	private GameKeyboard keyboard;

	public Menu(JFrame frame) {
		play = new JButton("Play Game");
		exit = new JButton("Exit");
		settings = new JButton("Settings");
		load = new JButton("Load Game");
		dimension = new Dimension(720, 720);
		this.frame = frame;
		addKeyListener(this);

		// Keyboard Keys
		keyboard = new GameKeyboard();
		keyboard.initializeKeys();

		// Set up menu and settings panels
		setMenuPanel();
		showPanel(this, "Maze Game");
		playPanel = new Play(this);
		settingsPanel = new Settings(this);

		// Button actions
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startPanel(playPanel, "Game mode");
			}
		});

		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startPanel(settingsPanel, "Game Settings");
			}
		});

		exit.addActionListener(new ActionListener() {

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

		load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
						"Maze files...", "maze"));
				int i = fileChooser.showDialog(Menu.this,
						"Select saved maze file");

				if (i == JFileChooser.APPROVE_OPTION) {
					File puzzle = fileChooser.getSelectedFile();
					startLoadedPuzzle(puzzle);
				}
			}
		});
	}

	public void startLoadedPuzzle(File savedMaze) {
		if (savedMaze.exists()) {
			ArrayList<ArrayList<Piece>> maze = new ArrayList<ArrayList<Piece>>();
			maze = getPuzzleFile(savedMaze);
			GameUI game = new GameUI(this, 1, dimension, maze, playPanel);
			frame.remove(this);
			this.showPanel(game, "Loaded maze game");
			game.requestFocusInWindow();
		} else {
			JOptionPane.showMessageDialog(null,
					"The file" + savedMaze.getName() + " doesn't exist!");
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ArrayList<Piece>> getPuzzleFile(File file) {
		ArrayList<ArrayList<Piece>> maze = new ArrayList<ArrayList<Piece>>();
		FileInputStream inStream = null;
		ObjectInputStream objInStream = null;

		try {
			inStream = new FileInputStream(file);
			objInStream = new ObjectInputStream(inStream);
			Object o = objInStream.readObject();

			if (o instanceof ArrayList)
				maze = (ArrayList<ArrayList<Piece>>) o;
			else {
				objInStream.close();
				return null;
			}
			objInStream.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Error reading from saved maze file");
			e.printStackTrace();
		}
		return maze;
	}

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

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public Play getPlayPanel() {
		return playPanel;
	}

	public Settings getSettingsPanel() {
		return settingsPanel;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public GameKeyboard getGameKeyboard() {
		keyboard.initializeKeys();
		return keyboard;
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
		add(play, style);
		add(load, style);
		add(settings, style);
		add(exit, style);
		setVisible(true);
	}

	public void showPanel(JPanel panel, String title) {
		frame.add(panel);
		frame.setSize(dimension);
		frame.setLocationRelativeTo(null);
		frame.setTitle(title);
		frame.setVisible(true);
		panel.setVisible(true);
		panel.requestFocusInWindow();
	}

	public void closePanel(JPanel oldPanel, JPanel newPanel, String title) {
		oldPanel.setVisible(false);
		frame.remove(oldPanel);
		showPanel(newPanel, title);
	}

	public void startPanel(JPanel panel, String title) {
		frame.remove(this);
		showPanel(panel, title);
	}
}