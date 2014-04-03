package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Menu extends JPanel {

	private JPanel panel;
	private JFrame menuFrame;
	private static final long serialVersionUID = 1L;

	public Menu() {
		JButton randommap = new JButton("MAPA TAMANHO21");
		JButton exitButton = new JButton("EXIT");
//		
//		jb.addItemListener(new ItemListener() {
//			public void itemStateChanged(ItemEvent ev) {
//				System.out.println("ItemEvent!");
//			}
//		});
//		jb.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent ev) {
//				System.out.println("ChangeEvent!");
//			}
//		});

		menuFrame = new JFrame();
		menuFrame.setSize(1024, 768);
		// f.pack();
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setVisible(true);

		JInternalFrame f = new JInternalFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(randommap);
		f.setSize(1024, 768);
		//f.pack();
		
		f.setVisible(true);
		menuFrame.add(f);
		
		//Called when the start random game button is clicked
		randommap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				startRandomBoard();
			}
		});
		
		//Called when the Exit button is clicked
	//	exitButton.setLocation(30, 30);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            }
        });
	}

	public void startRandomBoard() {
		Board board = new Board(21);

		menuFrame.add(board);
		menuFrame.setTitle("Random Maze");
		menuFrame.setResizable(false);
		menuFrame.setContentPane(board);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setSize(new Dimension(1024, 768));
		menuFrame.setVisible(true);

	}
}
