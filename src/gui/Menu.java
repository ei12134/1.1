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
	private JButton play;
	private JButton exit;
	private JButton settings;
	private JButton load;
	private Dimension dimension;

	public Menu(JFrame frame) {
		// Menu Buttons
		play = new JButton("Play Game");
		exit = new JButton("Exit");
		settings = new JButton("Settings");
		load = new JButton("Load Game");
		dimension = new Dimension(720, 720);
		this.frame = frame;

		addKeyListener(this);

		// Set up menu and settings panels
		setMenuPanel();
		showPanel(this);
		playPanel = new Play(this, dimension);
		settingsPanel = new Settings(this, dimension);

		// Button actions
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				playPanel.closeMenuPanel();
				playPanel.setPlayPanel();
				showPanel(playPanel);
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

		// load.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// final JFileChooser fileChooser = new JFileChooser();
		// fileChooser.addChoosableFileFilter(new
		// FileNameExtensionFilter("Ficheiros de puzzle...", "puzzle"));
		// int i = fileChooser.showDialog(Menu.this,
		// "Escolher o ficheiro do puzzle...");
		//
		// if(i == JFileChooser.APPROVE_OPTION) {
		// File puzzle = fileChooser.getSelectedFile();
		// startPuzzle(puzzle);
		// }
		// }
		// });
		//
	}

	public void startPuzzle(/* File puzzle */) {
		// if(puzzle.exists()) {
		// ArrayList<ArrayList<Piece>> maze = new ArrayList<ArrayList<Piece>>();
		// maze = getPuzzleFile(puzzle);

		playPanel.closeMenuPanel();
		playPanel.setPlayPanel();
		showPanel(playPanel);
		// GameUI game = new GameUI(this, 1, dimension, maze);
		// this.removeAll();
		// this.showPanel(game);
		// game.requestFocusInWindow();
		// } else {
		// JOptionPane.showMessageDialog(null, "O ficheiro " + puzzle.getName()
		// + " n‹o existe no sistema!");
		// }
	}

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
					"Ocorreu um erro ao ler o ficheiro do puzzle!");
			e.printStackTrace();
		}

		return maze;
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

	public void showPanel(JPanel panel) {
		frame.add(panel);
		frame.setSize(dimension);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.requestFocusInWindow();
	}

	public JFrame getFrame() {
		return frame;
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

	public Play getPlayPanel() {
		return playPanel;
	}
}