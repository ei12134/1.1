package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Menu extends JPanel {

	private JPanel panel;
	private JFrame frame;
	private static final long serialVersionUID = 1L;

	public Menu() {
		JButton jb = new JButton("Press Me");

		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.out.println("ActionEvent!");
			}
		});
		jb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				System.out.println("ItemEvent!");
			}
		});
		jb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ev) {
				System.out.println("ChangeEvent!");
			}
		});

		JFrame g = new JFrame();
		g.setSize(800, 600);
		// f.pack();
		g.setVisible(true);

		JInternalFrame f = new JInternalFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(jb);
		f.setSize(640, 480);
		f.pack();
		f.setVisible(true);
		g.add(f);

		Board board = new Board(21);

		g.add(board);
		g.setTitle("Random Maze");
		g.setResizable(false);
		g.setContentPane(board);
		g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.setSize(new Dimension(32 * 21, 32 * 21));
		g.setVisible(true);

	}
}
