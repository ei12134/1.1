package gui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class GameKeyboard {

	private HashMap<String, Integer> keys;
	private ArrayList<KeyboardKey> listKeys;
	String filename = "gameKeys.lpoo";

	
	public GameKeyboard() {
		keys = new HashMap<String, Integer>();
		listKeys = new ArrayList<KeyboardKey>();
	}

	
	public void initializeKeys() {
		File f = new File(filename);
		if(!f.exists()) {
			try {
				f.createNewFile();
				createStandardKeys();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			//Read the configuration file
		}
	}
	
	
	public void createStandardKeys() {
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
			//objStream.writeObject(new KeyboardKey("up_arrow", KeyEvent.VK_UP));
			//objStream.writeObject(new KeyboardKey("down_arrow",
				//	KeyEvent.VK_DOWN));
			//objStream.writeObject(new KeyboardKey("left_arrow",
				//	KeyEvent.VK_LEFT));
			//objStream.writeObject(new KeyboardKey("right_arrow",
				//	KeyEvent.VK_RIGHT));
			//objStream.writeObject(new KeyboardKey("eagle_spacebar",
				//	KeyEvent.VK_SPACE));

			//Allow to customize
			listKeys.add(new KeyboardKey("up", KeyEvent.VK_W));
			listKeys.add(new KeyboardKey("down", KeyEvent.VK_S));
			listKeys.add(new KeyboardKey("left", KeyEvent.VK_A));
			listKeys.add(new KeyboardKey("right", KeyEvent.VK_D));
			listKeys.add(new KeyboardKey("eagle", KeyEvent.VK_E));
			objStream.writeObject(listKeys);
			//objStream.writeObject(new KeyboardKey("up", KeyEvent.VK_W));
			//objStream.writeObject(new KeyboardKey("down", KeyEvent.VK_S));
			//objStream.writeObject(new KeyboardKey("left", KeyEvent.VK_A));
			//objStream.writeObject(new KeyboardKey("right", KeyEvent.VK_D));
			//objStream.writeObject(new KeyboardKey("eagle", KeyEvent.VK_E));
			objStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void readKeyboardKeys() {
		
	}
	

	public void readKeys() {
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
			/**KeyboardKey tmp;
			for (int i = 0; i < 10; i++) {
				tmp = (KeyboardKey) objInStream.readObject();
				keys.put(tmp.getCommand(), tmp.getCommandChar());
			}*/
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
