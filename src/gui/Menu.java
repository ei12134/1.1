package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

	private JFrame menuFrame;
	private JButton standardMaze;
	private JButton randomMaze;
	private JButton exit;
	private Maze board;

	public Menu() {
		standardMaze = new JButton("Standard maze");
		randomMaze = new JButton("Random maze");
		exit = new JButton("Exit");

		menuFrame = new JFrame();
		menuFrame.setSize(1280, 720);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setVisible(true);

		menuFrame.add(standardMaze);
		menuFrame.add(randomMaze);
		menuFrame.add(exit);
		menuFrame.setLayout(new FlowLayout());

		// Button action when clicked
		standardMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze();
			}
		});

		randomMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startMaze();
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

	public void startMaze() {
		board = new Maze(21);
		menuFrame.add(board);
		menuFrame.setTitle("Random Maze");
		menuFrame.setResizable(false);
		menuFrame.setContentPane(board);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setSize(new Dimension(1280, 720));
		menuFrame.setVisible(true);
	}
}
