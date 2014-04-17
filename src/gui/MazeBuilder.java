package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeBuilder extends JPanel {

	private JFrame frame;
	private Menu menu;
	private int mazeSize;
	
	
	public MazeBuilder(final Menu menu, int mazeSize) {
		this.menu = menu;
		this.frame = menu.getFrame();
		this.mazeSize = mazeSize;
		
		
		this.frame.setLayout(new GridLayout(mazeSize, mazeSize));
	}
	
	
	public void startMazeBuilder() {
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void closeMenuPanel() {
		this.setVisible(false);
		menu.remove(this);
		menu.showPanel(menu);
		menu.setVisible(true);
	}
}
