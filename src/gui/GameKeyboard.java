package gui;

import java.io.File;
import java.util.HashMap;

public class GameKeyboard {

	private HashMap<String, Integer> keys;
	private String filename = "gameKeys.lpoo";
	private KeyboardConfig keyConfig;

	
	public GameKeyboard() {
		keys = new HashMap<String, Integer>();
		keyConfig = new KeyboardConfig();
	}

	
	public void initializeKeys() {
		File f = new File(filename);
		if(!f.exists()) {
			try {
				f.createNewFile();
				keyConfig.writeDefaultConfig();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			//Read the configuration file
			keys = keyConfig.getKeys();
		}
	}
	

	public HashMap<String, Integer> getKeys() {
		if (keys.size() > 0)
			return keys;

		return null;
	}
}
