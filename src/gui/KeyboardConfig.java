package gui;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class KeyboardConfig {

	private String filename = "gameKeys.lpoo";
	
	public KeyboardConfig() {
	}
	
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> getKeys() {
		HashMap<String, Integer> keys = new HashMap<String, Integer>();
		FileInputStream inStream = null;
		ObjectInputStream objInStream = null;
		try {
			ArrayList<KeyboardKey> key = new ArrayList<KeyboardKey>();
			inStream = new FileInputStream(filename);
			objInStream = new ObjectInputStream(inStream);

			Object o = objInStream.readObject();
			if(o instanceof ArrayList) {
				key = (ArrayList<KeyboardKey>) o;
				KeyboardKey tmp;
				for(int i = 0; i < key.size(); i++) {
					tmp = key.get(i);
					keys.put(tmp.getCommand(), tmp.getCommandChar());
				}
			}		
			objInStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return keys;
	}
	
	
	public void writeDefaultConfig() {
		ArrayList<KeyboardKey> listKeys = new ArrayList<KeyboardKey>();
		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;
		try {
			outStream = new FileOutputStream(filename);
			objStream = new ObjectOutputStream(outStream);
			//Default ones don't overwrite
			listKeys.add(new KeyboardKey("up_arrow", KeyEvent.VK_UP));
			listKeys.add(new KeyboardKey("down_arrow", KeyEvent.VK_DOWN));
			listKeys.add(new KeyboardKey("left_arrow", KeyEvent.VK_LEFT));
			listKeys.add(new KeyboardKey("right_arrow", KeyEvent.VK_RIGHT));
			listKeys.add(new KeyboardKey("eagle_spacebar", KeyEvent.VK_SPACE));

			//Allow to customize
			listKeys.add(new KeyboardKey("up", KeyEvent.VK_W));
			listKeys.add(new KeyboardKey("down", KeyEvent.VK_S));
			listKeys.add(new KeyboardKey("left", KeyEvent.VK_A));
			listKeys.add(new KeyboardKey("right", KeyEvent.VK_D));
			listKeys.add(new KeyboardKey("eagle", KeyEvent.VK_E));
			objStream.writeObject(listKeys);
			objStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void writeCustomConfig(JTextField up, JTextField down, JTextField left, JTextField right) {
		ArrayList<KeyboardKey> listKeys = new ArrayList<KeyboardKey>();
		FileOutputStream outStream = null;
		ObjectOutputStream objStream = null;
		try {
			outStream = new FileOutputStream(filename);
			objStream = new ObjectOutputStream(outStream);
			listKeys.add(new KeyboardKey("up_arrow", KeyEvent.VK_UP));
			listKeys.add(new KeyboardKey("down_arrow", KeyEvent.VK_DOWN));
			listKeys.add(new KeyboardKey("left_arrow", KeyEvent.VK_LEFT));
			listKeys.add(new KeyboardKey("right_arrow", KeyEvent.VK_RIGHT));
			listKeys.add(new KeyboardKey("eagle_spacebar", KeyEvent.VK_SPACE));

			
			listKeys.add(new KeyboardKey("up", KeyStroke.getKeyStroke(up.getText()).getKeyCode()));
			listKeys.add(new KeyboardKey("down", KeyStroke.getKeyStroke(down.getText()).getKeyCode()));
			listKeys.add(new KeyboardKey("left", KeyStroke.getKeyStroke(left.getText()).getKeyCode()));
			listKeys.add(new KeyboardKey("right", KeyStroke.getKeyStroke(right.getText()).getKeyCode()));
			listKeys.add(new KeyboardKey("eagle", KeyEvent.VK_E));
			objStream.writeObject(listKeys);
			objStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
