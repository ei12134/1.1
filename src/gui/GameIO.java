package gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import logic.Piece;

public class GameIO implements Serializable {

	private static final long serialVersionUID = 1L;

	public GameIO() {
	}

	
	public void saveMaze(String filename, ArrayList<ArrayList<Piece>> m) {
		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;

		try {
			outStream = new FileOutputStream(filename);
			objStream = new ObjectOutputStream(outStream);

			objStream.writeObject(m);
			objStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<Piece>> readFile(String filename) {
		ArrayList<ArrayList<Piece>> maze = new ArrayList<ArrayList<Piece>>();
		FileInputStream inStream = null;
		ObjectInputStream objInStream = null;
		try {
			inStream = new FileInputStream(filename);
			objInStream = new ObjectInputStream(inStream);

			maze = (ArrayList<ArrayList<Piece>>) objInStream.readObject();
			objInStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return maze;
	}
}
