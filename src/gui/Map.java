package gui;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;

import cli.Cli;

public class Map {
	// int size = Cli.setMazeSize(); //Preciso do tamanho
	int size = 13;
	private Scanner m;

	private String Map[] = new String[size];

	private Image wall;
	private Image floor;

	public Map() {

		ImageIcon img = new ImageIcon(
				"src\\png\\wall12.png");
		wall = img.getImage();
		 img = new ImageIcon(
				"src\\png\\floor.png");
		floor = img.getImage();
	
		openFile();
		readFile();
		closeFile();
	}

	public Image getWall() {
		return wall;
	}
	
	public Image getFloor() {
		return floor;
	}

	public String getMap(int x, int y) {
		String index = Map[y].substring(x, x + 1);
		return index;

	}

	public void openFile() {
		try {
			m = new Scanner(
					new File(
							"src\\png\\Map.txt"));// ficheiro
																				// do
																									// mapa
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readFile() {

		while (m.hasNext()) {
			for (int i = 0; i < size; i++) {
				Map[i] = m.next();

			}
		}

	}

	public void closeFile() {

	}
}