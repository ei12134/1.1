package gui;

import logic.*;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
    private int boardSize;
	private Scanner scanner;

    private ArrayList<ArrayList<Piece> > tabuleiro;

	private Image wall;
	private Image floor;

	public Map(int boardSize, boolean random) {
        this.boardSize = boardSize;
        tabuleiro = new ArrayList<ArrayList<Piece>>();
        wall = new ImageIcon("src//png//wall12.png").getImage();
        floor = new ImageIcon("src//png/floor.png").getImage();

        if(random) {
            generateRandomBoard();
        }
    }


    public void generateRandomBoard() {
        Hero hero = new Hero(0, 0);
        Eagle eagle = new Eagle(0, 0);
        ArrayList<Dragon> dragons = new ArrayList<Dragon>();
        Maze maze = new Maze(boardSize, 3);
        tabuleiro = maze.getMaze();
        System.out.println(tabuleiro);
    }

    public Image getWall() {
        return wall;
    }


	public Image getFloor() {
        return floor;
    }


	public Piece getMap(int x, int y) {
		return tabuleiro.get(y).get(x);
	}

	public void openFile() {
		try {
			scanner = new Scanner(new File("src//png//Map.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void readFile() {
        HashMap<java.lang.Character, String> pieces = new HashMap<java.lang.Character, String>();
        pieces.put('X', PieceType.WALL.asString());
        pieces.put('F', PieceType.FREE.asString());

        int i = 0;
        while(scanner.hasNext()) {
            ArrayList<Piece> row = new ArrayList<Piece>();
            String s = scanner.next();
            for(int j = 0; j < s.length(); j++)
                row.add(new Piece(i, j, pieces.get(s.charAt(j))));
            tabuleiro.add(row);
            i++;
        }
        scanner.close();
	}


	public void closeFile() {

	}
}