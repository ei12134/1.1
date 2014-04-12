package gui;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class GameKeyboard {

	private HashMap<String, Integer> keys;
	String filename = "gameKeys.lpoo";

	public GameKeyboard() {
		keys = new HashMap<String, Integer>();
	}

	public void initializeKeys() {
		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;
		try {
			outStream = new FileOutputStream(filename);
			objStream = new ObjectOutputStream(outStream);
			objStream.writeObject(new KeyboardKey("up", KeyEvent.VK_W));
			objStream.writeObject(new KeyboardKey("down", KeyEvent.VK_S));
			objStream.writeObject(new KeyboardKey("left", KeyEvent.VK_A));
			objStream.writeObject(new KeyboardKey("right", KeyEvent.VK_D));
			objStream.writeObject(new KeyboardKey("eagle", KeyEvent.VK_E));

			objStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readKeys() {
		FileInputStream inStream = null;
		ObjectInputStream objInStream = null;
		try {
			inStream = new FileInputStream(filename);
			objInStream = new ObjectInputStream(inStream);

			KeyboardKey tmp;
			for (int i = 0; i < 5; i++) {
				tmp = (KeyboardKey) objInStream.readObject();
				keys.put(tmp.getCommand(), tmp.getCommandChar());
			}
			objInStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> getKeys() {
		if (keys.size() > 0)
			return keys;

		return null;
	}
}
