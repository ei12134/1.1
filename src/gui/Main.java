package gui;

import java.io.IOException;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws IOException {

		JFrame frame = new JFrame();
		frame.setTitle("Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		new Menu(frame);
	}
}